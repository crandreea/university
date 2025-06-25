
using log4net;
using System.Net;
using System.Net.Sockets;

namespace Networking.utils
{
    public abstract class AbstractServer
    {
        private int port;
        private TcpListener server;
        private static readonly ILog log = LogManager.GetLogger(typeof(AbsConcurrentServer));

        public AbstractServer(int port)
        {
            this.port = port;
        }

        public void Start()
        {
            try
            {
                server = new TcpListener(IPAddress.Any, port);
                server.Start();
                log.Info("Server started on port {port}" + port);
                while (true)
                {
                    log.Info("Waiting for clients...");
                    TcpClient client = server.AcceptTcpClient();
                    log.Info("Client connected...");
                    ProcessRequest(client);
                }
            }
            catch (Exception e)
            {
                log.Error(e);
                throw new ServerException("Starting server error", e);
            }
        }

        public void Stop()
        {
            try
            {
                server?.Stop();
                log.Info("Server stopped.");
            }
            catch (Exception e)
            {
                log.Error(e + "Error stopping server");
                throw new ServerException("Closing server error", e);
            }
        }

        protected abstract void ProcessRequest(TcpClient client);
    }

}
