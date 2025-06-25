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
import javafx.util.Callback;
import model.Game;
import model.Position;
import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.services.ProjectException;
import org.services.ProjectObserver;
import org.services.ProjectServices;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Home implements ProjectObserver {
    @FXML
    public Label userLabel;

    //leaderboard
    public TableColumn<Game, String> aliasColumn;
    public TableColumn<Game, Integer> scoreColumn;
    public TableColumn<Game, Integer> noOfSecondsColumn;
    public TableView<Game> clasamentTableView;


    private ProjectServices server;
    private User user;
    private LocalDateTime startTime;
    private Game game;
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

    @FXML
    public void initialize() {
        aliasColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Game, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Game, String> game) {
                return new SimpleStringProperty(game.getValue().getPlayer() != null ? game.getValue().getPlayer().getUsername() : null);
            }
        });

        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        noOfSecondsColumn.setCellValueFactory(new PropertyValueFactory<>("noOfSeconds"));
    }

    private void initModel() {
    }


    private void loadLeaderboard() {
        System.out.println("load leaderboard");
        try {
            List<Game> finishedGames = new ArrayList<>(server.getAllGames());
            finishedGames.sort(Comparator.comparingLong(Game::getScore).reversed()
                    .thenComparingLong(Game::getNoOfSeconds));
            ObservableList<Game> gameObservableList = FXCollections.observableArrayList(finishedGames);
            clasamentTableView.setItems(gameObservableList);
        } catch (ProjectException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Game endGame() {
        try {
            User playerForGame = server.findPlayerByAlias(user.getUsername());
            System.out.println("Player for game: " + playerForGame);
            game.setPlayer(playerForGame);
            Game addedGame = server.addGame(game);
            Platform.runLater(() -> {
                try {
                    revealAll();
                } catch (ProjectException | SQLException e) {
                    throw new RuntimeException(e);
                }
                showStatisticsAfterTheGame();
                initModel();
            });
            addPositions();
            return addedGame;
        } catch (SQLException | ProjectException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void addPositions() {
//        List<Game> games;
//        try {
//            games = server.getAllGames();
//        } catch (SQLException | ProjectException e) {
//            e.printStackTrace();
//            return;
//        }
//
//        if (games.isEmpty()) {
//            return;
//        }
//
//        Game gameWithHighestId = games.get(0);
//        for (Game game : games) {
//            if (game.getId() > gameWithHighestId.getId()) {
//                gameWithHighestId = game;
//            }
//        }
//
//        try {
//            for (Position position: positions) {
//                position.setGame(gameWithHighestId);
//                server.addPosition(position);
//            }
//        } catch ( SQLException | ProjectException e) {
//            throw new RuntimeException(e);
//        }
    }

    private void revealAll() throws ProjectException, SQLException {
//        for (Position p : pathTaken) {
//            Button btn = cellButtons.get(p.getCoordinateX() + "_" + p.getCoordinateY());
//            if (btn != null) {
//                btn.getStyleClass().add("final-path");
//            }
//        }
//
//        for (Position pos : positions) {
//            if(pos.isTrap()){
//                Platform.runLater(() -> {
//                    Button button = cellButtons.get(pos.getCoordinateX() + "_" + pos.getCoordinateY());
//                    if (button != null) {
//                        if (!pos.isRevealed()) {
//                            button.setText("TRAP");
//                            button.getStyleClass().clear();
//                            button.getStyleClass().add("grid-cell-trap");
//                        }
//                    }
//                });
//            }
//        }
//
//
//        Button finalPitBtn = cellButtons.get(currentPitPosition.getCoordinateX() + "_" + currentPitPosition.getCoordinateY());
//        if (finalPitBtn != null) {
//            finalPitBtn.getStyleClass().add("final-pit");
//        }
//
//        for (Button btn : cellButtons.values()) {
//            btn.setDisable(true);
//        }
    }

    private void showStatisticsAfterTheGame() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over!");
        alert.setHeaderText(null);

        Integer totalScore = game.getScore();

        int playerPositionOnLeaderboard = 1;
        try {
            for (Game gameFromServer : server.getAllGames()) {
                if (gameFromServer.getScore() > game.getScore()) {
                    playerPositionOnLeaderboard++;
                }
                else if (gameFromServer.getScore().equals(game.getScore()) && gameFromServer.getNoOfSeconds() < game.getNoOfSeconds()) {
                    playerPositionOnLeaderboard++;
                }
            }
        } catch (SQLException | ProjectException e) {
            throw new RuntimeException(e);
        }

        String content = "Total Score: " + totalScore + "\n" +
                "Your Position in Leaderboard: " + playerPositionOnLeaderboard;

        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    public void handleLogout(javafx.event.ActionEvent actionEvent) throws ProjectException {
        server.logout(user, this);
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over!");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void gameAdded(Game addedGame) {
        Platform.runLater(() -> {
            System.out.println("Game added: " + addedGame);
            loadLeaderboard();
        });
    }
}