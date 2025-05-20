package com.example.examen.utils;


import com.example.examen.domain.Driver;
import com.example.examen.domain.Order;
import com.example.examen.domain.validators.DriverValidator;
import com.example.examen.domain.validators.OrderValidator;
import com.example.examen.repository.database.DriverRepo;
import com.example.examen.repository.database.OrderRepo;
import com.example.examen.service.DriverService;
import com.example.examen.service.OrderService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class WindowManager {


    private static final OrderRepo oredrRepo = new OrderRepo(new OrderValidator());
    private static final OrderService orderService = OrderService.getInstance(oredrRepo);

    private static final DriverRepo driverRepo = new DriverRepo(new DriverValidator());
    private static final DriverService driverService = DriverService.getInstance(driverRepo);



    public void showDriverWindow(Driver driver, List<Order> activeOrders) throws SQLException {
        Stage stage = new Stage();
        stage.setTitle("Șofer: " + driver.getName());

        Label nameLabel = new Label("Șofer: " + driver.getName());
        nameLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");


        ObservableList<HBox> ordersList = FXCollections.observableArrayList();

        System.out.print(activeOrders);
        for (Order order : activeOrders) {
            HBox orderHBox = new HBox(10);

            Label orderLabel = new Label("Destinație: " + order.getDestinationAddress() + " | Start: " + order.getStartDate());
            orderLabel.setStyle("-fx-font-size: 14px;");

            Button finishButton = new Button("Finish");
            finishButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");

            if (order.getId() != null) {
                orderService.markOrderAsFinished(order.getId());
                ordersList.remove(orderHBox);
            } else {
                System.out.println("Order ID is null for order: " + order.getId());
            }
            finishButton.setOnAction(event -> {
                orderService.markOrderAsFinished(order.getId());
                ordersList.remove(orderHBox);
            });

            orderHBox.getChildren().addAll(orderLabel, finishButton);

            ordersList.add(orderHBox);


        ListView<HBox> ordersListView = new ListView<>(ordersList);
        ordersListView.setPrefHeight(300);  // Setează o înălțime pentru ListView

        VBox layout = new VBox(10, nameLabel, ordersListView);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 15px;");

        stage.setScene(new Scene(layout, 350, 400));
        stage.show();
        }
    }

    public void showAddOrderWindow() {
        Stage stage = new Stage();
        stage.setTitle("Adăugare comandă");


        Label pickupAddressLabel = new Label("Adresă Plecare:");
        Label destinationAddressLabel = new Label("Adresă Destinație:");
        Label clientNameLabel = new Label("Nume Client:");

        TextField pickupAddressField = new TextField();
        pickupAddressField.setPromptText("Introduceți adresa de plecare");

        TextField destinationAddressField = new TextField();
        destinationAddressField.setPromptText("Introduceți adresa de destinație");

        TextField clientNameField = new TextField();
        clientNameField.setPromptText("Introduceți numele clientului");

        Button addButton = new Button("Adaugă comandă");
        addButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");


        addButton.setOnAction(event -> {
            String pickupAddress = pickupAddressField.getText();
            String destinationAddress = destinationAddressField.getText();
            String clientName = clientNameField.getText();

            if (pickupAddress.isEmpty() || destinationAddress.isEmpty() || clientName.isEmpty()) {
                showError("Toate câmpurile sunt obligatorii!");
            } else {
                Driver driver = driverService.getDriverByName(clientName);
                Integer id = null;
                try {
                    id = driverService.getDriverIdByName(clientName);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(id);
                System.out.println(driver);

                Order newOrder = new Order(1, "PENDING", LocalDateTime.now(), LocalDateTime.now(), pickupAddress, destinationAddress, clientName); // ID-ul și alte date pot fi setate în funcție de logica ta
                try {
                    orderService.addOrder(newOrder);
                    showError("Adaugarea a fost realizata!");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }


            }
        });

        VBox layout = new VBox(10, pickupAddressLabel, pickupAddressField, destinationAddressLabel, destinationAddressField, clientNameLabel, clientNameField, addButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 15px;");

        Scene scene = new Scene(layout, 350, 300);
        stage.setScene(scene);
        stage.show();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("MESAJ");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }




}

