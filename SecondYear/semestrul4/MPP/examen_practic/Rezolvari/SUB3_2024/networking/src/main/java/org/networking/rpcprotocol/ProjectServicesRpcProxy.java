package org.networking.rpcprotocol;

import model.Configuration;
import model.Game;
import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.services.ProjectException;
import org.services.ProjectObserver;
import org.services.ProjectServices;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ProjectServicesRpcProxy implements ProjectServices {
    private final String host;
    private final int port;

    private ProjectObserver client;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;

    private final BlockingQueue<Response> qresponses;
    private volatile boolean finished;

    private static final Logger logger = LogManager.getLogger(ProjectServicesRpcProxy.class);

    public ProjectServicesRpcProxy(String host, int port) throws ProjectException {
        this.host = host;
        this.port = port;
        qresponses = new LinkedBlockingQueue<>();
        logger.info("RPC Proxy created for server {}:{}", host, port);
        initializeConnection();
    }

    private void initializeConnection() throws ProjectException {
        try {
            logger.debug("Initializing connection to {}:{}", host, port);
            connection = new Socket(host, port);
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            finished = false;
            startReader();
            logger.info("Connection established successfully to {}:{}", host, port);
        } catch (IOException e) {
            logger.error("Error initializing connection to {}:{}: {}", host, port, e.getMessage(), e);
            throw new ProjectException("Error connecting to server: " + e.getMessage(), e);
        }
    }

    private void startReader() {
        Thread readerThread = new Thread(() -> {
            logger.info("Starting response reader thread");
            while (!finished) {
                try {
                    Response response = (Response) input.readObject();
                    if (response.getType() == ResponseType.UPDATE_GAME) {
                        handleUpdateResponse(response);
                    } else {
                        qresponses.put(response);
                    }
                } catch (IOException e) {
                    if (!finished) {
                        logger.error("Error reading response: {}", e.getMessage(), e);
                        closeConnection();
                        break;
                    }
                } catch (ClassNotFoundException ignored) {

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        readerThread.setDaemon(true);
        readerThread.start();
    }

    private void closeConnection() {
        logger.info("Closing connection to {}:{}", host, port);
        finished = true;
        try {
            if (input != null) input.close();
        } catch (IOException e) {
            logger.error("Error closing input stream: {}", e.getMessage());
        }
        try {
            if (output != null) output.close();
        } catch (IOException e) {
            logger.error("Error closing output stream: {}", e.getMessage());
        }
        try {
            if (connection != null && !connection.isClosed()) connection.close();
            logger.debug("Socket connection closed.");
        } catch (IOException e) {
            logger.error("Error closing socket connection: {}", e.getMessage());
        }
        this.client = null;
    }

    private void handleUpdateResponse(Response response) {
        try {
            if (response.getType() == ResponseType.UPDATE_GAME) {
                logger.info("Received update notification");
                if (client != null) {
                    Game game = (Game) response.getData();
                    client.gameAdded(game);
                } else {
                    logger.warn("Client observer not set, couldn't forward notification");
                }
            }
        } catch (Exception e) {
            logger.error("Error handling update notification: {}", e.getMessage(), e);
        }
    }

    private Response sendRequestAndWait(Request request) throws ProjectException {
        try {
            logger.debug("Sending request: {}", request);
            synchronized (output){
                output.writeObject(request);
            }
            output.flush();
            Response response = qresponses.take();
            logger.debug("Received response from queue: {}", response);
            return response;
        } catch (IOException e) {
            logger.error("Error sending request or flushing output: {}", e.getMessage(), e);
            closeConnection();
            throw new ProjectException("Error sending request to server: " + e.getMessage(), e);
        } catch (InterruptedException e) {
            logger.error("Interrupted while waiting for response: {}", e.getMessage(), e);
            Thread.currentThread().interrupt();
            closeConnection();
            throw new ProjectException("Interrupted while waiting for server response: " + e.getMessage(), e);
        }
    }

    @Override
    public User login(String username, String password, ProjectObserver client) throws Exception {
        if (this.output == null) {
            initializeConnection();
        }
        User orgToSend = new User(username, password);
        Request req = new Request.Builder().type(RequestType.LOGIN).data(orgToSend).build();

        Response response = sendRequestAndWait(req);

        if (response.getType() == ResponseType.OK) {
            logger.info("Login successful via proxy for user: {}", username);
            this.client = client;
            return (User) response.getData();
        } else {
            logger.warn("Login failed via proxy for user {}: {}", username, response.getData());
            String errorMessage = (response.getData() != null) ? response.getData().toString() : "Unknown login error";
            throw new ProjectException(errorMessage);
        }
    }

    @Override
    public void logout(User user, ProjectObserver client) throws ProjectException {
        this.client = client;
        Request req = new Request.Builder().type(RequestType.LOGOUT).data(user).build();

        Response response = sendRequestAndWait(req);
        closeConnection();

        if (response.getType() == ResponseType.ERROR) {
            logger.warn("Logout failed via proxy for user {}: {}", user.getUsername(), response.getData());
            String errorMessage = (response.getData() != null) ? response.getData().toString() : "Unknown login error";
            throw new ProjectException(errorMessage);
        }
    }

    @Override
    public Iterable<User> getAllOrganizatori() throws ProjectException {
        Request req = new Request.Builder().type(RequestType.GET_ORGANIZATORI).build();
        Response response = sendRequestAndWait(req);
        if (response.getType() == ResponseType.ERROR) {
            String errorMessage = (response.getData() != null) ? response.getData().toString() : "Unknown login error";
            throw new ProjectException(errorMessage);
        }

        return (List<User>) response.getData();
    }

    @Override
    public Game addGame(Game game) throws SQLException, ProjectException {
        Request req = new Request.Builder().type(RequestType.ADD_GAME).data(game).build();
        Response response = sendRequestAndWait(req);
        if (response.getType() == ResponseType.ERROR) {
            String errorMessage = (response.getData() != null) ? response.getData().toString() : "Unknown login error";
            throw new ProjectException(errorMessage);
        }

        return (Game) response.getData();
    }

    @Override
    public List<Game> getAllGames() throws ProjectException {
        Request req = new Request.Builder().type(RequestType.GET_ALL_GAMES).build();
        Response response = sendRequestAndWait(req);
        if (response.getType() == ResponseType.ERROR) {
            String errorMessage = (response.getData() != null) ? response.getData().toString() : "Unknown login error";
            throw new ProjectException(errorMessage);
        }

        return (List<Game>) response.getData();
    }

    @Override
    public List<Configuration> getAllConfigurations() throws ProjectException {
        Request req = new Request.Builder().type(RequestType.GET_ALL_CONFIGURATIONS).build();
        Response response = sendRequestAndWait(req);
        if (response.getType() == ResponseType.ERROR) {
            String errorMessage = (response.getData() != null) ? response.getData().toString() : "Unknown login error";
            throw new ProjectException(errorMessage);
        }

        return (List<Configuration>) response.getData();
    }

    @Override
    public User findPlayerByAlias(String username) throws ProjectException {
        Request req = new Request.Builder().type(RequestType.FIND_PLAYER_BY_ALIAS).data(username).build();
        Response response = sendRequestAndWait(req);
        if (response.getType() == ResponseType.ERROR) {
            String errorMessage = (response.getData() != null) ? response.getData().toString() : "Unknown login error";
            throw new ProjectException(errorMessage);
        }

        return (User) response.getData();
    }
}
