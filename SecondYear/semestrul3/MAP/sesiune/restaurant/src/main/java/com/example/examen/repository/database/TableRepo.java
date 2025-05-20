package com.example.examen.repository.database;

import com.example.examen.database.DBConnection;
import com.example.examen.domain.Table;
import com.example.examen.domain.validators.Validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TableRepo extends AbstractDBRepo<Integer, Table> {
    private final Connection connection;

    public TableRepo(Validator<Table> validator) {
        super(validator);
        this.connection = DBConnection.getInstance().getConnection();
    }

    @Override
    protected PreparedStatement findOneQuery(Integer integer) throws SQLException {
        String query = "SELECT * FROM tables WHERE id_t = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, integer);
        return statement;
    }

    @Override
    protected PreparedStatement findAllQuery() throws SQLException {
        String query = "SELECT * FROM tables";
        PreparedStatement statement = connection.prepareStatement(query);

        return statement;
    }

    @Override
    protected PreparedStatement saveQuery(Table entity) throws SQLException {
       return null;
    }

    @Override
    protected PreparedStatement deleteQuery(Integer integer) throws SQLException {
        return null;
    }

    @Override
    protected PreparedStatement updateQuery(Table entity) throws SQLException {
        return null;
    }

    @Override
    protected Table buildEntity(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id_t");
        Table table = new Table();
        table.setId(id);
        return table;
    }
}
