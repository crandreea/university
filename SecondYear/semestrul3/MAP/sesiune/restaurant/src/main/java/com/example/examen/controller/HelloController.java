package com.example.examen.controller;

import com.example.examen.domain.Order;
import com.example.examen.service.OrderService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HelloController {
    @FXML
    private TableView<Order> ordersTable;
    @FXML
    private TableColumn<Order, Integer> tableIdColumn;
    @FXML
    private TableColumn<Order, String> dateColumn;
    @FXML
    private TableColumn<Order, String> itemsColumn;

    private OrderService orderService = OrderService.getInstance();
    private ObservableList<Order> orders = FXCollections.observableArrayList();

    // Metoda pentru a încărca comenzile
    private void loadOrders() {
        List<Order> placedOrders = orderService.getAllPlacedOrders(); // Obținem comenzile din serviciu
        orders.setAll(placedOrders); // Setăm comenzile în lista observabilă

        // Actualizăm tabelul
        ordersTable.setItems(orders);
    }
    private Timer timer = new Timer();

    @FXML
    private void initialize() {
        // Inițializăm coloanele
        tableIdColumn.setCellValueFactory(new PropertyValueFactory<>("table"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        itemsColumn.setCellValueFactory(new PropertyValueFactory<>("items"));

        loadOrders(); // Încarcă comenzile la început
        // Actualizăm comenzile la fiecare 5 secunde
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                loadOrders(); // Reîncărcăm comenzile
            }
        }, 0, 5000);

        // Setăm un converter personalizat pentru items (dacă este necesar să formatezi produsele într-un format mai prietenos)
        itemsColumn.setCellFactory(new Callback<TableColumn<Order, String>, javafx.scene.control.TableCell<Order, String>>() {
            @Override
            public TableCell<Order, String> call(TableColumn<Order, String> param) {
                return new TableCell<Order, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            // Folosește un format custom pentru lista de produse
                            setText(String.join(", ", item.split(",")));
                        }
                    }
                };
            }
        });

        // Încărcăm comenzile inițial
        loadOrders();
    }


}