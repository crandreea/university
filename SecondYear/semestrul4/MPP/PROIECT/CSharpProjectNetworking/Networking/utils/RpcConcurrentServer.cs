
using System.Net.Sockets;
using log4net;
using Services;
using Networking.rpcprotocol;

namespace Networking.utils
{
    public class RpcConcurrentServer : AbsConcurrentServer
    {
        private IProjectServices server;
        private static readonly ILog log = LogManager.GetLogger(typeof(AbsConcurrentServer));

        public RpcConcurrentServer(int port, IProjectServices server) : base(port)
        {
            this.server = server;
            log.Error("RpcConcurrentServer created on port {port}" + port);
        }

        protected override Thread CreateWorker(TcpClient client)
        {
            ProjectClientRpcWorker worker = new ProjectClientRpcWorker(server, client);
            return new Thread(worker.Run); 
        }
    }
}
