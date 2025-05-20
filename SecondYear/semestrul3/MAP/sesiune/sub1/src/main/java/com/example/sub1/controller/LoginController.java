package com.example.sub1.controller;

import com.example.sub1.domain.Persoana;
import com.example.sub1.service.GlobalService;
import com.example.sub1.service.Methods;
import com.example.sub1.service.PersoanaService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;


public class LoginController {
    @FXML public ListView userListView;
    public Button buttonAutentificare;
    @FXML private TabPane tabPane;
    @FXML private TextField nameField;
    @FXML private TextField surnameField;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private ComboBox<Oras> orasComboBox;
    @FXML private TextField stradaField;
    @FXML private TextField nrStradaField;
    @FXML private TextField telefonField;
    private Methods network;



    public LoginController() {
    }

    @FXML
    public void initialize() {
        network = GlobalService.getNetwork();
        orasComboBox.getItems().addAll(Oras.values());
        buttonAutentificare.setVisible(true);
        loadUsers();
    }

    public enum Oras {
        BUCURESTI, CLUJ, TIMISOARA, IASI, BRASOV
    }


    @FXML
    private void handleRegistration() {
        if (orasComboBox.getValue() == null) {
            showAlert("Error", "Please select a city");
            return;
        }

        Persoana newUser = new Persoana(
                nameField.getText(),
                surnameField.getText(),
                usernameField.getText(),
                passwordField.getText(),
                orasComboBox.getValue().toString(),
                stradaField.getText(),
                nrStradaField.getText(),
                telefonField.getText()
        );

        network.savePersoana(newUser);
        showAlert("Success", "Registration successful!");
        clearFields();
        loadUsers();
    }

    @FXML
    private void handleLogin() {
        String selectedUsername = (String) userListView.getSelectionModel().getSelectedItem();
        if (selectedUsername != null) {
            Persoana persoana = StreamSupport.stream(network.findAllPersoane().spliterator(), false)
                    .filter(p -> p.getUsername().equals(selectedUsername))
                    .findFirst().orElse(null);
            GlobalUser.setPersoana(persoana);

            showAlert("Success", "Logged in as: " + selectedUsername);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/sub1/hello-view.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage stage = new Stage();
            stage.setTitle("FapteBune");
            stage.setScene(scene);
            stage.show();
        }
        else showAlert("Error", "Please select an user");
    }

    private void loadUsers() {
        userListView.getItems().clear();
        network.findAllPersoane().forEach(user ->
                userListView.getItems().add(user.getUsername())
        );
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void clearFields() {
        nameField.clear();
        surnameField.clear();
        usernameField.clear();
        passwordField.clear();
        stradaField.clear();
        nrStradaField.clear();
        telefonField.clear();
        orasComboBox.setValue(null);
    }
}