package org.server.service;

import model.ConfigurationWord;
import persistence.database.*;
import persistence.interfaces.IUserRepo;

import java.sql.SQLException;

public class GlobalService {
    private static MainService network = null;

    private GlobalService() {
    }

    public static MainService getNetwork() throws SQLException {
        if (network == null) {
            IUserRepo userRepo = new UserRepo();
            UserService userService = new UserService(userRepo);

            GameRepo gameRepo = new GameRepo();
            ConfigurationRepo configurationRepo = new ConfigurationRepo();
            ConfigurationWordRepo configurationWordRepo = new ConfigurationWordRepo();
            PositionRepo positionRepo = new PositionRepo();
            WordRepo wordRepo = new WordRepo();

            network = new MainService(userService, gameRepo, configurationRepo, positionRepo, configurationWordRepo, wordRepo);
        }
        return network;
    }
}
