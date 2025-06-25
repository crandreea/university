package persistence.database;

import model.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import persistence.AbstractRepository;
import persistence.interfaces.IConfigurationRepo;
import persistence.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConfigurationRepo extends AbstractRepository<Integer, Configuration> implements IConfigurationRepo {
    private static final Logger logger= LogManager.getLogger(ConfigurationRepo.class);

    private final Connection connection;

    public ConfigurationRepo() throws SQLException {
        logger.info("Initializing OrganizatorRepository");
        this.connection = JdbcUtils.getInstance().getConnection();
    }

    @Override
    protected PreparedStatement findOneQuery(Integer id) throws SQLException {
        String query = "SELECT * FROM configurations WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setObject(1, id);

        return statement;
    }

    @Override
    protected PreparedStatement findAllQuery() throws SQLException {
        String query = "SELECT * FROM configurations";
        return connection.prepareStatement(query);
    }

    @Override
    protected PreparedStatement saveQuery(Configuration entity) throws SQLException {
        String query = "INSERT INTO configurations default values";
        return connection.prepareStatement(query);
    }

    @Override
    protected Configuration buildEntity(ResultSet resultSet) throws SQLException {
        Integer id = resultSet.getInt("id");
        Configuration configuration = new Configuration();
        configuration.setId(id);
        return configuration;
    }


}
