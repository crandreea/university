package org.networking.rpcprotocol;

import model.Game;
import model.Position;
import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.services.ProjectObserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Optional;

import org.services.ProjectServices;

public class ProjectClientRpcWorker implements Runnable, ProjectObserver {
    private ProjectServices server;
    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;
    private static final Logger logger = LogManager.getLogger(ProjectClientRpcWorker.class);

    public ProjectClientRpcWorker(ProjectServices server, Socket connection) {
        this.server = server;
        this.connection = connection;
        try {
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            connected = true;
            logger.info("Worker created for client: {}", connection.getInetAddress());
        } catch (IOException e) {
            logger.error("Error creating worker streams for client {}: {}", connection.getInetAddress(), e.getMessage());
        }
    }

    @Override
    public void run() {
        while(connected){
            try {
                Object request=input.readObject();
                logger.debug("Received request from client: "+request);
                Response response = handleRequest((Request)request);
                if (response!=null){
                    sendResponse(response);
                }
            } catch (IOException|ClassNotFoundException e) {
                logger.error(e.getStackTrace());
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error(e.getStackTrace());
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            logger.error("Error "+e);
        }
    }

    private static Response okResponse=new Response.Builder().type(ResponseType.OK).build();

    private Response handleRequest(Request request) {
        Response response = null;
        try {
            switch (request.getType()) {
                case LOGIN: {
                    logger.info("Processing LOGIN request");
                    User org = (User) request.getData();

                    try{
                        User orgLog = server.login(org.getUsername(), org.getPassword(), this);
                        logger.info("Login successful for user: {}", org.getUsername());
                        return new Response.Builder().type(ResponseType.OK).data(orgLog).build();

                    } catch (Exception e) {
                        connected = false;
                        return new Response.Builder().type(ResponseType.ERROR).data(e).build();
                    }
                }
                case LOGOUT: {
                    logger.info("Processing LOGOUT request");
                    User org = (User) request.getData();
                    try{
                        server.logout(org, this);
                        connected = false;
                        logger.info("Logout successful for user: {}", org.getUsername());
                        return okResponse;
                    }catch (Exception e) {
                        return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
                    }
                }
                case GET_ORGANIZATORI: {
                    logger.info("Processing GET_CATEGORII_VARSTA request");
                    List<User> organizators = (List<User>) server.getAllOrganizatori();
                    response = new Response.Builder().type(ResponseType.GET_ORGANIZATORI).data(organizators).build();
                    break;
                }
                case GET_ALL_GAMES:{
                    logger.info("Processing GET_ALL_GAMES_VARSTA request");
                    List<Game> organizators = (List<Game>) server.getAllGames();
                    response = new Response.Builder().type(ResponseType.GET_ALL_GAMES).data(organizators).build();
                    break;
                }
                case GET_ALL_TRAPS:{
                    logger.info("Processing GET_ALL_TRAPS_VARSTA request");
                    Game game = (Game) request.getData();
                    List<Position> organizators = (List<Position>) server.findTraps(game);
                    response = new Response.Builder().type(ResponseType.GET_ALL_TRAPS).data(organizators).build();
                    break;
                }
                case GET_ALL_POSITIONS:{
                    logger.info("Processing GET_ALL_POSITIONS_VARSTA request");
                    List<Position> organizators = (List<Position>) server.getAllPositions();
                    response = new Response.Builder().type(ResponseType.GET_ALL_POSITIONS).data(organizators).build();
                    break;
                }
                case GET_ALL_POSITIONS_BY_GAME:{
                    logger.info("Processing GET_ALL_POSITIONS_BY_GAME_VARSTA request");
                    Game game = (Game) request.getData();
                    List<Position> organizators = (List<Position>) server.getAllPositionsByGame(game);
                    response = new Response.Builder().type(ResponseType.GET_ALL_POSITIONS).data(organizators).build();
                    break;
                }
                case FIND_PLAYER_BY_ALIAS:{
                    logger.info("Processing FIND_PLAYER_BY_ALIAS_VARSTA request");
                    String alias = (String) request.getData();
                    User organizators = (User) server.findPlayerByAlias(alias);
                    response = new Response.Builder().type(ResponseType.FIND_PLAYER_BY_ALIAS).data(organizators).build();
                    break;

                }
                case ADD_POSITION:{
                    logger.info("Processing ADD_POSITION_VARSTA request");
                    Position position = (Position) request.getData();
                    Position position1 = (Position) server.addPosition(position);
                    response = new Response.Builder().type(ResponseType.ADD_POSITION).data(position1).build();
                    break;
                }
                case ADD_GAME:{
                    logger.info("Processing ADD_GAME_VARSTA request");
                    Game game = (Game) request.getData();
                    Game game1 = (Game) server.addGame(game);
                    response = new Response.Builder().type(ResponseType.ADD_GAME).data(game1).build();
                    break;
                }
                default:
                    logger.warn("Unknown request type: {}", request.getType());
                    response = new Response.Builder().type(ResponseType.ERROR).data("Unsupported request type").build();
                    break;
            }
        } catch (Exception e) {
            logger.error("Unexpected error handling request {}: {}", request.getType(), e.getMessage(), e);
            response = new Response.Builder().type(ResponseType.ERROR).data("Internal server error: " + e.getMessage()).build();
        }

        return response;
    }


    private void sendResponse(Response response) throws IOException{
        logger.debug("sending response "+response);
        synchronized (output) {
            output.writeObject(response);
        }
        output.flush();
    }

    @Override
    public void gameAdded(Game addedGame) {
        Response response = new Response.Builder().type(ResponseType.UPDATE_GAMES).data(addedGame).build();
        try {
            sendResponse(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
