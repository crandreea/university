package com.example.examen.repository.database;

import com.example.examen.database.DBConnection;
import com.example.examen.domain.Ticket;
import com.example.examen.domain.validators.Validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketRepository extends AbstractDBRepo<Long, Ticket>{
    private final Connection connection;

    public TicketRepository(Validator<Ticket> validator) {
        super(validator);
        this.connection = DBConnection.getInstance().getConnection();
    }

    @Override
    protected PreparedStatement findOneQuery(Long aLong) throws SQLException {
        return null;
    }

    @Override
    protected PreparedStatement findAllQuery() throws SQLException {
        String query = "SELECT * FROM bilete";
        return connection.prepareStatement(query);
    }

    @Override
    protected PreparedStatement saveQuery(Ticket entity) throws SQLException {
        return null;
    }

    @Override
    protected PreparedStatement deleteQuery(Long aLong) throws SQLException {
        return null;
    }

    @Override
    protected PreparedStatement updateQuery(Ticket entity) throws SQLException {
        return null;
    }

    @Override
    protected Ticket buildEntity(ResultSet resultSet) throws SQLException {
        return null;
    }
}
