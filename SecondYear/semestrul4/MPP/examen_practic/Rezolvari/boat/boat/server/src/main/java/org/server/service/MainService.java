package org.server.service;

import model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.server.Authenticator;
import org.services.ProjectException;
import org.services.ProjectObserver;
import org.services.ProjectServices;

import javax.sound.midi.SysexMessage;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainService implements ProjectServices {
    private final Service<Integer, User> userService;
    private final Service<Integer, Game> gameService;
    private final GamePositionService gamePositionService;

    private final Map<String, ProjectObserver> loggedInClients;
    private ExecutorService notificationExecutor;

    private User currentUser;
    private static final Logger logger = LogManager.getLogger(MainService.class);

    public MainService(Service<Integer, User> userService, Service<Integer, Game> gameService, Service<Integer, GamePosition> gamePositionService) {
        this.userService = userService;
        this.gameService = gameService;
        this.gamePositionService = (GamePositionService) gamePositionService;

        this.loggedInClients = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized User login(String username, String password, ProjectObserver client) throws Exception {
        logger.info("Attempting login for user: {}", username);
        Optional<User> orgOpt = Optional.ofNullable(Authenticator.login(username, password));

        if (orgOpt.isPresent()) {
            User org = orgOpt.get();
            if (org.getPassword().equals(password)) {
                if (loggedInClients.containsKey(username)) {
                    logger.warn("User {} is already logged in.", username);
                    throw new ProjectException("User already logged in.");
                }
                loggedInClients.put(username, client);

                logger.info("User {} logged in successfully. Observer added.", username);

                return org;

            } else {
                logger.warn("Incorrect password for user: {}", username);
                throw new ProjectException("Authentication failed! Incorrect password.");
            }
        } else {
            logger.warn("User not found: {}", username);
            throw new ProjectException("Authentication failed! User not found.");
        }
    }

    @Override
    public void logout(User user, ProjectObserver client) throws ProjectException {
        logger.info("Attempting logout for user: {}", user.getUsername());
        ProjectObserver removedClient = loggedInClients.remove(user.getUsername());
        if (removedClient == null) {
            logger.warn("User {} was not logged in or already logged out.", user.getUsername());
            throw new ProjectException("User " + user.getUsername() + " is not logged in.");
        }
        logger.info("User {} logged out successfully. Observer removed.", user.getUsername());
    }

    @Override
    public Iterable<User> getAllOrganizatori() throws ProjectException {
        try{
            logger.debug("Getting all users...");
            var x =  userService.findAll();
            logger.debug("Users found: {}", x);
            return x;
        }catch (Exception e){
            throw new ProjectException("Failed to find all users." + e);
        }
    }

    @Override
    public Optional<User> findUserById(Integer userId) throws ProjectException, SQLException {
        return userService.findOne(userId);
    }

    @Override
    public Game createGame(User user) throws ProjectException {
        logger.info("Creating new game for user: {}", user.getUsername());
        try {
            Optional<User> foundUser = userService.findOne(user.getId());
            if (foundUser.isEmpty()) {
                throw new ProjectException("User " + user.getUsername() + " not found.");
            }

            User newUser = foundUser.get();
            Game newGame = new Game(newUser);
            newGame.setStatus(GameStatus.SETUP); // Explicitly set initial status
            newGame.setTotal_shots(0); // Initialize shots
            newGame.setSuccessful_shots(0); // Initialize successful shots
            newGame.setScore(0); // Initialize score to 0
            newGame.setStart_time(LocalDateTime.now());

            gameService.save(newGame);

            List<GamePosition> allPositions = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    GamePosition position = new GamePosition(newGame, i, j);
                    allPositions.add(position);
                    gamePositionService.save(position);
                }
            }

            boolean placed = false;
            Random rand = new Random();
            while (!placed) {
                boolean horizontal = rand.nextBoolean();

                int startX, startY;

                if (horizontal) {
                    startX = rand.nextInt(5);
                    startY = rand.nextInt(3);
                } else {
                    startX = rand.nextInt(3);
                    startY = rand.nextInt(5);
                }// true = horizontal, false = vertical

                List<GamePosition> boatPositions = new ArrayList<>();
                for (int k = 0; k < 3; k++) {
                    int x = startX + (horizontal ? 0 : k);
                    int y = startY + (horizontal ? k : 0);

                    GamePosition pos = allPositions.stream()
                            .filter(p -> p.getX() == x && p.getY() == y)
                            .findFirst().orElse(null);
                    if (pos != null && boatPositions.size()<3) boatPositions.add(pos);
                }

                if (boatPositions.size() == 3) {
                    for (GamePosition pos : boatPositions) {
                        pos.setHasBoat(true);
                        pos.setStatus(PositionsStatus.BOAT.toString());
                        gamePositionService.update(pos);
                    }
                    placed = true;
                }

                System.out.println("Pozitii barci" + boatPositions);
            }


            logger.info("New game created with ID {} for user {}. Positions initialized and boats placed.", newGame.getId(), user.getUsername());
            return newGame;
        } catch (SQLException e) {
            logger.error("Error creating game for user {}: {}", user.getUsername(), e.getMessage());
            throw new ProjectException("Failed to create game: " + e.getMessage());
        }
    }

    @Override
    public ShotResultDTO makeShot(Integer gameId, int x, int y) throws ProjectException {
        logger.info("User making shot at game {} at coordinates ({}, {})", gameId, x, y);
        try {
            GamePosition position = ((GamePositionService)gamePositionService).findByGameAndCoordinates(gameId, x, y);
            if (position == null) {
                throw new ProjectException("Invalid game position: (" + x + ", " + y + ") for game ID " + gameId);
            }

            if (position.isRevealed()) {
                throw new ProjectException("Position (" + x + ", " + y + ") already revealed.");
            }

            Optional<Game> gameOptional = gameService.findOne(gameId);
            if (gameOptional.isPresent()) {
                Game game = gameOptional.get();

                double distance = calculateMinDistanceToBoat(gameId, x, y);
                if ( distance > 0) {
                    position.setStatus(PositionsStatus.MISS.toString());
                    game.incrementScore(-3);
                } else {
                    position.setStatus(PositionsStatus.HIT.toString());
                    game.incrementScore(5);
                    game.incrementSuccessfulShots();
                }

                position.setRevealed(true);
                game.incrementTotalShots();

                position.setShotOrder(game.getTotal_shots()); // Set the order here
                position.setShotTimestamp(LocalDateTime.now());

                gamePositionService.update(position);

                gameService.update(game);

                System.out.println("Updated game in makeshot: " + game);

                List<GamePosition> boatPositions = ((GamePositionService) gamePositionService).findBoatPositionsByGameId(gameId);
                long revealedBoatPositionsCount = boatPositions.stream()
                        .filter(GamePosition::isRevealed)
                        .count();

                if (revealedBoatPositionsCount == 3 || game.getTotal_shots() >= 3) {
                    game.setStatus(GameStatus.FINISHED);
                    game.setEnd_time(LocalDateTime.now());
                    gameService.update(game);

                    notifyGameObservers(game);
                }

                return new ShotResultDTO(position, position.isHasBoat() ? 0 : distance);

            } else {
                throw new ProjectException("Associated game not found for game position " + position.getId());
            }

        } catch (SQLException e) {
            logger.error("Error making shot in game {}: {}", gameId, e.getMessage());
            throw new ProjectException("Failed to make shot: " + e.getMessage());
        }
    }

    private double calculateMinDistanceToBoat(int gameId, int x, int y) throws SQLException {
        List<GamePosition> boatPositions = ((GamePositionService) gamePositionService).findBoatPositionsByGameId(gameId);
        return boatPositions.stream()
                .mapToDouble(p -> Math.sqrt(Math.pow(p.getX() - x, 2) + Math.pow(p.getY() - y, 2)))
                .min().orElse(0);
    }


    @Override
    public List<Game> getUserGames(User user) throws ProjectException {
        logger.info("Getting games for user: {}", user.getUsername());
        try {
            return ((GameService)gameService).findByUserId(user.getId());
        } catch (SQLException e) {
            logger.error("Error getting games for user {}: {}", user.getUsername(), e.getMessage());
            throw new ProjectException("Failed to retrieve user games: " + e.getMessage());
        }
    }

    @Override
    public List<GamePosition> getGamePositions(Integer gameId) throws ProjectException {
        logger.info("Getting all positions for game: {}", gameId);
        try {
            return ((GamePositionService)gamePositionService).findByGameId(gameId);
        } catch (SQLException e) {
            logger.error("Error getting positions for game {}: {}", gameId, e.getMessage());
            throw new ProjectException("Failed to retrieve game positions: " + e.getMessage());
        }
    }

    @Override
    public List<GamePosition> getRevealedGamePositions(Integer gameId) throws ProjectException {
        logger.info("Getting revealed positions for game: {}", gameId);
        try {
            return ((GamePositionService)gamePositionService).findRevealedByGameId(gameId);
        } catch (SQLException e) {
            logger.error("Error getting revealed positions for game {}: {}", gameId, e.getMessage());
            throw new ProjectException("Failed to retrieve revealed game positions: " + e.getMessage());
        }
    }

    @Override
    public List<GamePosition> getBoatGamePositions(Integer gameId) throws ProjectException {
        logger.info("Getting boat positions for game: {}", gameId);
        try {
            return ((GamePositionService)gamePositionService).findBoatPositionsByGameId(gameId);
        } catch (SQLException e) {
            logger.error("Error getting boat positions for game {}: {}", gameId, e.getMessage());
            throw new ProjectException("Failed to retrieve boat positions: " + e.getMessage());
        }
    }

    @Override
    public Game update(Game game) throws ProjectException, SQLException {
        logger.info("Updating game {}", game.getId());
        return gameService.update(game).get();
    }

    @Override
    public GamePosition findByGameAndCoordinates(Integer id, int x, int y) throws SQLException {
        return gamePositionService.findByGameAndCoordinates(id, x, y);
    }

    @Override
    public Game updateGameStatus(Integer gameId, GameStatus newStatus) throws ProjectException {
        logger.info("Updating status for game {} to {}", gameId, newStatus);
        try {
            Optional<Game> gameOptional = gameService.findOne(gameId);
            if (gameOptional.isPresent()) {
                Game game = gameOptional.get();
                game.setStatus(newStatus);
                if (game.getStatus() != newStatus) { // Only update if status actually changes
                    game.setStatus(newStatus);
                    if (newStatus == GameStatus.FINISHED) {
                        game.setEnd_time(LocalDateTime.now());
                    }
                    gameService.update(game);
                }

                return game;
            } else {
                throw new ProjectException("Game with ID " + gameId + " not found.");
            }
        } catch (SQLException e) {
            logger.error("Error updating game status for game {}: {}", gameId, e.getMessage());
            throw new ProjectException("Failed to update game status: " + e.getMessage());
        }
    }

    @Override
    public Game getGameById(Integer gameId) throws ProjectException {
        logger.info("Getting game by ID: {}", gameId);
        try {
            return gameService.findOne(gameId).get();
        } catch (Exception e) {
            logger.error("Error getting game by ID {}: {}", gameId, e.getMessage());
            throw new ProjectException("Failed to retrieve game by ID: " + e.getMessage());
        }
    }

    @Override
    public List<Game> getFinishedGames() throws ProjectException {
        Iterable<Game> allgames = gameService.findAll();
        List<Game> finishedGames = new ArrayList<>();
        for(Game game : allgames) {
            if (game.getStatus() == GameStatus.FINISHED) {
                finishedGames.add(game);
            }
        }

        finishedGames.sort(Comparator.comparing(Game::getScore).reversed());
        return finishedGames;
    }


    private void notifyGameObservers(Game game) {
        notificationExecutor = Executors.newFixedThreadPool(5);
        for (ProjectObserver client : loggedInClients.values()) {
            notificationExecutor.execute(() -> {
                try {
                    client.leaderboardUpdated();
                    logger.info("Successfully notified client (observer) about update: {}", client.getClass().getName());
                } catch (Exception e) {
                    logger.error("Unexpected error during notification for client (observer) {}: {}", client.getClass().getName(), e.getMessage(), e);
                }
            });
        }
        notificationExecutor.shutdown();
    }

}
