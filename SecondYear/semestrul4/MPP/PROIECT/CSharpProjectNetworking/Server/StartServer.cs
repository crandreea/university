using Networking.utils;
using Services;
using System.Configuration;

namespace Server;

public class StartServer
{
    private const int DefaultPort = 55555;
    public static void Main(string[] args)
    {
        Console.WriteLine("RPC Server starting..."); 
        IProjectServices service;
        try
        {
            service = GlobalService.GetNetwork(); 
            Console.WriteLine("Service layer initialized.");
        }
        catch (Exception ex)
        {
            Console.WriteLine($"ERROR: Failed to initialize service layer. {ex.ToString()}"); // Log substitut
            Console.WriteLine("Server startup aborted.");
            return;
        }

     
        int serverPort = DefaultPort;
        try
        {
        
            string? portSetting = ConfigurationManager.AppSettings["ServerPort"];

            if (!string.IsNullOrEmpty(portSetting))
            {
                
                if (int.TryParse(portSetting, out int parsedPort))
                {
                   
                    if (parsedPort > 0 && parsedPort <= 65535)
                    {
                        serverPort = parsedPort;
                        Console.WriteLine($"Read ServerPort '{serverPort}' from app.config."); // Log substitut
                    }
                    else
                    {
                        Console.WriteLine($"Warning: Invalid port number value '{parsedPort}' for 'ServerPort' in app.config. Using default port {DefaultPort}."); // Log substitut
                    }
                }
                else
                {
                    Console.WriteLine($"Warning: Could not parse value '{portSetting}' for 'ServerPort' in app.config. Using default port {DefaultPort}."); // Log substitut
                }
            }
            else
            {
                Console.WriteLine($"Warning: 'ServerPort' key not found in app.config <appSettings>. Using default port {DefaultPort}."); // Log substitut
            }
        }
        catch (ConfigurationErrorsException ex)
        {
            Console.WriteLine($"ERROR: Error reading app.config <appSettings>. {ex.Message}. Using default port {DefaultPort}."); // Log substitut
        }
        catch (Exception ex) 
        {
            Console.WriteLine($"ERROR: Unexpected error reading configuration. {ex.ToString()}. Using default port {DefaultPort}."); // Log substitut
        }

        Console.WriteLine($"Server will run on port: {serverPort}"); // Log substitut


     
        AbsConcurrentServer? server = null;
        try
        {
            server = new RpcConcurrentServer(serverPort, service);
            Console.WriteLine($"Project RPC server starting on port {serverPort} ..."); // Log substitut
            server.Start();
        }
        catch (ServerException e)
        {
            Console.WriteLine($"ERROR starting the server: {e.Message}"); 
        }
        catch (Exception ex)
        {
            Console.WriteLine($"ERROR: An unexpected error occurred during server startup: {ex.ToString()}"); // Log substitut
        }
        finally
        {
            if (server != null)
            {
                Console.WriteLine("Attempting to stop the server..."); 
                try
                {
                    server.Stop();
                    Console.WriteLine("Server stopped."); 
                }
                catch (ServerException e)
                {
                    Console.WriteLine($"ERROR stopping server: {e.Message}"); // Log substitut
                }
                catch (Exception ex)
                {
                    Console.WriteLine($"ERROR: An unexpected error occurred during server shutdown: {ex.ToString()}"); // Log substitut
                }
            }
            Console.WriteLine("Server application finished."); // Log substitut
        }
    }
}