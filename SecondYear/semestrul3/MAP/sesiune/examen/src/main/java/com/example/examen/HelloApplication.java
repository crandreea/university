package com.example.examen;

import com.example.examen.domain.Driver;
import com.example.examen.domain.Order;
import com.example.examen.domain.validators.DriverValidator;
import com.example.examen.domain.validators.OrderValidator;
import com.example.examen.domain.validators.ValidationException;
import com.example.examen.domain.validators.Validator;
import com.example.examen.repository.database.DriverRepo;
import com.example.examen.repository.database.OrderRepo;
import com.example.examen.service.DriverService;
import com.example.examen.service.OrderService;
import com.example.examen.utils.WindowManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class HelloApplication extends Application {

    private static final OrderRepo oredrRepo = new OrderRepo(new OrderValidator());
    private static final OrderService orderService = OrderService.getInstance(oredrRepo);
    private static final DriverRepo driverRepo = new DriverRepo(new DriverValidator());
    private static final DriverService driverService = DriverService.getInstance(driverRepo);
    private WindowManager windowManager = new WindowManager();

    @Override
    public void start(Stage stage) throws IOException {
        try {
            List<Driver> drivers = driverService.getAllDrivers();

            for (Driver driver : drivers) {
                List<Order> activeOrders = orderService.activeOrdersByIdDriverId(driver.getId());
                System.out.println(activeOrders);
                windowManager.showDriverWindow(driver, activeOrders);
            }

            windowManager.showAddOrderWindow();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}