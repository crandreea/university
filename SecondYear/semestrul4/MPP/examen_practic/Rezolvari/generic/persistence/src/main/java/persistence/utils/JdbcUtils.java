package persistence.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtils {


    private static final Logger logger= LogManager.getLogger(JdbcUtils.class);
    private static JdbcUtils instance;
    private final Connection connection;

    public JdbcUtils(){
        try {
            logger.info("Loading database configuration...");
            Properties config = PropertyFileLoader.loadProperties("/Users/croitoruandreea/Desktop/ANUL2/semestrul4/MPP/examen_practic/boat/boat/bd.config");
            String URL = config.getProperty("jdbc.url");
            String USER = config.getProperty("jdbc.user");
            String PASS = config.getProperty("jdbc.pass");

            logger.info("Attempting to connect to database...");
            connection = DriverManager.getConnection(URL, USER, PASS);

            logger.info("Database connection established successfully.");
        } catch (SQLException e) {
            logger.error("Database connection failed.", e);
            throw new RuntimeException(e);
        }
    }

    public static JdbcUtils getInstance() {
        if (instance == null) {
            instance = new JdbcUtils();
        }
        return instance;
    }

    public Connection getConnection(){
        logger.traceEntry();
        try{
            return connection;
        }catch (Exception e){
            logger.error("Connection lost", e);
            throw new RuntimeException(e);
        }


    }
}
