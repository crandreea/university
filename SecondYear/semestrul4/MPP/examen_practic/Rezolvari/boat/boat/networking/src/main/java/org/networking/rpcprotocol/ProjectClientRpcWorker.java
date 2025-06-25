package org.networking.rpcprotocol;

import model.Game;
import model.GamePosition;
import model.ShotResultDTO;
import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.services.ProjectException;
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
                case MAKE_SHOT:{
                    logger.info("Processing MAKE_SHOT request");
                    Object[] datalist = (Object[]) request.getData();
                    Integer gameId = (Integer) datalist[0];
                    int x = (int) datalist[1];
                    int y = (int) datalist[2];

                    ShotResultDTO position = server.makeShot(gameId, x, y);
                    response = new Response.Builder().type(ResponseType.R_MAKE_SHOT).data(position).build();
                    break;
                }
                case CREATE_GAME:{
                    logger.info("Processing CREATE_GAME request");
                    User user = (User) request.getData();
                    Game game = server.createGame(user);
                    response = new Response.Builder().type(ResponseType.R_CREATE_GAME).data(game).build();
                    break;
                }
                case GET_GAME_BY_ID:{
                    logger.info("Processing GET_GAME_BY_ID request");
                    Integer gameId = (Integer) request.getData();
                    Game game = server.getGameById(gameId);
                    response = new Response.Builder().type(ResponseType.R_GET_GAME_BY_ID).data(game).build();
                    break;
                }
                case GET_FINISHED_GAMES:{
                    logger.info("Processing GET_FINISHED_GAMES request");
                    List<Game> games = server.getFinishedGames();
                    response = new Response.Builder().type(ResponseType.R_GET_FINISHED_GAMES).data(games).build();
                    break;
                }
                case GET_GAME_POSITIONS:{
                    logger.info("Processing GET_GAME_POSITIONS request");
                    Integer gameId = (Integer) request.getData();
                    List<GamePosition> games = server.getGamePositions(gameId);
                    response = new Response.Builder().type(ResponseType.R_GET_GAME_POSITIONS).data(games).build();
                    break;
                }
                case GET_BOAT_GAME_POSITIONS:{
                    logger.info("Processing GET_BOAT_GAME_POSITIONS request");
                    Integer gameId = (Integer) request.getData();
                    List<GamePosition> games = server.getBoatGamePositions(gameId);
                    response = new Response.Builder().type(ResponseType.R_GET_BOAT_GAME_POSITIONS).data(games).build();
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
    public void gameFinished(Game game) throws ProjectException {
        logger.info("Notifying client {} about participant inscription update.", connection.getInetAddress());
        Response resp = new Response.Builder().type(ResponseType.GAME_FINISHED).data(game).build();
        try {
            sendResponse(resp);
            logger.info("Sent UPDATE_PROBE notification to client {}", connection.getInetAddress());
        } catch (IOException e) {
            logger.error("Error sending update notification to client {}: {}", connection.getInetAddress(), e.getMessage(), e);
            throw new ProjectException("Error sending update to client: " + e.getMessage(), e);
        }
    }

    @Override
    public void leaderboardUpdated() throws ProjectException {
        logger.info("Notifying client {} about participant inscription update.", connection.getInetAddress());
        Response resp = new Response.Builder().type(ResponseType.UPDATE_CLASAMENT).build();
        try {
            sendResponse(resp);
            logger.info("Sent UPDATE_PROBE notification to client {}", connection.getInetAddress());
        } catch (IOException e) {
            logger.error("Error sending update notification to client {}: {}", connection.getInetAddress(), e.getMessage(), e);
            throw new ProjectException("Error sending update to client: " + e.getMessage(), e);
        }
    }

}
