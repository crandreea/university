package persistence.database;

import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import persistence.AbstractRepository;
import persistence.interfaces.IUserRepo;
import persistence.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepo extends AbstractRepository<Integer, User> implements IUserRepo {

    private static final Logger logger= LogManager.getLogger(UserRepo.class);

    private final Connection connection;

    public UserRepo() throws SQLException {
        logger.info("Initializing OrganizatorRepository");
        this.connection = JdbcUtils.getInstance().getConnection();
    }

    @Override
    protected PreparedStatement findOneQuery(Integer id) throws SQLException {
        String query = "SELECT * FROM users WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setObject(1, id);

        return statement;
    }

    @Override
    protected PreparedStatement findAllQuery() throws SQLException {
        String query = "SELECT * FROM users";
        return connection.prepareStatement(query);
    }

    @Override
    protected PreparedStatement saveQuery(User entity) throws SQLException {
        String query = "INSERT INTO users(id, username, password) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setObject(1, entity.getId());
        statement.setString(2, entity.getUsername());
        statement.setString(3, entity.getPassword());
        return statement;
    }

    @Override
    protected User buildEntity(ResultSet resultSet) throws SQLException {
        Integer id = resultSet.getInt("id");
        String username = resultSet.getString("username");
        String password= resultSet.getString("password");

        User organizator = new User(username, password);
        organizator.setId(id);

        return organizator;
    }

}
