using Networking.rpcprotocol;
using Services;
using System.Configuration;
namespace Client;

public class StartClient
{
    private const string defaultHost = "localhost";
    private const int defaultPort = 55555;

    [STAThread]
    public static void Main(string[] args)
    {

        string serverIP = ConfigurationManager.AppSettings["server.host"] ?? defaultHost;

        int serverPort;
        if (!int.TryParse(ConfigurationManager.AppSettings["server.port"], out serverPort))
        {
            serverPort = defaultPort;
            MessageBox.Show("Port invalid. Se folosește portul default: " + defaultPort);
        }

        IProjectServices service = new ProjectServicesRpcProxy(serverIP, serverPort);

        Login loginWindow = new Login();
        loginWindow.SetServer(service);

        Home mainWindow = new Home();
        mainWindow.SetServer(service);
        loginWindow.SetMainForm(mainWindow);
        
        Application.Run(loginWindow);
    }
}

