package com.example.examen.repository.database;

import com.example.examen.database.DBConnection;
import com.example.examen.domain.MenuItem;
import com.example.examen.domain.Order;
import com.example.examen.domain.Table;
import com.example.examen.domain.validators.Validator;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderRepo extends AbstractDBRepo<Integer, Order> {
    private final Connection connection;

    public OrderRepo(Validator<Order> validator) {
        super(validator);
        this.connection = DBConnection.getInstance().getConnection();
    }

    @Override
    protected PreparedStatement findOneQuery(Integer integer) throws SQLException {
        String query = "SELECT * FROM orders WHERE id_o = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, integer);
        return statement;
    }

    @Override
    protected PreparedStatement findAllQuery() throws SQLException {
        String query = "SELECT * FROM orders";
        PreparedStatement statement = connection.prepareStatement(query);

        return statement;
    }

    @Override
    protected PreparedStatement saveQuery(Order entity) throws SQLException {
        String query = "INSERT INTO orders(id_t, date) VALUES (?,?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setObject(1, entity.getTable());
        statement.setTimestamp(2, java.sql.Timestamp.valueOf(entity.getDate()));

        return statement;
    }

    @Override
    protected PreparedStatement deleteQuery(Integer integer) throws SQLException {
        return null;
    }

    @Override
    protected PreparedStatement updateQuery(Order entity) throws SQLException {
        return null;
    }

    @Override
    protected Order buildEntity(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id_o");
        List<MenuItem> list = itemsByOrder(id);
        int id_t = resultSet.getInt("id_t");
        LocalDateTime date = resultSet.getTimestamp("date").toLocalDateTime();

        Order order = new Order(id_t, list, date);
        order.setId(id);
        return order;
    }

//    private List<MenuItem> itemsByOrder(Integer id) throws SQLException {
//        List<MenuItem> items = new ArrayList<>();
//        String query = "SELECT orderitems.id_m FROM orderitems WHERE id_o = ?";
//        PreparedStatement statement = connection.prepareStatement(query);
//        statement.setInt(1, id);
//        ResultSet resultSet = statement.executeQuery();
//        while (resultSet.next()) {
//            int idm = resultSet.getInt("id_m");
//
//            items.add(idm);
//        }
//        return items;
//    }

    private List<MenuItem> itemsByOrder(Integer id) throws SQLException {
        String query = "SELECT orderitems.id_m, menuitems.category, menuitems.item, menuitems.price, menuitems.currency " +
                "FROM orderitems " +
                "JOIN menuitems ON orderitems.id_m = menuitems.id_m " +
                "WHERE orderitems.id_o = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();  // Execută interogarea și obține rezultatele

        List<MenuItem> items = new ArrayList<>();  // Crează o listă goală de MenuItem
        while (resultSet.next()) {
            // Extrage datele pentru fiecare produs din ResultSet
            int itemId = resultSet.getInt("id_m");
            String category = resultSet.getString("category");
            String item = resultSet.getString("item");
            float price = resultSet.getFloat("price");
            String currency = resultSet.getString("currency");

            // Creează un obiect MenuItem și adaugă-l în listă
            MenuItem menuItem = new MenuItem(category, item, price, currency);
            menuItem.setId(itemId);
            items.add(menuItem);
        }

        return items;  // Returnează lista de MenuItem
    }

}
