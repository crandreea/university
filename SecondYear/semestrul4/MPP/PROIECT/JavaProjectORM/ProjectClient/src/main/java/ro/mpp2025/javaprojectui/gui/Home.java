package ro.mpp2025.javaprojectui.gui;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ro.mpp2025.javaprojectui.*;
import ro.mpp2025.javaprojectui.dto.ParticipantDTO;
import ro.mpp2025.javaprojectui.dto.ProbaDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Home implements ProjectObserver {
    public Label userLabel;
    public Button logoutButton;
    @FXML
    public TableView<ProbaDTO> eventsTable;
    public TableColumn<ProbaDTO, String> eventNameColumn;
    public TableColumn<ProbaDTO, String> ageGroupColumn;
    public TableColumn<ProbaDTO, Number> registeredCountColumn;
    public ComboBox<String> searchEventComboBox;
    public ComboBox<String> searchAgeGroupComboBox;
    public TableView<ParticipantDTO> searchResultsTable;
    public TableColumn<ParticipantDTO, String> participantNameColumn;
    public TableColumn<ParticipantDTO, Number> participantAgeColumn;
    public TextField participantNameField;
    public TextField participantCNPField;
    public ComboBox<String> event1ComboBox;
    public ComboBox<String> ageGroup1ComboBox;
    public ComboBox<String> event2ComboBox;
    public Label registrationMessageLabel;

    private ProjectServices server;
    private Organizator user;

    ObservableList<ProbaDTO> observableList = FXCollections.observableArrayList();

    private static Logger logger = LogManager.getLogger(Home.class);

    public Home(){
    }

    public Home(ProjectServices server) {
        this.server = server;
        logger.debug("constructor HomeController cu server param");

    }

    public void setServer(ProjectServices s) {
        server = s;
        try {
            loadDataFromServer();
        } catch (ProjectException e) {
            logger.error("Error loading data after setting server: " + e.getMessage(), e);
        }
    }

    public void setUser(Organizator user) {

        this.user = user;
        if(user != null) {
            userLabel.setText(user.getUsername());
        }
    }

    @FXML
    public void initialize() throws ProjectException {
        populateComboBox();
        initializeTable();
    }

    public void loadDataFromServer() throws ProjectException {

        if (server != null) {
            server.refreshProbeStatistics();

            List<ProbaDTO> allProbaDTO =  server.getAllProbaDTO();
            if (allProbaDTO == null) {
                allProbaDTO = new ArrayList<>();
            }

            observableList.setAll(allProbaDTO);
            System.out.println("ProbaDTO from server:" + observableList);
        }
    }

    public void initializeTable() throws ProjectException {
        eventNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEventName()));
        ageGroupColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getAgeGroup()));
        registeredCountColumn.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getRegisteredCount()));

        participantNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getName()));
        participantAgeColumn.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getAge()));

        eventsTable.setItems(observableList);
        loadDataFromServer();
    }

    public void populateComboBox(){
        List<String> stringList = Arrays.asList("Desen", "Cautare de comori", "Poezie");
        ObservableList<String> observableList = FXCollections.observableArrayList(stringList);
        event1ComboBox.setItems(observableList);
        event2ComboBox.setItems(observableList);
        searchEventComboBox.setItems(observableList);

        List<String> agestringList = Arrays.asList("6-8", "9-11", "12-15");
        ObservableList<String> observableList2 = FXCollections.observableArrayList(agestringList);
        ageGroup1ComboBox.setItems(observableList2);
        searchAgeGroupComboBox.setItems(observableList2);
    }

    public void handleLogout(javafx.event.ActionEvent actionEvent) {
        logout();
        ((javafx.scene.Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }

    void logout() {
        try {
            server.logout(user, this);
        } catch (ProjectException e) {
            logger.error("Logout error " + e);
        }
    }

    public void handleSearch(javafx.event.ActionEvent actionEvent) throws ProjectException {
        String selectedProba = searchEventComboBox.getValue();
        String selectedCategory = searchAgeGroupComboBox.getValue();

        Proba proba = server.getProbaByName(selectedProba);
        CategorieVarsta categorieVarsta = server.getVarstaByRange(selectedCategory);

        System.out.println("Proba:" + proba);
        System.out.println("CategorieVarsta:" + categorieVarsta);

        List<ParticipantDTO> results = server.searchParticipants(proba, categorieVarsta);
        System.out.println("Resultat" + results);
        searchResultsTable.setItems(FXCollections.observableArrayList(results));

    }

    public void handleRegister(javafx.event.ActionEvent actionEvent) throws ProjectException, SQLException {
        String name = participantNameField.getText();
        String cnp = participantCNPField.getText();
        String event1 =  event1ComboBox.getValue();
        String event2 =  event2ComboBox.getValue();
        String age =  ageGroup1ComboBox.getValue();


        String result = server.registerParticipant(name, cnp, event1, event2, age);

        registrationMessageLabel.setText(result);
        registrationMessageLabel.setVisible(true);

        if (result.contains("successful")) {
            participantNameField.clear();
            participantCNPField.clear();
            event1ComboBox.setValue(null);
            event2ComboBox.setValue(null);
            ageGroup1ComboBox.setValue(null);
            ageGroup1ComboBox.setDisable(false);

            loadDataFromServer();
        }
    }


    @Override
    public void inscriereAdded(Inscriere inscriere) throws ProjectException {
        Platform.runLater(() -> {
            logger.info("Received notification about new registration");
            try {
                observableList.clear();
                List<ProbaDTO> probe = server.getAllProbaDTO();
                observableList.addAll(probe);
                eventsTable.refresh();
            } catch (ProjectException e) {
                logger.error("Error loading data after notification: " + e.getMessage(), e);
            }
        });
    }

}
