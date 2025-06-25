package org.server.service;

import model.Configuration;
import model.Game;
import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.server.Authenticator;
import org.services.ProjectException;
import org.services.ProjectObserver;
import org.services.ProjectServices;
import persistence.interfaces.IConfigurationRepo;
import persistence.interfaces.IGameRepo;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.stream.StreamSupport;

public class MainService implements ProjectServices {
    private final Service<Integer, User> userService;

    private final IGameRepo gameRepository;
    private final IConfigurationRepo configurationRepository;

    private final Map<String, ProjectObserver> loggedInClients;
    private ExecutorService notificationExecutor;

    private User currentUser;
    private static final Logger logger = LogManager.getLogger(MainService.class);

    public MainService(Service<Integer, User> userService, IGameRepo gameRepository, IConfigurationRepo configurationRepository) {
        this.userService = userService;
        this.gameRepository = gameRepository;
        this.configurationRepository = configurationRepository;

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
    public synchronized Game addGame(Game game) throws SQLException, ProjectException {
        logger.info("Adding Game: " + game + " ...");
        Game addedGame = gameRepository.save(game).orElseThrow(() -> new ProjectException("Error adding the game."));
        // notify all clients that a new game was added
        for (ProjectObserver client: loggedInClients.values()) {
            client.gameAdded(addedGame);
        }
        return addedGame;
    }

    @Override
    public synchronized List<Game> getAllGames() {
        logger.info("Getting all games...");
        Iterable<Game> games = gameRepository.findAll();
        return StreamSupport.stream(games.spliterator(), false).toList();
    }

    @Override
    public synchronized List<Configuration> getAllConfigurations() {
        logger.info("Getting all configurations...");
        Iterable<Configuration> configurations = configurationRepository.findAll();
        return StreamSupport.stream(configurations.spliterator(), false).toList();
    }

    @Override
    public synchronized User findPlayerByAlias(String alias) {
        Iterable<User> players = userService.findAll();
        for (User player : players) {
            if (player.getUsername().equals(alias)) {
                return player;
            }
        }
        return null;
    }
}
