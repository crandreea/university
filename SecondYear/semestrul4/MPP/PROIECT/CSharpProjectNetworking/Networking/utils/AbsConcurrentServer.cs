
using System.Net.Sockets;
using log4net;

namespace Networking.utils
{
    public abstract class AbsConcurrentServer : AbstractServer
    {
        private static readonly ILog log = LogManager.GetLogger(typeof(AbsConcurrentServer));
        public AbsConcurrentServer(int port) : base(port)
        {
            log.Info("Concurrent AbstractServer");
        }

        protected override void ProcessRequest(TcpClient client)
        {
            Thread tw = CreateWorker(client);
            tw.Start();
        }

        protected abstract Thread CreateWorker(TcpClient client);
    }
}
