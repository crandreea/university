package org.client.gui;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Configuration;
import model.Game;
import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.services.ProjectException;
import org.services.ProjectObserver;
import org.services.ProjectServices;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class Home implements ProjectObserver {
    @FXML
    public Label userLabel;
    public Button logoutButton;

    @FXML
    private TableView<Game> clasamentTableView;

    @FXML
    private TableColumn<Game, String> aliasColumn;

    @FXML
    private TableColumn<Game, Long> scoreColumn;

    @FXML
    private TableColumn<Game, LocalDateTime> startingTimeColumn;

    @FXML
    private Button tryButton;

    @FXML
    private TextField enteredText;

    @FXML
    private Text literePosibile;

    @FXML
    private Text word1;

    @FXML
    private Text word2;

    @FXML
    private Text word3;

    @FXML
    private Text word4;

    @FXML
    private Text cautaCuvantul;


    private ProjectServices server;
    private User user;

    private Long noOfGuessedWords = 0L;
    private Long noOfTries = 0L;
    private Stage stage;
    private LocalDateTime startTime;
    private Game game;
    private Configuration configuration;

    private static Logger logger = LogManager.getLogger(Home.class);

    public Home(){
    }

    public Home(ProjectServices server) {
        this.server = server;
        logger.debug("constructor HomeController cu server param");

    }

    public void setServer(ProjectServices s) {
        server = s;
    }

    public void setUser(User user) {
        this.user = user;
        if(user != null) {
            userLabel.setText(user.getUsername());
        }
        initModel();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize(){
        aliasColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Game, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Game, String> game) {
                return new SimpleStringProperty(game.getValue().getPlayer() != null ? game.getValue().getPlayer().getUsername() : null);
            }
        });

        startingTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startingTime"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

    }

    private void initModel() {
        try {
            List<Configuration> configurations = server.getAllConfigurations();
            if (configurations == null || configurations.isEmpty()) {
                System.err.println("No configurations found on the server. Please add some configurations to the database.");
                PopupNotification.showNotification(stage, "No configurations available. Game cannot start.", 5000, "#ffc107");
                return;
            }

            Collections.shuffle(configurations);
            configuration = configurations.getFirst();
        } catch (ProjectException e) {
            throw new RuntimeException(e);
        }

        StringBuilder letters = new StringBuilder();
        for (int i = 0; i < configuration.getLetters().length(); i++) {
            letters.append(configuration.getLetters().charAt(i));
            letters.append(" ");
        }
        literePosibile.setText(String.valueOf(letters));

        word1.setText("");
        word2.setText("");
        word3.setText("");
        word4.setText("");
        noOfGuessedWords = 0L;
        noOfTries = 0L;
        startTime = LocalDateTime.now();
        game = new Game(user, configuration, LocalDateTime.now(), 0L, 0L);

        loadLeaderboard();
    }

    private void loadLeaderboard() {
        try {
            List<Game> finishedGames = server.getAllGames();
            ObservableList<Game> gameObservableList = FXCollections.observableArrayList(finishedGames).sorted();
            clasamentTableView.setItems(gameObservableList);
        } catch (ProjectException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleButtonClick(MouseEvent mouseEvent) {
        System.out.println("Button clicked.");

        noOfTries ++;
        System.out.println(noOfTries);

        String cuvant = enteredText.getText().trim();
        if (cuvant.equals(configuration.getWord1())) {
            showAlert("Cuvant ghicit!");
            game.setScore(game.getScore() + cuvant.length());
            cautaCuvantul.setText("Cuvant ghicit!");
            word1.setText(configuration.getWord1());
            noOfGuessedWords ++;
        }
        else if (cuvant.equals(configuration.getWord2())) {
            showAlert("Cuvant ghicit!");
            game.setScore(game.getScore() + cuvant.length());
            cautaCuvantul.setText("Cuvant ghicit!");
            word2.setText(configuration.getWord2());
            noOfGuessedWords ++;
        }
        else if (cuvant.equals(configuration.getWord3())) {
            showAlert("Cuvant ghicit!");
            game.setScore(game.getScore() + cuvant.length());
            cautaCuvantul.setText("Cuvant ghicit!");
            word3.setText(configuration.getWord3());
            noOfGuessedWords ++;
        }
        else if (cuvant.equals(configuration.getWord4())) {
            showAlert("Cuvant ghicit!");
            game.setScore(game.getScore() + cuvant.length());
            cautaCuvantul.setText("Cuvant ghicit!");
            word4.setText(configuration.getWord4());
            noOfGuessedWords ++;
        }
        else {
            Long word1Match = 0L;
            Long word2Match = 0L;
            Long word3Match = 0L;
            Long word4Match = 0L;
            for (int i = 0; i < cuvant.length(); i++) {
                if (cuvant.charAt(i) == configuration.getWord1().charAt(i)) {
                    word1Match ++;
                }
                else
                    break;
            }
            for (int i = 0; i < cuvant.length(); i++) {
                if (cuvant.charAt(i) == configuration.getWord2().charAt(i)) {
                    word2Match ++;
                }
                else
                    break;
            }
            for (int i = 0; i < cuvant.length(); i++) {
                if (cuvant.charAt(i) == configuration.getWord3().charAt(i)) {
                    word3Match ++;
                }
                else
                    break;
            }
            for (int i = 0; i < cuvant.length(); i++) {
                if (cuvant.charAt(i) == configuration.getWord4().charAt(i)) {
                    word4Match ++;
                }
                else
                    break;
            }
            Long maximum = 0L;
            if (word1Match >= word2Match)
                maximum = word1Match;
            else maximum = word2Match;
            if (word3Match >= maximum)
                maximum = word3Match;
            else if (word4Match >= maximum)
                maximum = word3Match;

            game.setScore(game.getScore() + maximum);
            showAlert("Cuvant gresit...Mai incearca!");
        }

        if (noOfTries >= 4)
            endGame("Game Over!");
    }

    private Game endGame(String message) {
        try {
            User player = server.findPlayerByAlias(user.getUsername());
            game.setPlayer(user);
            game.setNoOfGuessedWords(noOfGuessedWords);
            Game addedGame = server.addGame(game);
            Platform.runLater(() -> {
                showStatisticsAfterTheGame(message);
                initModel();
            });
            return addedGame;
        } catch (ProjectException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private void showStatisticsAfterTheGame(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over!");
        alert.setHeaderText(message);

        Long totalScore = game.getScore();

        int playerPositionOnLeaderboard = 1;
        try {
            for (Game gameFromServer : server.getAllGames()) {
                if (gameFromServer.getScore() > game.getScore()) {
                    playerPositionOnLeaderboard++;
                }
            }
        } catch (ProjectException e) {
            throw new RuntimeException(e);
        }

        String content = "Total Score: " + totalScore + "\n" +
                "Your Position in Leaderboard: " + playerPositionOnLeaderboard;

        alert.setContentText(content);
        alert.showAndWait();
    }


    @Override
    public void gameAdded(Game addedGame) {
        Platform.runLater(() -> {
            System.out.println("Game added: " + addedGame);
            loadLeaderboard();
        });
    }

    public void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    @FXML
    public void handleLogout(MouseEvent mouseEvent) throws ProjectException {
        server.logout(user, this);
        ((Node)(mouseEvent.getSource())).getScene().getWindow().hide();
    }
}