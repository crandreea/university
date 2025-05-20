package com.example.examen.repository.database;

import com.example.examen.database.DBConnection;
import com.example.examen.domain.Driver;
import com.example.examen.domain.validators.Validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DriverRepo extends AbstractDBRepo<Integer, Driver> {

    private final Connection connection;

    public DriverRepo(Validator<Driver> validator) {
        super(validator);
        this.connection = DBConnection.getInstance().getConnection();
    }

    @Override
    protected PreparedStatement findOneQuery(Integer integer) throws SQLException {
        String query = "SELECT * FROM soferi WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, integer);
        return preparedStatement;
    }

    @Override
    protected PreparedStatement findAllQuery() throws SQLException {
        String query = "SELECT * FROM soferi";
        return connection.prepareStatement(query);
    }

    @Override
    protected PreparedStatement saveQuery(Driver entity) throws SQLException {
        return null;
    }

    @Override
    protected PreparedStatement deleteQuery(Integer integer) throws SQLException {
        return null;
    }

    @Override
    protected PreparedStatement updateQuery(Driver entity) throws SQLException {
        return null;
    }

    @Override
    protected Driver buildEntity(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString("name");
        Integer id = resultSet.getInt("id");

        Driver driver = new Driver(name);
        driver.setId(id);
        return driver;
    }


    public Driver getDriverByName(String name) {
        String query = "SELECT * FROM soferi WHERE name = ?"; // Adjust table name if necessary

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Driver(
                        resultSet.getString("name")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception properly in production code
        }
        return null; // Return null if no driver is found
    }
}
