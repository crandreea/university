package org.rest.rest;

import model.Game;
import model.GamePosition;
import model.User;
import org.server.service.*;
import org.services.ProjectServices;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import persistence.database.GamePositionsRepo;
import persistence.database.GameRepo;
import persistence.database.UserRepo;

import java.sql.SQLException;
import java.util.Properties;

@SpringBootApplication
public class RestApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class, args);
    }

    @Bean
    public ProjectServices projectServices() throws SQLException {

        UserRepo userRepo = new UserRepo();
        GameRepo gameRepo = new GameRepo();
        GamePositionsRepo gamePositionRepo = new GamePositionsRepo();

        Service<Integer, User> userService = new UserService(userRepo);
        Service<Integer, Game> gameService = new GameService(gameRepo);
        Service<Integer, GamePosition> gamePositionService = new GamePositionService(gamePositionRepo);

        return new MainService(userService, gameService, gamePositionService);
    }

}
