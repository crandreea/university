module com.example.sub1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.dotenv;


    opens com.example.sub1 to javafx.fxml;
    opens com.example.sub1.controller to javafx.fxml;

    exports com.example.sub1;
    exports com.example.sub1.controller;
}