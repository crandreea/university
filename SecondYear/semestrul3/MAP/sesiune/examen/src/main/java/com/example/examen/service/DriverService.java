package com.example.examen.service;

import com.example.examen.database.DBConnection;
import com.example.examen.domain.Driver;
import com.example.examen.repository.database.DriverRepo;
import com.example.examen.utils.observer.ObservableImplementation;
import com.example.examen.utils.observer.Observer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DriverService extends ObservableImplementation {

    private static DriverService service = null;
    private final DriverRepo repository;

    private DriverService(DriverRepo repository) {
        this.repository = repository;
    }

    public static DriverService getInstance(DriverRepo repository) {
        if (service == null) {
            service = new DriverService(repository);
        }
        return service;
    }
    public Driver getDriverByName(String name) {
        return repository.getDriverByName(name);
    }

    public List<Driver> getAllDrivers() {
        return (List<Driver>) repository.findAll();
    }


    public Integer getDriverIdByName(String driverName) throws SQLException {
        String query = "SELECT id FROM soferi WHERE name = ?";

        try (PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(query)) {
            statement.setString(1, driverName);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("id");
            } else {

                return null;
            }
        }
    }
}
