package ro.mpp2025.javaprojectui.rpcprotocol;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ro.mpp2025.javaprojectui.*;
import ro.mpp2025.javaprojectui.dto.ParticipantDTO;
import ro.mpp2025.javaprojectui.dto.ProbaDTO;
import ro.mpp2025.javaprojectui.Participant;


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

    public void run() {
        while(connected){
            try {
                Object request=input.readObject();
                logger.debug("Received request from client: "+request);
                Response response=handleRequest((Request)request);
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

    @Override
    public void inscriereAdded(Inscriere inscriere) throws ProjectException {
        logger.info("Notifying client {} about participant inscription update.", connection.getInetAddress());
        Response resp = new Response.Builder().type(ResponseType.UPDATE_INSCRIERE).data(inscriere).build();
        try {
            sendResponse(resp);
            logger.info("Sent UPDATE_PROBE notification to client {}", connection.getInetAddress());
        } catch (IOException e) {
            logger.error("Error sending update notification to client {}: {}", connection.getInetAddress(), e.getMessage(), e);
            throw new ProjectException("Error sending update to client: " + e.getMessage(), e);
        }
    }

    private static Response okResponse=new Response.Builder().type(ResponseType.OK).build();

    private Response handleRequest(Request request) {
        Response response = null;
        try {
            switch (request.getType()) {
                case LOGIN: {
                    logger.info("Processing LOGIN request");
                    Organizator org = (Organizator) request.getData();

                    try{
                        Organizator orgLog = server.login(org.getUsername(), org.getPassword(), this);
                        logger.info("Login successful for user: {}", org.getUsername());
                        return new Response.Builder().type(ResponseType.OK).data(orgLog).build();

                    } catch (Exception e) {
                        connected = false;
                        return new Response.Builder().type(ResponseType.ERROR).data(e).build();
                    }
                }
                case LOGOUT: {
                    logger.info("Processing LOGOUT request");
                    Organizator org = (Organizator) request.getData();
                    try{
                        server.logout(org, this);
                        connected = false;
                        logger.info("Logout successful for user: {}", org.getUsername());
                        return okResponse;
                    }catch (Exception e) {
                        return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
                    }
                }
                case GET_PROBE: {
                    logger.info("Processing GET_PROBE request");
                    try{
                        List<Proba> probe = (List<Proba>) server.getAllProba();
                        return new Response.Builder().type(ResponseType.GET_PROBE).data(probe).build();
                    }
                    catch (Exception e) {
                        connected = false;
                        return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
                    }

                }
                case GET_ORGANIZATORI: {
                    logger.info("Processing GET_CATEGORII_VARSTA request");
                    List<Organizator> organizators = (List<Organizator>) server.getAllOrganizatori();
                    response = new Response.Builder().type(ResponseType.GET_ORGANIZATORI).data(organizators).build();
                    break;
                }
                case GET_PROBE_DTO: {
                    logger.info("Processing GET_PROBE_DTO request");
                    List<ProbaDTO> probeDTO = server.getAllProbaDTO();
                    response = new Response.Builder().type(ResponseType.GET_PROBE_DTO).data(probeDTO).build();
                    break;
                }
                case GET_PROBA_BY_NAME: {
                    logger.info("Processing GET_PROBA_BY_NAME request");
                    String name = (String) request.getData();
                    Proba proba = server.getProbaByName(name);
                    response = new Response.Builder().type(ResponseType.GET_PROBA_BY_NAME).data(proba).build();
                    break;
                }
                case GET_PROBA_BY_NAME_AND_RANGE: {
                    logger.info("Processing GET_PROBA_BY_NAME_AND_RANGE request");
                    Object[] datalist = (Object[]) request.getData();
                    String name = (String) datalist[0];
                    Integer range = (Integer) datalist[1];
                    Proba proba = server.getProbaByNameAndRange(name, range);
                    response = new Response.Builder().type(ResponseType.GET_PROBA_BY_NAME_AND_RANGE).data(proba).build();
                    break;
                }
                case SEARCH_PARTICIPANTS:{
                    logger.info("Processing SEARCH_PARTICIPANTS request");
                    Object[] datalist = (Object[]) request.getData();
                    Proba proba = (Proba) datalist[0];
                    CategorieVarsta categorie = (CategorieVarsta) datalist[1];
                    List<ParticipantDTO> participants = server.searchParticipants(proba, categorie);
                    response = new Response.Builder().type(ResponseType.SEARCH_PARTICIPANTS).data(participants).build();
                    break;
                }
                case GET_VARSTA_BY_RANGE:{
                    logger.info("Processing GET_VARSTA_BY_RANGE request");
                    String range = (String) request.getData();
                    CategorieVarsta varsta = server.getVarstaByRange(range);
                    response = new Response.Builder().type(ResponseType.GET_VARSTA_BY_RANGE).data(varsta).build();
                    break;
                }
                case FIND_PARTICIPANT_BY_CNP:{
                    logger.info("Processing FIND_PARTICIPANT_BY_CNP request");
                    String cnp = (String) request.getData();
                    Optional<Participant> participantDTO =  server.findParticipantByCNP(cnp);
                    response = new Response.Builder().type(ResponseType.FIND_PARTICIPANT_BY_CNP).data(participantDTO).build();
                    break;
                }
                case REFRESH_PROBE_STATISTICS: {
                    logger.info("Processing REFRESH_PROBE_STATISTICS request");
                    try {
                        server.refreshProbeStatistics();
                        return okResponse;
                    } catch (Exception e) {
                        return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
                    }
                }
                case REGISTER: {
                    logger.info("Processing REGISTER request");
                    Object[] registrationData = (Object[]) request.getData();
                    String name = (String) registrationData[0];
                    String cnp = (String) registrationData[1];
                    String event1 = (String) registrationData[2];
                    String event2 = (String) registrationData[3];
                    String range = (String) registrationData[4];
                    try{
                        String result = server.registerParticipant(name, cnp, event1, event2, range);
                        logger.info("Register successful for user: {}", cnp);
                        return  new Response.Builder().type(ResponseType.REGISTER).data(result).build();
                    }catch (Exception e) {
                        connected = false;
                        return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
                    }

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

}