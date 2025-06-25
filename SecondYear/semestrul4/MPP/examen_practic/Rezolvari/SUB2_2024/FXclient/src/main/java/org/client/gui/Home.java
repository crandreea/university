package org.client.gui;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Game;
import model.Position;
import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.services.ProjectException;
import org.services.ProjectObserver;
import org.services.ProjectServices;

import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Home implements ProjectObserver {
    public GridPane gameTable;
    public TableColumn<Game, String> aliasColumn;
    public TableColumn<Game, Integer> scoreColumn;
    public TableColumn<Game, Integer> noOfSecondsColumn;
    public TableView<Game> clasamentTableView;
    public Label userLabel;


    private ProjectServices server;
    private User user;
    private LocalDateTime startTime;
    private Game game;
    private Stage stage;
    private List<Position> positions = new ArrayList<>();
    private Map<String, Button> cellButtons = new HashMap<>();
    private Set<String> trapCoordinates;
    private static final int GRID_SIZE = 5;
    private Position currentPitPosition; // To store the current location of the 'groapa'
    private List<Position> pathTaken;

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

    private void populateGrid() {
        gameTable.getChildren().clear();
        cellButtons.clear();
        for (int y = 0; y < GRID_SIZE; y++) {
            for (int x = 0; x < GRID_SIZE; x++) {
                Button cellButton = new Button(" ");
                cellButton.setPrefSize(50, 50);
                cellButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); // Allow buttons to expand
                cellButton.getStyleClass().add("grid-cell");
                cellButton.setAlignment(Pos.CENTER);

                if(x == 0 && y == 0){
                    cellButton.setText("START");
                    cellButton.getStyleClass().add("grid-start-cell");
                }

                if(x == GRID_SIZE - 1 && y == GRID_SIZE - 1){
                    cellButton.setText("END");
                    cellButton.getStyleClass().add("grid-end-cell");
                }

                final int col = x;
                final int row = y;
                cellButton.setOnAction(event -> handleCellClick(col, row, cellButton));
                gameTable.add(cellButton, col, row);
                cellButtons.put(col + "_" + row, cellButton);
            }
        }
    }

    private void initModel() {
        populateGrid();

        positions.clear();
        pathTaken = new ArrayList<>();

        startTime = LocalDateTime.now();
        game = new Game(user, 0, 0);

        currentPitPosition = new Position(game, 0, 0); // Assuming constructor (x, y)
        currentPitPosition.setRevealed(true);

        for (int r = 0; r < GRID_SIZE; r++) {
            for (int c = 0; c < GRID_SIZE; c++) {
                Position p = new Position(c, r); // Assuming (col, row)
                if (c == 0 && r == 0) {
                    //p.setTrap(true); // Initial pit
                    currentPitPosition = p; // Store the initial pit position
                }
                positions.add(p);
            }
        }

        trapCoordinates = placeRandomTraps();
        logger.info("Generated trap coordinates: {}", trapCoordinates);

        for (Position p : positions) {
            String coordKey = p.getCoordinateX() + "_" + p.getCoordinateY();
            if (trapCoordinates.contains(coordKey)) {
                p.setTrap(true);
            }
        }

        pathTaken.add(currentPitPosition);
        loadLeaderboard();
    }

    private Set<String> placeRandomTraps() {
        Set<String> traps = new HashSet<>();
        List<String> availableCells = new ArrayList<>();
        for (int r = 0; r < GRID_SIZE; r++) {
            for (int c = 0; c < GRID_SIZE; c++) {
                String coord = c + "_" + r;
                // Ensure START and END are not traps
                if (!((c == 0 && r == 0) || (c == GRID_SIZE - 1 && r == GRID_SIZE - 1))) {
                    availableCells.add(coord);
                }
            }
        }
        Collections.shuffle(availableCells); // Shuffle for random selection

        // Step 1: Place one trap in each column (4 traps)
        Set<Integer> columnsWithTraps = new HashSet<>();
        List<String> cellsForColumnPlacement = new ArrayList<>(availableCells); // Use a copy
        Random random = new Random();

        for (int c = 0; c < GRID_SIZE; c++) {
            int finalC = c;
            List<String> columnCells = cellsForColumnPlacement.stream()
                    .filter(coord -> Integer.parseInt(coord.split("_")[0]) == finalC)
                    .collect(Collectors.toList());

            if (!columnCells.isEmpty()) {
                String chosenCell = columnCells.remove(random.nextInt(columnCells.size()));
                traps.add(chosenCell);
                availableCells.remove(chosenCell); // Remove from main available list
            } else {
                logger.warn("Could not place trap in column {} as no available cells.", c);
            }
        }

        // Step 2: Place the remaining traps (6 - 4 = 2 traps) from available cells
        int trapsPlacedSoFar = traps.size();
        while (trapsPlacedSoFar < 6 && !availableCells.isEmpty()) {
            String chosenCell = availableCells.remove(random.nextInt(availableCells.size()));
            traps.add(chosenCell);
            trapsPlacedSoFar++;
        }

        if (traps.size() != 6) {
            logger.error("Failed to place exactly {} traps. Placed: {}", 6, traps.size());
        }

        return traps;
    }

    private void handleCellClick(int col, int row, Button cellButton) {
        Optional<Position> clickedPositionOpt = positions.stream()
                .filter(p -> p.getCoordinateX().equals(col) && p.getCoordinateY().equals(row))
                .findFirst();

        if (clickedPositionOpt.isEmpty()) {
            logger.warn("Clicked an invalid cell (no corresponding Position object): ({},{})", col, row);
            return;
        }

        Position clickedPosition = clickedPositionOpt.get();

        boolean isValidMove = isAdjacent(currentPitPosition, clickedPosition) && !clickedPosition.isRevealed();

        if (!isValidMove) {
            showAlert("Invalid move! You can only move to an adjacent, unrevealed cell.");
            return;
        }

        if (clickedPosition.isRevealed()) {
            showAlert("Invalid move: Target position has already been revealed.");
            return;
        }

        if(clickedPosition.isTrap()){
            showAlert("GAME OVER! Wait for the reveal!");
            endGame();
        }

        //currentPitPosition.setTrap(false);

        //clickedPosition.setTrap(true);
        clickedPosition.setRevealed(true);
        cellButton.getStyleClass().add("revealed-cell");

        pathTaken.add(clickedPosition);

        currentPitPosition = clickedPosition;

        positions.add(clickedPosition);

        game.setScore(game.getScore() + col);

        cellButton.setDisable(true);

        if (currentPitPosition.getCoordinateX() == GRID_SIZE - 1 && currentPitPosition.getCoordinateY() == GRID_SIZE - 1) {
            showAlert("Congratulations! You reached the END!");
            game.setNoOfSeconds((int) ChronoUnit.SECONDS.between(startTime, LocalDateTime.now()));// Simple duration
            endGame();
        }

    }

    private boolean isAdjacent(Position p1, Position p2) {
        int dx = Math.abs(p1.getCoordinateX() - p2.getCoordinateX());
        int dy = Math.abs(p1.getCoordinateY() - p2.getCoordinateY());
        return (dx == 1 && dy == 0) || (dx == 0 && dy == 1);
    }

    private Game endGame() {
        try {
            User playerForGame = server.findPlayerByAlias(user.getUsername());
            System.out.println("Player for game: " + playerForGame);
            game.setPlayer(playerForGame);
            Game addedGame = server.addGame(game);
            Platform.runLater(() -> {
                try {
                    revealAllTraps();
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
        List<Game> games;
        try {
            games = server.getAllGames();
        } catch (SQLException | ProjectException e) {
            e.printStackTrace();
            return;
        }

        if (games.isEmpty()) {
            return;
        }

        Game gameWithHighestId = games.get(0);
        for (Game game : games) {
            if (game.getId() > gameWithHighestId.getId()) {
                gameWithHighestId = game;
            }
        }

        try {
            for (Position position: positions) {
                position.setGame(gameWithHighestId);
                server.addPosition(position);
            }
        } catch ( SQLException | ProjectException e) {
            throw new RuntimeException(e);
        }
    }

    private void revealAllTraps() throws ProjectException, SQLException {
        for (Position p : pathTaken) {
            Button btn = cellButtons.get(p.getCoordinateX() + "_" + p.getCoordinateY());
            if (btn != null) {
                btn.getStyleClass().add("final-path");
            }
        }

        for (Position pos : positions) {
            if(pos.isTrap()){
                Platform.runLater(() -> {
                    Button button = cellButtons.get(pos.getCoordinateX() + "_" + pos.getCoordinateY());
                    if (button != null) {
                        if (!pos.isRevealed()) {
                            button.setText("TRAP");
                            button.getStyleClass().clear();
                            button.getStyleClass().add("grid-cell-trap");
                        }
                    }
                });
            }
        }


        Button finalPitBtn = cellButtons.get(currentPitPosition.getCoordinateX() + "_" + currentPitPosition.getCoordinateY());
        if (finalPitBtn != null) {
            finalPitBtn.getStyleClass().add("final-pit");
        }

        for (Button btn : cellButtons.values()) {
            btn.setDisable(true);
        }
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