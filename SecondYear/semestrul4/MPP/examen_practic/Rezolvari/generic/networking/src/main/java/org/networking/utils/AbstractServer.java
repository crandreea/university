package org.networking.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.ServerException;

public abstract class AbstractServer {
    private int port;
    private ServerSocket server = null;
    private static final Logger logger = LogManager.getLogger(AbstractServer.class);


    public AbstractServer(int port) {
        this.port = port;
    }

    public void start() throws ServerException {
        try {
            server = new ServerSocket(port);
            logger.info("Server started on port {}", port);
            while (true) {
                logger.info("Waiting for clients ...");
                Socket client = server.accept();
                logger.info("Client connected: {}", client.getInetAddress());
                processRequest(client);
            }
        } catch (IOException e) {
            logger.error("Error starting server", e);
            throw new ServerException("Starting server error ", e);
        }
    }

    protected abstract void processRequest(Socket client);

    public void stop() throws ServerException {
        try {
            if (server != null && !server.isClosed()) {
                server.close();
                logger.info("Server stopped.");
            }
        } catch (IOException e) {
            logger.error("Error stopping server", e);
            throw new ServerException("Closing server error ", e);
        }
    }
}
