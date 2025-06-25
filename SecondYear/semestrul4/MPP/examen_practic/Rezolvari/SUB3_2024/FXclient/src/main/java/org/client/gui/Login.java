package org.client.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import org.services.ProjectServices;

import java.io.IOException;

public class Login {
    private ProjectServices server;
    private Home homeCtrl;
    private User crtUser;

    Parent mainParent;

    public TextField usernameField;
    public PasswordField passwordField;

    public void setServer(ProjectServices s){
        server=s;
    }

    public void setParent(Parent p){
        mainParent=p;
    }

    public void setUser(User user) {
        this.crtUser = user;
    }

    public void setMainController(Home controller) {
        this.homeCtrl = controller;
    }

    public void handleConfirmLoginClick(ActionEvent actionEvent) throws IOException {
        String username = usernameField.getText().strip();
        String password = passwordField.getText().strip();

        Stage stage = (Stage) usernameField.getScene().getWindow();

        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/main.fxml"));
        Parent newRoot = fxmlloader.load();

        homeCtrl = fxmlloader.getController();

        try{
            crtUser = server.login(username, password, homeCtrl);

            if (crtUser != null) {
                homeCtrl.setServer(server);
                homeCtrl.setUser(crtUser);

                //homeCtrl.loadDataFromServer();

                stage.setScene(new Scene(newRoot));
                stage.setMaximized(true);
                stage.setTitle("Home Page");
                stage.show();
            } else {
                PopupNotification.showNotification(stage, "Invalid username or password", 3000, "#ef5356");
            }
        }catch (Exception e){
            PopupNotification.showNotification(stage, e.getMessage(), 3000, "#ef5356");
        }
    }
}
