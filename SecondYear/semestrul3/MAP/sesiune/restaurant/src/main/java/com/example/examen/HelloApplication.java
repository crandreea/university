package com.example.examen;

import com.example.examen.domain.Table;
import com.example.examen.service.TableService;
import com.example.examen.utils.WindowManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application {
    private final TableService tableService = TableService.getInstance();
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Staff");
        stage.setScene(scene);
        stage.show();

        List<Table> tableIds = tableService.getTables();
        List<Integer> tables = new ArrayList<>();
        for (Table table : tableIds) {
            tables.add(table.getId());
        }

        // Creează o fereastră pentru fiecare masă folosind clasa TableViewWindow
        for (int tableId : tables) {
            new WindowManager(tableId);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}