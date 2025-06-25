
using System.Configuration;
using System.Data.Common;
using log4net;
using Microsoft.Data.Sqlite;

namespace Persistence;

public class JdbcUtils
{
    private static readonly ILog logger = LogManager.GetLogger(typeof(JdbcUtils));
    private static JdbcUtils instance;
    private readonly DbConnection connection;

    private JdbcUtils()
    {
        try
        {
            logger.Info("Loading database configuration from app.config...");

            var connectionStringSettings = ConfigurationManager.ConnectionStrings["SQLiteConnection"];

            if (connectionStringSettings == null)
            {
                throw new Exception("No valid database connection string found in app.config.");
            }

            string providerName = connectionStringSettings.ProviderName;
            string connectionString = connectionStringSettings.ConnectionString;

            if (string.IsNullOrEmpty(connectionString))
            {
                throw new Exception("Database connection string is missing in app.config.");
            }

            logger.Info($"Connecting to database using provider: {providerName}");
            
            if (providerName == "System.Data.SQLite")
            {
                connection = new SqliteConnection(connectionString);
            }
            else
            {
                throw new Exception($"Unsupported database provider: {providerName}");
            }

            connection.Open();
            logger.Info("Database connection established successfully.");
        }
        catch (Exception ex)
        {
            logger.Error("Database connection failed.", ex);
            throw new Exception("Database connection failed.", ex);
        }
    }

    public static JdbcUtils GetInstance()
    {
        return instance ??= new JdbcUtils();
    }

    public DbConnection GetConnection()
    {
        logger.Info("Getting database connection...");
        return connection;
    }
}
