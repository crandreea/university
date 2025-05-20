package com.example.examen.utils;

import com.example.examen.domain.MenuItem;
import com.example.examen.domain.Order;
import com.example.examen.service.MenuItemsService;
import com.example.examen.service.OrderService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WindowManager {
    private static final MenuItemsService menuService = MenuItemsService.getInstance();

    private ListView<MenuItem> selectedItemsList;
    private ObservableList<MenuItem> selectedItems;
    int tableId;

    public WindowManager(int tableId) {
        this.tableId = tableId;
        Stage tableStage = new Stage();
        tableStage.setTitle("Table " + tableId);

        VBox layout = new VBox(10);

        Map<String, List<MenuItem>> groupedMenu = menuService.getMenuGroupedByCategory();

        selectedItems = FXCollections.observableArrayList(); // Inițializăm lista globală de selecții
        selectedItemsList = new ListView<>(selectedItems);

        for (String category : groupedMenu.keySet()) {
            Label categoryLabel = new Label(category);
            layout.getChildren().add(categoryLabel);

            TableView<MenuItem> tableView = createTableView(groupedMenu.get(category));
            layout.getChildren().add(tableView);
        }

        Button placeOrderButton = new Button("Place Order");
        placeOrderButton.setVisible(true);
        placeOrderButton.setOnAction(e -> placeOrder());

        layout.getChildren().addAll(new Label("Selected Items:"), selectedItemsList, placeOrderButton);

        Scene scene = new Scene(layout,400, 500);
        tableStage.setScene(scene);
        tableStage.show();
    }

    private TableView<MenuItem> createTableView(List<MenuItem> menuItems) {
        TableView<MenuItem> tableView = new TableView<>();
        ObservableList<MenuItem> data = FXCollections.observableArrayList(menuItems);

        TableColumn<MenuItem, String> nameColumn = new TableColumn<>("Item");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("item"));

        TableColumn<MenuItem, Float> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<MenuItem, String> currencyColumn = new TableColumn<>("Currency");
        currencyColumn.setCellValueFactory(new PropertyValueFactory<>("currency"));

        tableView.getColumns().addAll(nameColumn, priceColumn, currencyColumn);
        tableView.setItems(data);


        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableView.setOnMouseClicked(event -> {
            ObservableList<MenuItem> selected = tableView.getSelectionModel().getSelectedItems();
            addToSelectedItems(selected);
           // selectedItems.addAll(selected);

        });

        return tableView;
    }

    private void addToSelectedItems(ObservableList<MenuItem> items) {
        for (MenuItem item : items) {
            if (!selectedItems.contains(item)) {
                selectedItems.add(item); // Adăugăm produsul în lista globală dacă nu există deja
            }
        }
    }

    private void placeOrder() {
        if (selectedItems.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No items selected!", ButtonType.OK);
            alert.show();
            return;
        }

        OrderService orderService = OrderService.getInstance();
        List<MenuItem> items = new ArrayList<>(selectedItems);
        LocalDateTime date = LocalDateTime.now();
        Order order = new Order(tableId, items, date);
        orderService.save(order);

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Order placed successfully!", ButtonType.OK);
        alert.show();
        selectedItems.clear(); // Resetăm lista de selecție
    }
}
