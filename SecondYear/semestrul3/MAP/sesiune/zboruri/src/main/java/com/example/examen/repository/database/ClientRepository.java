package com.example.examen.repository.database;

import com.example.examen.database.DBConnection;
import com.example.examen.domain.Client;
import com.example.examen.domain.validators.Validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientRepository extends AbstractDBRepo<Long, Client>{

    private final Connection connection;

    public ClientRepository(Validator<Client> validator) {
        super(validator);
        this.connection = DBConnection.getInstance().getConnection();
    }

    @Override
    protected PreparedStatement findOneQuery(Long aLong) throws SQLException {
        return null;
    }

    @Override
    protected PreparedStatement findAllQuery() throws SQLException {
        String query = "SELECT * FROM clienti";
        return connection.prepareStatement(query);
    }

    @Override
    protected PreparedStatement saveQuery(Client entity) throws SQLException {
        return null;
    }

    @Override
    protected PreparedStatement deleteQuery(Long aLong) throws SQLException {
        return null;
    }

    @Override
    protected PreparedStatement updateQuery(Client entity) throws SQLException {
        return null;
    }

    @Override
    protected Client buildEntity(ResultSet resultSet) throws SQLException {
        return null;
    }

    public Client findByUsername(String username) throws SQLException {
        String query = "SELECT * FROM clienti WHERE username = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, username);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return new Client(resultSet.getString("name"), resultSet.getString("username"));
        }
        return null;
    }
}
