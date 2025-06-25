package org.server.service;

import persistence.database.GamePositionsRepo;
import persistence.database.GameRepo;
import persistence.database.UserRepo;
import persistence.interfaces.IGamePositionsRepo;
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

            GameRepo boatRepo = new GameRepo();
            GameService boatService = new GameService(boatRepo);

            GamePositionsRepo gamePositionsRepo = new GamePositionsRepo();
            GamePositionService gamePositionService = new GamePositionService(gamePositionsRepo);


            network = new MainService(userService, boatService, gamePositionService);
        }
        return network;
    }
}
