package ro.mpp2025.javaprojectui.utils;

import java.net.Socket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ro.mpp2025.javaprojectui.ProjectServices;
import ro.mpp2025.javaprojectui.rpcprotocol.ProjectClientRpcWorker;

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
