module com.example.marire {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.dotenv;
    requires java.sql;


    opens com.example.marire to javafx.fxml;
    opens com.example.marire.domain to javafx.fxml;
    exports com.example.marire;
    exports com.example.marire.controller;
    opens com.example.marire.controller to javafx.fxml;
}