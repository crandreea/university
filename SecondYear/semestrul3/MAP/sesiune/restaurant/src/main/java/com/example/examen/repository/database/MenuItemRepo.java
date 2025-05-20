package com.example.examen.repository.database;

import com.example.examen.database.DBConnection;
import com.example.examen.domain.MenuItem;
import com.example.examen.domain.Order;
import com.example.examen.domain.validators.Validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class MenuItemRepo extends AbstractDBRepo<Integer, MenuItem> {

    private final Connection connection;

    public MenuItemRepo(Validator<MenuItem> validator) {
        super(validator);
        this.connection = DBConnection.getInstance().getConnection();
    }

    @Override
    protected PreparedStatement findOneQuery(Integer integer) throws SQLException {
        String query = "SELECT * FROM menuitems WHERE id_m = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, integer);
        return statement;
    }

    @Override
    protected PreparedStatement findAllQuery() throws SQLException {
        String query = "SELECT * FROM menuitems";
        PreparedStatement statement = connection.prepareStatement(query);

        return statement;
    }

    @Override
    protected PreparedStatement saveQuery(MenuItem entity) throws SQLException {
        String query = "INSERT INTO menuitems(category, item, price, currency) VALUES (?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setObject(1, entity.getCategory());
        statement.setObject(2, entity.getItem());
        statement.setObject(3, entity.getPrice());
        statement.setObject(4, entity.getCurrency());
        return statement;
    }

    @Override
    protected PreparedStatement deleteQuery(Integer integer) throws SQLException {
        return null;
    }

    @Override
    protected PreparedStatement updateQuery(MenuItem entity) throws SQLException {
        return null;
    }

    @Override
    protected MenuItem buildEntity(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id_m");
        String category = resultSet.getString("category");
        String item = resultSet.getString("item");
        Float price = resultSet.getFloat("price");
        String currency = resultSet.getString("currency");

        MenuItem menuitem = new MenuItem(category, item, price, currency);
        menuitem.setId(id);
        return menuitem;
    }
}
