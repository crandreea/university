package org.networking.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.networking.rpcprotocol.ProjectClientRpcWorker;
import org.services.ProjectServices;

import java.net.Socket;

public class RpcConcurrentServer extends AbsConcurrentServer {
    private ProjectServices server;
    private static final Logger logger = LogManager.getLogger(RpcConcurrentServer.class);


    public RpcConcurrentServer(int port, ProjectServices server) {
        super(port);
        this.server = server;
        logger.info("RpcConcurrentServer created on port {}", port);
    }

    @Override
    protected Thread createWorker(Socket client) {
        ProjectClientRpcWorker worker = new ProjectClientRpcWorker(server, client);
        Thread thread=new Thread(worker);
        return thread;
    }

}
