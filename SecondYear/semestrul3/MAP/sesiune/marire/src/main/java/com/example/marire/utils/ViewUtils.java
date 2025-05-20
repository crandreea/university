package com.example.marire.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;

public class ViewUtils {
    public static void openView(String viewName, String fxmlPath, Map<String, Object> params) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(ViewUtils.class.getResource("/com/example/marire/" + fxmlPath));
        Parent root = loader.load();

        Scene scene = new Scene(root, 600, 800);
        stage.setScene(scene);
        stage.setTitle(viewName);
        stage.show();
    }

    public static void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
