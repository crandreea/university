package com.example.sub1.controller;

import com.example.sub1.domain.Nevoi;
import com.example.sub1.service.GlobalService;
import com.example.sub1.service.Methods;
import com.example.sub1.utils.observer.Observable;
import com.example.sub1.utils.observer.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class HelloController implements Observer {

    @FXML private TabPane tabPane;

    @FXML private TableView<Nevoi> tableNevoi;
    @FXML private TableColumn<Nevoi, String> colTitlu;
    @FXML private TableColumn<Nevoi, String> colDescriere;
    @FXML private TableColumn<Nevoi, String> colStatus;
    @FXML private TableColumn<Nevoi, LocalDateTime> colDeadline;
    @FXML private TableColumn<Nevoi, Long> colOmInNevoie;
    @FXML private TableColumn<Nevoi, Long> colOmSalvator;

    @FXML private TableView<Nevoi> tableFapteBune;
    @FXML private TableColumn<Nevoi, String> colTitlu1;
    @FXML private TableColumn<Nevoi, String> colDescriere1;
    @FXML private TableColumn<Nevoi, String> colStatus1;
    @FXML private TableColumn<Nevoi, LocalDateTime> colDeadline1;
    @FXML private TableColumn<Nevoi, Long> colOmInNevoie1;
    @FXML private TableColumn<Nevoi, Long> colOmSalvator1;

    @FXML private TextField fieldTitlu;
    @FXML private TextArea fieldDescriere;
    @FXML private DatePicker datePickerDeadline;
    @FXML private Button btnSalveaza;

    private Methods network;

    @FXML
    public void initialize() throws SQLException {
        network = GlobalService.getNetwork();
        network.addObserver(this);
        this.update(null);

        colTitlu.setCellValueFactory(cellData -> cellData.getValue().titluProperty());
        colDescriere.setCellValueFactory(cellData -> cellData.getValue().descriereProperty());
        colStatus.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        colDeadline.setCellValueFactory(cellData -> cellData.getValue().deadlineProperty());
        colOmInNevoie.setCellValueFactory(cellData -> cellData.getValue().omInNevoieProperty());
        colOmSalvator.setCellValueFactory(cellData -> cellData.getValue().omSalvatorProperty());

        colTitlu1.setCellValueFactory(cellData -> cellData.getValue().titluProperty());
        colDescriere1.setCellValueFactory(cellData -> cellData.getValue().descriereProperty());
        colStatus1.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        colDeadline1.setCellValueFactory(cellData -> cellData.getValue().deadlineProperty());
        colOmInNevoie1.setCellValueFactory(cellData -> cellData.getValue().omInNevoieProperty());
        colOmSalvator1.setCellValueFactory(cellData -> cellData.getValue().omSalvatorProperty());

        tableNevoi.setOnMouseClicked(event -> {
            Nevoi selectedNevoie = tableNevoi.getSelectionModel().getSelectedItem();
            if (selectedNevoie != null) {
                showConfirmationDialog(selectedNevoie);
            }
        });

        btnSalveaza.setOnAction(event -> handleSave());
    }

    private void handleSave() {
        String titlu = fieldTitlu.getText();
        String descriere = fieldDescriere.getText();
        LocalDateTime deadline = datePickerDeadline.getValue() != null
                ? datePickerDeadline.getValue().atStartOfDay()
                : null;

        if (titlu.isEmpty() || descriere.isEmpty() || deadline == null) {
            showAlert("Eroare", "Toate câmpurile sunt obligatorii!", Alert.AlertType.ERROR);
        } else {
            Nevoi nevoie = new Nevoi(titlu, descriere, "Caut erou!", deadline, GlobalUser.getPersoana().getId(), 0L );
            network.saveNevoie(nevoie);
            showAlert("Succes", "Nevoia a fost salvată cu succes!", Alert.AlertType.INFORMATION);

            fieldTitlu.clear();
            fieldDescriere.clear();
            datePickerDeadline.setValue(null);
        }
    }

    private void showConfirmationDialog(Nevoi selectedNevoie) {
        Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmare preluare nevoie");
        alert.setHeaderText("Vrei să preiei această nevoie?");
        alert.setContentText("Titlu: " + selectedNevoie.getTitlu() + "\nDescriere: " + selectedNevoie.getDescriere());

        javafx.scene.control.ButtonType acceptButton = new javafx.scene.control.ButtonType("Acceptă");
        javafx.scene.control.ButtonType refuseButton = new javafx.scene.control.ButtonType("Refuză");

        alert.getButtonTypes().setAll(acceptButton, refuseButton);

        alert.showAndWait().ifPresent(response -> {
            if (response == acceptButton) {
                selectedNevoie.setStatus("Erou găsit!");
                selectedNevoie.setOmSalvator(GlobalUser.getPersoana().getId());
                network.updateNevoie(selectedNevoie);
            }
        });
    }

    @Override
    public void update(Object arg) {
//        List<Nevoi> nevoi = StreamSupport.stream(network.findAllByOras(GlobalUser.getPersoana().getOras()).spliterator(), false)
//                .filter(nevoie -> !Objects.equals(nevoie.getOmInNevoie(), GlobalUser.getPersoana().getId()) && Objects.equals(nevoie.getStatus(), "Caut erou!"))
//                .toList();
//        ObservableList<Nevoi> nevoiObservableList = FXCollections.observableArrayList();
//        nevoiObservableList.addAll(nevoi);
//        tableNevoi.setItems(nevoiObservableList);

        List<Nevoi> fapteBune = StreamSupport.stream(network.getNevoi().spliterator(), false)
                .filter(nevoie -> Objects.equals(nevoie.getOmSalvator(), GlobalUser.getPersoana().getId()))
                .toList();
        ObservableList<Nevoi> fapteBuneObservableList = FXCollections.observableArrayList();
        fapteBuneObservableList.addAll(fapteBune);
        tableFapteBune.setItems(fapteBuneObservableList);

        tableNevoi.refresh();
        tableFapteBune.refresh();
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

