package ro.mpp2025.javaprojectui.rpcprotocol;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ro.mpp2025.javaprojectui.*;
import ro.mpp2025.javaprojectui.dto.ParticipantDTO;
import ro.mpp2025.javaprojectui.dto.ProbaDTO;
import ro.mpp2025.javaprojectui.Participant;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Optional;
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
                    if (response.getType() == ResponseType.UPDATE_INSCRIERE) {
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
                    logger.error("Interrupted while adding response to queue: {}", e.getMessage(), e);
                    Thread.currentThread().interrupt();
                }
            }
        });
        readerThread.setDaemon(true);
        readerThread.start();
    }

    private void handleUpdateResponse(Response response) {
        try {
            if (response.getType() == ResponseType.UPDATE_INSCRIERE) {
                logger.info("Received update notification");
                if (client != null) {
                    Inscriere inscriere = (Inscriere) response.getData();
                    client.inscriereAdded(inscriere);
                } else {
                    logger.warn("Client observer not set, couldn't forward notification");
                }
            }
        } catch (Exception e) {
            logger.error("Error handling update notification: {}", e.getMessage(), e);
        }
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
    public Organizator login(String username, String password, ProjectObserver client) throws Exception {
        if (this.output == null) {
            initializeConnection();  // Ensure connection is initialized before using it
        }
        Organizator orgToSend = new Organizator(username, password);
        Request req = new Request.Builder().type(RequestType.LOGIN).data(orgToSend).build();

        Response response = sendRequestAndWait(req);

        if (response.getType() == ResponseType.OK) {
            logger.info("Login successful via proxy for user: {}", username);
            this.client = client;
            return (Organizator) response.getData();
        } else {
            logger.warn("Login failed via proxy for user {}: {}", username, response.getData());
            String errorMessage = (response.getData() != null) ? response.getData().toString() : "Unknown login error";
            throw new ProjectException(errorMessage);
        }
    }

    @Override
    public void logout(Organizator user, ProjectObserver client) throws ProjectException {
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
    public Iterable<Organizator> getAllOrganizatori() throws ProjectException {
        Request req = new Request.Builder().type(RequestType.GET_ORGANIZATORI).build();
        Response response = sendRequestAndWait(req);
        if (response.getType() == ResponseType.ERROR) {
            String errorMessage = (response.getData() != null) ? response.getData().toString() : "Unknown login error";
            throw new ProjectException(errorMessage);
        }

        return (List<Organizator>) response.getData();
    }

    @Override
    public Iterable<Proba> getAllProba() throws ProjectException {
        Request req = new Request.Builder().type(RequestType.GET_PROBE).build();
        Response response = sendRequestAndWait(req);
        if (response.getType() == ResponseType.ERROR) {
            String errorMessage = (response.getData() != null) ? response.getData().toString() : "Unknown login error";
            throw new ProjectException(errorMessage);
        }

        return (List<Proba>) response.getData();
    }

    @Override
    public List<ProbaDTO> getAllProbaDTO() throws ProjectException {
        Request req = new Request.Builder().type(RequestType.GET_PROBE_DTO).build();
        Response response = sendRequestAndWait(req);
        if (response.getType() == ResponseType.ERROR) {
            String errorMessage = (response.getData() != null) ? response.getData().toString() : "Unknown login error";
            throw new ProjectException(errorMessage);
        }

        return (List<ProbaDTO>) response.getData();
    }



    @Override
    public Proba getProbaByName(String name) throws ProjectException {
        Request req = new Request.Builder().type(RequestType.GET_PROBA_BY_NAME).data(name).build();
        Response response = sendRequestAndWait(req);
        if (response.getType() == ResponseType.ERROR) {
            String errorMessage = (response.getData() != null) ? response.getData().toString() : "Unknown login error";
            throw new ProjectException(errorMessage);
        }
        return (Proba) response.getData();
    }


    @Override
    public Proba getProbaByNameAndRange(String name, Integer range) throws ProjectException {
        Object[] data = new Object[2];
        data[0] = name;
        data[1] = range;
        Request req = new Request.Builder().type(RequestType.GET_PROBA_BY_NAME_AND_RANGE).data(data).build();
        Response response = sendRequestAndWait(req);
        if (response.getType() == ResponseType.ERROR) {
            String errorMessage = (response.getData() != null) ? response.getData().toString() : "Unknown login error";
            throw new ProjectException(errorMessage);
        }
        return (Proba) response.getData();
    }

    @Override
    public CategorieVarsta getVarstaByRange(String selectedCategory) throws ProjectException {
        Request req = new Request.Builder().type(RequestType.GET_VARSTA_BY_RANGE).data(selectedCategory).build();
        Response response = sendRequestAndWait(req);
        if (response.getType() == ResponseType.ERROR) {
            String errorMessage = (response.getData() != null) ? response.getData().toString() : "Unknown login error";
            throw new ProjectException(errorMessage);
        }
        return (CategorieVarsta) response.getData();
    }

    @Override
    public List<ParticipantDTO> searchParticipants(Proba selectedProba, CategorieVarsta selectedCategory) throws ProjectException {
        Object[] searchData = new Object[2];
        searchData[0] = selectedProba;
        searchData[1] = selectedCategory;
        Request req = new Request.Builder().type(RequestType.SEARCH_PARTICIPANTS).data(searchData).build();
        Response response = sendRequestAndWait(req);
        if (response.getType() == ResponseType.ERROR) {
            String errorMessage = (response.getData() != null) ? response.getData().toString() : "Unknown login error";
            throw new ProjectException(errorMessage);
        }
        return (List<ParticipantDTO>) response.getData();
    }

    @Override
    public String registerParticipant(String name, String cnp, String event1, String event2, String range) throws ProjectException {
        Object[] registrationData = new Object[5];
        registrationData[0] = name;
        registrationData[1] = cnp;
        registrationData[2] = event1;
        registrationData[3] = event2;
        registrationData[4] = range;
        Request req = new Request.Builder().type(RequestType.REGISTER).data(registrationData).build();
        Response response = sendRequestAndWait(req);
        if (response.getType() == ResponseType.ERROR) {
            String errorMessage = (response.getData() != null) ? response.getData().toString() : "Unknown login error";
            throw new ProjectException(errorMessage);
        }
        return (String) response.getData();
    }

    @Override
    public Optional<Participant> findParticipantByCNP(String cnp) throws ProjectException {
        Request req = new Request.Builder().type(RequestType.FIND_PARTICIPANT_BY_CNP).data(cnp).build();
        Response response = sendRequestAndWait(req);
        if (response.getType() == ResponseType.ERROR) {
            String errorMessage = (response.getData() != null) ? response.getData().toString() : "Unknown login error";
            throw new ProjectException(errorMessage);
        }
        return (Optional<Participant>) response.getData();
    }

    @Override
    public void refreshProbeStatistics() throws ProjectException {
        Request req = new Request.Builder().type(RequestType.REFRESH_PROBE_STATISTICS).build();
        Response response = sendRequestAndWait(req);
        if (response.getType() == ResponseType.ERROR) {
            String errorMessage = (response.getData() != null) ? response.getData().toString() : "Unknown error";
            throw new ProjectException(errorMessage);
        }
    }
}
