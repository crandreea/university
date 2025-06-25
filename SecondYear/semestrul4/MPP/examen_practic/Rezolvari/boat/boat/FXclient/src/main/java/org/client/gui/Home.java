package org.client.gui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.services.ProjectException;
import org.services.ProjectObserver;
import org.services.ProjectServices;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Home implements ProjectObserver {
    @FXML
    public GridPane table;
    public TableView<Game> clasament;
    public TableColumn<Game, Integer> successfulShotsColumn;
    public TableColumn<Game, String> usernameColumn;
    public TableColumn<Game, String> startTimeColumn;
    public TableColumn<Game, Integer> scoreColumn;
    public Label userLabel;

    private static final int GRID_SIZE = 5;

    private ProjectServices server;
    private User user;
    private Game currentGame;
    private Map<String, Button> cellButtons = new HashMap<>();
    private static Logger logger = LogManager.getLogger(Home.class);

    private ObservableList<Game> gameData = FXCollections.observableArrayList();

    public Home(){
    }

    public Home(ProjectServices server) throws ProjectException {
        this.server = server;
        logger.debug("constructor HomeController cu server param");
    }

    public void initializeController() throws ProjectException {

        if (this.server == null) {
            logger.error("initController called but server is null!");
            throw new IllegalStateException("ProjectServices (server) not set before calling initController.");
        }
        if (this.user == null) {
            logger.error("initController called but user is null!");
            throw new IllegalStateException("User not set before calling initController.");
        }

        if (this.currentGame == null) {
            this.currentGame = server.createGame(user);
            logger.debug("New game created for user: {}", user.getUsername());
        }

        initializeTable();
        populateGrid();
        loadExistingGamePositions();
    }

    public void setServer(ProjectServices s) throws ProjectException {
        this.server = s;
    }

    public void setUser(User user) {
        this.user = user;
        if(user != null) {
            userLabel.setText(user.getUsername());
        }
    }

    public void loadDataFromServer() throws ProjectException {
        logger.info("loadDataFromServer called");
        List<Game> games = server.getFinishedGames();
        System.out.println("Finished games:" + games);
        gameData.setAll(games);
    }

    private void initializeTable() throws ProjectException {
        logger.info("initializeTable called");
        usernameColumn.setCellValueFactory(cellData -> {
            Game game = cellData.getValue();
            return new javafx.beans.property.SimpleStringProperty(game.getUser_id().getUsername()); // Calls Game.getUsername()
        });

        successfulShotsColumn.setCellValueFactory(new PropertyValueFactory<>("successful_shots"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        startTimeColumn.setCellValueFactory(cellData -> {
            Game game = cellData.getValue();
            return new javafx.beans.property.SimpleStringProperty(
                    game.getStart_time() != null ? game.getStart_time().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) : "N/A"
            );
        });

        loadDataFromServer();
        clasament.setItems(gameData);

    }
    private void populateGrid() {
        table.getChildren().clear();
        cellButtons.clear();
        for (int y = 0; y < GRID_SIZE; y++) {
            for (int x = 0; x < GRID_SIZE; x++) {
                Button cellButton = new Button(" ");
                cellButton.setPrefSize(50, 50);
                cellButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); // Allow buttons to expand
                cellButton.getStyleClass().add("grid-cell");
                cellButton.setAlignment(Pos.CENTER);

                final int col = x;
                final int row = y;
                cellButton.setOnAction(event -> handleCellClick(col, row, cellButton));
                table.add(cellButton, col, row);
                cellButtons.put(col + "_" + row, cellButton);
            }
        }
    }


    private void handleCellClick(int x, int y, Button button) {
        if (currentGame.getStatus() == GameStatus.FINISHED) {
            showAlert(Alert.AlertType.WARNING, "Game Over", "This game is already finished. Please start a new game.");
            return;
        }

        try {
            ShotResultDTO result = server.makeShot(currentGame.getId(), x, y);

            currentGame = server.getGameById(currentGame.getId());

            Platform.runLater(() -> {
                button.setDisable(true);

                GamePosition revealedPosition = result.getPosition();
                System.out.println("Pozitia:" + revealedPosition);

                if(revealedPosition != null) {
                    if (result.getDistanceToNearestBoat() > 0) {
                        Double distance = result.getDistanceToNearestBoat();
                        System.out.println("Distance: " + distance);
                        if (distance != null) {
                            button.setText(String.format("%.1f", distance));
                            button.getStyleClass().add("grid-cell-miss");
                        }

                    } else{
                        button.setText("BOAT");
                        button.getStyleClass().add("grid-cell-boat");
                        showAlert(Alert.AlertType.INFORMATION, "Hit!", "You hit a boat! +5 points!");
                    }
                }

                if(currentGame.getStatus() == GameStatus.FINISHED){
                    gameFinished(currentGame);
                }
            });


        } catch (ProjectException e) {
            logger.error("Failed to make shot at ({}, {}) in Game ID {}: {}", x, y, currentGame.getId(), e.getMessage());
            showAlert(Alert.AlertType.ERROR, "Shot Failed", e.getMessage());}

    }


    private void loadExistingGamePositions() {
        try {
            List<GamePosition> positions = server.getGamePositions(currentGame.getId());
            for (GamePosition pos : positions) {
                if (pos.isRevealed()) {
                    updateCellUI(pos);
                    if (currentGame.getStatus() == GameStatus.SETUP) {
                        Button btn = cellButtons.get(pos.getX() + "_" + pos.getY());
                        if (btn != null) {
                            btn.setDisable(true);
                        }
                    }
                }
            }
        } catch (ProjectException e) {
            logger.error("Failed to load existing game positions for Game ID {}: {}", currentGame.getId(), e.getMessage());
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load game positions: " + e.getMessage());
        }
    }

    private void updateCellUI(GamePosition position) {
        Platform.runLater(() -> {
            Button button = cellButtons.get(position.getX() + "_" + position.getY());
            if (button != null) {
                if (position.isRevealed()) {
                    button.setDisable(true);
                    button.getStyleClass().clear();
                    if (position.getStatus() == PositionsStatus.HIT) {
                        button.getStyleClass().add("grid-cell-boat");
                        button.setText("BOAT");
                    } else if (position.getStatus() == PositionsStatus.MISS) {
                        button.getStyleClass().add("grid-cell-miss");
                    } else if (position.getStatus() == PositionsStatus.BOAT && currentGame.getStatus() == GameStatus.FINISHED) {
                        button.getStyleClass().add("grid-cell-boat");
                        button.setText("BOAT");
                    }
                }
            }
        });
    }


    private void handleGameFinished(Game game) {
        if (game.getId().equals(currentGame.getId())) {
            Platform.runLater(() -> {
                try {
                    server.updateGameStatus(game.getId(), GameStatus.FINISHED);
                    currentGame.setStatus(GameStatus.FINISHED);
                    server.update(game);
                    server.update(currentGame);
                    showAlert(Alert.AlertType.INFORMATION, "Game Over", "You reached the max attempts.\nFinal Score: " + currentGame.getScore());

                    currentGame = game;
                    disableAllCells();
                    revealAllBoats();

                } catch (ProjectException e) {
                    throw new RuntimeException(e);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            });
        }
    }


    private void disableAllCells() {
        cellButtons.values().forEach(button -> button.setDisable(true));
    }

    private void revealAllBoats() {
        try {
            List<GamePosition> boatPositions = server.getBoatGamePositions(currentGame.getId());
            for (GamePosition pos : boatPositions) {
                Platform.runLater(() -> {
                    Button button = cellButtons.get(pos.getX() + "_" + pos.getY());
                    if (button != null) {
                        if (!pos.isRevealed()) {
                            button.setText("BOAT");
                            button.getStyleClass().clear();
                            button.getStyleClass().add("grid-cell-boat");
                        }
                    }
                });
            }
        } catch (ProjectException e) {
            logger.error("Failed to reveal all boat positions for Game ID {}: {}", currentGame.getId(), e.getMessage());
        }
    }

    @FXML
    public void handleLogoutButton(ActionEvent actionEvent) {
        try {
            server.logout(user, this);
            ((javafx.scene.Node)(actionEvent.getSource())).getScene().getWindow().hide();
        } catch (ProjectException e) {
            logger.error("Logout failed: {}", e.getMessage());
            showAlert(Alert.AlertType.ERROR, "Logout Failed", "Failed to log out: " + e.getMessage());
        }
    }


    @Override
    public void gameFinished(Game game) {
        handleGameFinished(game);
    }

    @Override
    public void leaderboardUpdated() {
        Platform.runLater(() -> {
            try {
                logger.info("Leaderboard update received. Loading data...");
                loadDataFromServer(); // Re-fetch the latest data from the server
                clasament.refresh(); // Refresh the TableView to show new data
                logger.info("Leaderboard refreshed successfully.");
            } catch (ProjectException e) {
                logger.error("Failed to update leaderboard in client: {}", e.getMessage());
                showAlert(Alert.AlertType.ERROR, "Leaderboard Update Failed", "Failed to update leaderboard: " + e.getMessage());
            }
        });
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}