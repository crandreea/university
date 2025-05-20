package com.example.sub1.repository.dbRepo;

import com.example.sub1.database.DBConnection;
import com.example.sub1.domain.Persoana;
import com.example.sub1.domain.validators.PersoanaValidator;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersoanaRepository extends AbstractRepo<Long, Persoana>{

    private final Connection connection;

    public PersoanaRepository(PersoanaValidator validator) {
        super(validator);
        this.connection = DBConnection.getInstance().getConnection();
    }

    @Override
    protected PreparedStatement findOneQuery(Long id) throws SQLException {
        String query = "SELECT * FROM persoane WHERE id_p = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setObject(1, id);

        return statement;
    }

    @Override
    protected PreparedStatement findAllQuery() throws SQLException {
        String query = "SELECT * FROM persoane";
        PreparedStatement statement = connection.prepareStatement(query);

        return statement;
    }

    @Override
    protected PreparedStatement saveQuery(Persoana entity) throws SQLException {
        String query = "INSERT INTO persoane(name, surname, username, password, oras, strada, nrstrada, telefon) VALUES (?, ?, ?, ?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setObject(1, entity.getName());
        statement.setString(2, entity.getSurname());
        statement.setString(3, entity.getUsername());
        statement.setString(4, entity.getPassword());
        statement.setString(5, entity.getOras());
        statement.setString(6, entity.getStrada());
        statement.setString(7, entity.getNrstrada());
        statement.setString(8, entity.getTelefon());

        return statement;
    }

    @Override
    protected PreparedStatement deleteQuery(Long id) throws SQLException {
        String query = "DELETE FROM persoane WHERE id_p = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setObject(1, id);

        return statement;
    }

    @Override
    protected PreparedStatement updateQuery(Persoana entity) throws SQLException {
        return null;
    }


    @Override
    protected Persoana buildEntity(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id_p");
        String name = resultSet.getString("name");
        String surname = resultSet.getString("surname");
        String username = resultSet.getString("username");
        String password = resultSet.getString("password");
        String oras = resultSet.getString("oras");
        String strada = resultSet.getString("strada");
        String nrstrada = resultSet.getString("nrstrada");
        String telefon = resultSet.getString("telefon");


        Persoana user = new Persoana(name, surname, username, password, oras, strada, nrstrada, telefon);
        user.setId(id);

        return user;
    }


}
