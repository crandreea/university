module com.example.examen {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.dotenv;


    opens com.example.examen to javafx.fxml;
    opens com.example.examen.domain to javafx.base;
    exports com.example.examen;
    exports com.example.examen.controller;
    opens com.example.examen.controller to javafx.fxml;
}