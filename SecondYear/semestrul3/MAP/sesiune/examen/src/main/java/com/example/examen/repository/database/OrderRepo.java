package com.example.examen.repository.database;

import com.example.examen.database.DBConnection;
import com.example.examen.domain.Driver;
import com.example.examen.domain.Order;
import com.example.examen.domain.validators.Validator;
import javafx.beans.property.LongProperty;
import javafx.beans.property.StringProperty;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderRepo extends AbstractDBRepo<Integer, Order>{

    private final Connection connection;

    public OrderRepo(Validator<Order> validator) {
        super(validator);
        this.connection = DBConnection.getInstance().getConnection();
    }

    @Override
    protected PreparedStatement findOneQuery(Integer integer) throws SQLException {
        String query = "SELECT * FROM comenzi WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, integer);
        return preparedStatement;
    }

    @Override
    protected PreparedStatement findAllQuery() throws SQLException {
        return null;
    }

    @Override
    protected PreparedStatement saveQuery(Order entity) throws SQLException {
        String query = "INSERT INTO comenzi (driverid, status, startdate, enddate, pickupaddress, destinationaddress, clientname) VALUES (?, ?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, entity.getDriverId());
        preparedStatement.setString(2, entity.getStatus());
        preparedStatement.setTimestamp(3, Timestamp.valueOf(entity.getStartDate()));
        preparedStatement.setTimestamp(4, Timestamp.valueOf(entity.getEndDate()));
        preparedStatement.setString(5, entity.getPickupAddress());
        preparedStatement.setString(6, entity.getDestinationAddress());
        preparedStatement.setString(7, entity.getClientName());
        return preparedStatement;
    }

    @Override
    protected PreparedStatement deleteQuery(Integer integer) throws SQLException {
        return null;
    }

    @Override
    protected PreparedStatement updateQuery(Order entity) throws SQLException {
        String query = "UPDATE comenzi SET status = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, entity.getStatus());
        preparedStatement.setLong(2, entity.getId());
        return preparedStatement;
    }

    @Override
    protected Order buildEntity(ResultSet resultSet) throws SQLException {
        Integer id = resultSet.getInt("id");
        String status = resultSet.getString("status");
        Integer idDriver = resultSet.getInt("driverid");
        String pickupAddress = resultSet.getString("pickupaddress");
        String destinationAddress = resultSet.getString("destinationaddress");
        String clientName = resultSet.getString("clientname");
        LocalDateTime startDate = resultSet.getTimestamp("startdate").toLocalDateTime();
        LocalDateTime endDate = null;
        if(resultSet.getTimestamp("enddate") != null){
            endDate = resultSet.getTimestamp("enddate").toLocalDateTime();
        }

        Order order = new Order(idDriver, status, startDate, endDate, pickupAddress, destinationAddress, clientName);
        order.setId(id);
        return order;
    }


    public List<Order> getActiveOrdersByDriverId(Integer driverId) throws SQLException {
        List<Order> activeOrders = new ArrayList<>();
        String query = "SELECT * FROM comenzi WHERE driverid = ? AND status = 'IN_PROGRESS'";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, driverId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Order order = new Order(
                        resultSet.getInt("driverid"),
                        resultSet.getString("status"),
                        resultSet.getTimestamp("startdate").toLocalDateTime(),
                        resultSet.getTimestamp("enddate") != null ? resultSet.getTimestamp("enddate").toLocalDateTime() : null,
                        resultSet.getString("pickupaddress"),
                        resultSet.getString("destinationaddress"),
                        resultSet.getString("clientname")
                );
                order.setId(resultSet.getInt("id"));
                activeOrders.add(order);
            }
        }
        return activeOrders;
    }


}
