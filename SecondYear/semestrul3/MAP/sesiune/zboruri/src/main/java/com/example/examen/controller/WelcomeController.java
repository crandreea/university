package com.example.examen.controller;

import com.example.examen.domain.Flight;
import com.example.examen.service.FlightService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WelcomeController {
    public Button buyButton;
    @FXML
    private ComboBox<String> fromComboBox, toComboBox;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button searchButton;
    @FXML
    private TableView<Flight> flightsTable;
    @FXML
    private TableColumn<Flight, String> fromColumn, toColumn, departureColumn, landingColumn;

    private FlightService flightService = FlightService.getInstance();
    @FXML
    private Label welcomeLabel;

    @FXML
    public void initialize() {
        ObservableList<String> places = FXCollections.observableArrayList(
                "Bucharest", "Cluj", "Iași", "Timișoara", "Constanța", "Brașov"
        );

        fromComboBox.setItems(places);
        toComboBox.setItems(places);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:dd");


        // Set up table columns to show flight data
        fromColumn.setCellValueFactory(cellData -> cellData.getValue().fromProperty());
        toColumn.setCellValueFactory(cellData -> cellData.getValue().toProperty());
        departureColumn.setCellValueFactory(cellData -> cellData.getValue().departureTimeProperty().asString());

        landingColumn.setCellValueFactory(cellData -> cellData.getValue().landingTimeProperty().asString());
    }


    public void setUsername(String username) {
        welcomeLabel.setText("Welcome, " + username + "!");
    }

    @FXML
    public void handleSearch() {
        String fromLocation = fromComboBox.getValue();
        String toLocation = toComboBox.getValue();
        java.time.LocalDate selectedDate = datePicker.getValue();

        if (fromLocation == null || toLocation == null || selectedDate == null) {
            showAlert("Error", "Please select all fields.");
            return;
        }

        try {
            ObservableList<Flight> flights = flightService.searchFlights(
                    fromLocation, toLocation, selectedDate);
            flightsTable.setItems(flights);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void handleBuyTicket(ActionEvent actionEvent) {
    }
}
