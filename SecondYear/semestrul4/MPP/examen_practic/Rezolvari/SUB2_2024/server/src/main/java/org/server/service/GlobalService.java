package org.server.service;

import persistence.database.GameRepo;
import persistence.database.PositionRepo;
import persistence.database.UserRepo;
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
            PositionRepo positionRepo = new PositionRepo();

            network = new MainService(userService, gameRepo, positionRepo);
        }
        return network;
    }
}
