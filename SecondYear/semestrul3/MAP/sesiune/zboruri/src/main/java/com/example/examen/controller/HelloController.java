package com.example.examen.controller;

import com.example.examen.domain.Client;
import com.example.examen.service.ClientService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class HelloController {
    public TextField usernameField;
    public Button loginButton;

    private ClientService clientService = ClientService.getInstance();
    @FXML
    public void handleMouseClicked(ActionEvent actionEvent) throws SQLException {
        String username = usernameField.getText().trim();
        if (!username.isEmpty()) {
            Client client = clientService.findByUsername(username);
            if (client == null) {
                showAlert("Login Failed", "User does not exist in the database.");
                return;
            }
            openWelcomeWindow(username);
        }
    }

    private void showAlert(String title, String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(s);
        alert.showAndWait();
    }

    private void openWelcomeWindow(String username) {
        try {
            Stage welcomeStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/examen/welcome.fxml"));
            VBox root = loader.load();

            WelcomeController welcomeController = loader.getController();
            welcomeController.setUsername(username);

            welcomeStage.setTitle("Welcome");
            welcomeStage.setScene(new Scene(root, 300, 150));
            welcomeStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}