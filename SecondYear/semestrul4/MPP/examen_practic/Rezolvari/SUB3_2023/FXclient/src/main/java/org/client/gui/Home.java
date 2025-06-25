package org.client.gui;

import javafx.animation.PauseTransition;
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
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.services.ProjectException;
import org.services.ProjectObserver;
import org.services.ProjectServices;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.lang.String.valueOf;

public class Home implements ProjectObserver {
    @FXML
    public Label userLabel;
    @FXML
    public Button buttonlogout;
    @FXML
    private GridPane gameTable;

    @FXML
    private TableView<Game> clasamentTableView;

    @FXML
    private TableColumn<Game, String> aliasColumn;

    @FXML
    private TableColumn<Game, Long> scoreColumn;

    @FXML
    private TableColumn<Game, Long> noOfSecondsColumn;

    private ProjectServices server;
    private User user;
    private int cellClickCount = 0;
    private WordCell[][] wordCells = new WordCell[2][5];
    private WordCell lastClickedCell;
    private LocalDateTime startTime;
    private Game game;
    private Stage stage;
    private Configuration configuration;
    private List<Position> positions = new ArrayList<>();
    private List<ConfigurationWord> configurationWords = new ArrayList<>();

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
    
    public void initModel(){
        System.out.println("initModel");
        try {
            List<Word> words = new ArrayList<>(server.getAllWords());
            if (words == null || words.isEmpty()) {
                System.err.println("No configurations found on the server. Please add some configurations to the database.");
                PopupNotification.showNotification(stage, "No configurations available. Game cannot start.", 5000, "#ffc107");
                return;
            }

            Collections.shuffle(words);
            List<Word> gameWords = new ArrayList<>();
            int i = 0;
            while (gameWords.size() < 10) {
                Word word = words.get(i);
                gameWords.add(word);
                gameWords.add(word);
                i++;
            }
            Collections.shuffle(gameWords);

            for (Word word: gameWords) {
                ConfigurationWord configurationWord = new ConfigurationWord();
                configurationWord.setWord(word);
                configurationWord.setWordNumber(Integer.parseInt(valueOf(configurationWords.size() + 1)));
                configurationWords.add(configurationWord);
            }

            configuration = new Configuration();
            server.addConfiguration(configuration);

            addConfigurationWords();

            for (i = 0; i < 10; i ++) {
                int rowIndex = i / 5;
                int colIndex = i % 5;
                Node node = getNodeByRowColumnIndex(rowIndex, colIndex, gameTable);
                if (node == null) {
                    System.err.println("ERROR: Node is null for row: " + rowIndex + ", col: " + colIndex);
                } else if (!(node instanceof Text)) {
                    System.err.println("ERROR: Node at row: " + rowIndex + ", col: " + colIndex + " is not a Text node. It's: " + node.getClass().getName());
                } else {
                    WordCell cell = new WordCell(gameWords.get(i).getWord(), false, (Text) node);
                    wordCells[rowIndex][colIndex] = cell;
                }
            }

            hideAllWords();
            lastClickedCell = null;
            cellClickCount = 0;
            positions.clear();
            startTime = LocalDateTime.now();
            game = new Game(user, configuration, 0, 0);

            loadLeaderboard();

        } catch (ProjectException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
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

    private Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> children = gridPane.getChildren();

        for (Node node : children) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }

        return result;
    }

