package ubb.scs.map.repository.database;

import ubb.scs.map.database.DBConnection;
import ubb.scs.map.domain.Utilizator;
import ubb.scs.map.domain.validators.UtilizatorValidator;

import java.sql.*;

public class UtilizatorDBRepository extends AbstractDBRepository<Long, Utilizator> {
    private final Connection connection;

    public UtilizatorDBRepository(UtilizatorValidator validator) {
        super(validator);
        this.connection = DBConnection.getInstance().getConnection();
    }

    @Override
    protected PreparedStatement findOneQuery(Long id) throws SQLException {
        String query = "SELECT * FROM utilizatori WHERE uid = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setObject(1, id);

        return statement;
    }

    @Override
    protected PreparedStatement findAllQuery() throws SQLException {
        String query = "SELECT * FROM utilizatori";
        PreparedStatement statement = connection.prepareStatement(query);

        return statement;
    }

    @Override
    protected PreparedStatement saveQuery(Utilizator entity) throws SQLException {
        String query = "INSERT INTO utilizatori(uid, username, password) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setObject(1, entity.getId());
        statement.setString(2, entity.getUsername());
        statement.setString(3, entity.getPassword());

        return statement;
    }

    @Override
    protected PreparedStatement deleteQuery(Long id) throws SQLException {
        String query = "DELETE FROM utilizatori WHERE uid = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setObject(1, id);

        return statement;
    }

    @Override
    protected PreparedStatement updateQuery(Utilizator entity) throws SQLException {
        return null;
    }

    @Override
    protected Utilizator buildEntity(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("uid");
        String username = resultSet.getString("username");
        String password = resultSet.getString("password");

        Utilizator user = new Utilizator(username, password);
        user.setId(id);

        return user;
    }
}
