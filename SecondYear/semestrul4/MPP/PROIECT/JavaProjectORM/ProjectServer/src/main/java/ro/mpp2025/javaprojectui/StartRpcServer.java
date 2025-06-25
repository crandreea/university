package ro.mpp2025.javaprojectui;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ro.mpp2025.javaprojectui.service.GlobalService;
import ro.mpp2025.javaprojectui.service.MainService;
import ro.mpp2025.javaprojectui.utils.AbsConcurrentServer;
import ro.mpp2025.javaprojectui.utils.RpcConcurrentServer;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;


public class StartRpcServer {
    private static int defaultPort = 55555;
    private static final Logger logger = (Logger) LogManager.getLogger(StartRpcServer.class);


    public static void main(String[] args) throws SQLException {

        Properties serverProps=new Properties();
        try {
            serverProps.load(StartRpcServer.class.getResourceAsStream("/server.properties"));
            serverProps.list(System.out);
        } catch (IOException e) {
            logger.error("Cannot find chatserver.properties {}", String.valueOf(e));
            return;
        }

        ProjectServices service =  GlobalService.getNetwork();

        int serverPort = defaultPort;
        try {
            serverPort = Integer.parseInt(serverProps.getProperty("server.port"));
        }catch (NumberFormatException nef){
            logger.debug("Using default port "+defaultPort);
        }

        logger.info("Starting Project RPC server on port {} ...", serverPort);
        AbsConcurrentServer server = new RpcConcurrentServer(serverPort, service);
        try {
            server.start();
        } catch (java.rmi.ServerException e) {
            logger.error("Error starting the server" + e.getMessage());
        }finally {
            try {
                server.stop();
            }catch(java.rmi.ServerException e){
                logger.error("Error stopping server "+e.getMessage());
            }
        }
    }
}