    @FXML
    public void handleCellClick(MouseEvent event) {
        System.out.println("GridPane clicked.");

        double cellWidth = gameTable.getWidth() / 5;
        double cellHeight = gameTable.getHeight() / 2;

        int colIndex = (int) Math.floor(event.getX() / cellWidth);
        int rowIndex = (int) Math.floor(event.getY() / cellHeight);

        System.out.println("Row: " + rowIndex + ", Col: " + colIndex);

        WordCell clickedCell = wordCells[rowIndex][colIndex];
        Text clickedTextNode = clickedCell.getTextNode();

        if (!clickedCell.isRevealed()) {
            clickedCell.setRevealed(true);
            clickedTextNode.setText(clickedCell.getWord());

            cellClickCount ++;

            Position position = new Position(game, Integer.parseInt(valueOf(rowIndex)), Integer.parseInt(valueOf(colIndex)), Integer.parseInt(valueOf(cellClickCount)));
            positions.add(position);

            if (lastClickedCell == null) {
                lastClickedCell = clickedCell;
            } else {
                if (lastClickedCell.getWord().equals(clickedCell.getWord())) {
                    game.setScore(game.getScore() + 3);
                    lastClickedCell = null;
                    showAlert("You uncovered the right pair!");
                } else {
                    game.setScore(game.getScore() - 2);
                    showAlert("Try again!");
                    PauseTransition pause = new PauseTransition(Duration.seconds(1));
                    pause.setOnFinished(e -> {
                        lastClickedCell.setRevealed(false);
                        lastClickedCell.getTextNode().setText("");
                        clickedCell.setRevealed(false);
                        clickedTextNode.setText("");
                        lastClickedCell = null;
                    });
                    pause.play();
                }
            }
            if (allCellsRevealed() || cellClickCount == 20) {
                game.setNoOfSeconds((int) ChronoUnit.SECONDS.between(startTime, LocalDateTime.now()));
                endGame();
            }
        } else {
            System.out.println("Cell at Row: " + rowIndex + ", Col: " + colIndex + " has already been revealed.");
        }
    }

    private boolean allCellsRevealed() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 5; j++) {
                if (!wordCells[i][j].isRevealed()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void revealAllWords() {
        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 5; j++) {
                WordCell cell = wordCells[i][j];
                cell.setRevealed(true);
                cell.getTextNode().setText(cell.getWord());
            }
    }

    private void hideAllWords() {
        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 5; j++) {
                WordCell cell = wordCells[i][j];
                cell.setRevealed(false);
                cell.getTextNode().setText("");
            }
    }

    private Game endGame() {
        try {
            User playerForGame = server.findPlayerByAlias(user.getUsername());
            System.out.println("Player for game: " + playerForGame);
            game.setPlayer(playerForGame);
            Game addedGame = server.addGame(game);
            Platform.runLater(() -> {
                revealAllWords();
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

    private void addConfigurationWords() {
        List<Configuration> configurations;
        try {
            configurations = server.getAllConfigurations();
        } catch (ProjectException | SQLException e) {
            e.printStackTrace();
            return;
        }

        if (configurations.isEmpty()) {
            return;
        }

        Configuration configurationWithHighestId = configurations.get(0);
        for (Configuration configuration : configurations) {
            if (configuration.getId() > configurationWithHighestId.getId()) {
                configurationWithHighestId = configuration;
            }
        }

        configuration = configurationWithHighestId;

        try {
            for (ConfigurationWord configurationWord: configurationWords) {
                configurationWord.setConfiguration(configurationWithHighestId);
                server.addConfigurationWord(configurationWord);
            }
        } catch (SQLException | ProjectException e) {
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


    @Override
    public void gameAdded(Game addedGame) {
        Platform.runLater(() -> {
            System.out.println("Game added: " + addedGame);
            loadLeaderboard();
        });
    }


    @FXML
    public void handleLogout(javafx.event.ActionEvent actionEvent) throws ProjectException {
        server.logout(user, this);
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }

    private void handleExit() {
        Stage stage = (Stage) gameTable.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over!");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

class WordCell {
    private String word;
    private boolean revealed;
    private Text textNode;

    public WordCell(String word, boolean revealed, Text textNode) {
        this.word = word;
        this.revealed = revealed;
        this.textNode = textNode;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }

    public Text getTextNode() {
        return textNode;
    }

    public void setTextNode(Text textNode) {
        this.textNode = textNode;
    }


}