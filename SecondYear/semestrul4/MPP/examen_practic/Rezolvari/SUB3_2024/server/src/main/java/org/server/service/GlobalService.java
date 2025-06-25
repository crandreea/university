package org.server.service;

import persistence.database.ConfigurationRepo;
import persistence.database.GameRepo;
import persistence.database.UserRepo;
import persistence.interfaces.IConfigurationRepo;
import persistence.interfaces.IGameRepo;
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

            IGameRepo gameRepo = new GameRepo();
            IConfigurationRepo configRepo = new ConfigurationRepo();

            network = new MainService(userService, gameRepo, configRepo);
        }
        return network;
    }
}
