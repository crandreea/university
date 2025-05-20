package com.example.marire.controller;

import com.example.marire.utils.ViewUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class HelloController {

    @FXML
    public void handleAddClient(ActionEvent actionEvent) {
        try {
            ViewUtils.openView("ClientWindow", "booking-form.fxml", null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}