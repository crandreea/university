package com.example.examen.repository.database;

import com.example.examen.database.DBConnection;
import com.example.examen.domain.Flight;
import com.example.examen.domain.Ticket;
import com.example.examen.domain.validators.Validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FlightRepository extends AbstractDBRepo<Long, Flight>{
    private final Connection connection;

    public FlightRepository(Validator<Flight> validator) {
        super(validator);
        this.connection = DBConnection.getInstance().getConnection();
    }

    @Override
    protected PreparedStatement findOneQuery(Long aLong) throws SQLException {
        return null;
    }

    @Override
    protected PreparedStatement findAllQuery() throws SQLException {
        String query = "SELECT * FROM zboruri";
        return connection.prepareStatement(query);
    }

    @Override
    protected PreparedStatement saveQuery(Flight entity) throws SQLException {
        return null;
    }

    @Override
    protected PreparedStatement deleteQuery(Long aLong) throws SQLException {
        return null;
    }

    @Override
    protected PreparedStatement updateQuery(Flight entity) throws SQLException {
        return null;
    }

    @Override
    protected Flight buildEntity(ResultSet resultSet) throws SQLException {
        return null;
    }


}
