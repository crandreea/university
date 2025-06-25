package org.rest.rest;

import model.Configuration;
import model.Game;
import model.User;
import org.server.service.MainService;
import org.server.service.Service;
import org.server.service.UserService;
import org.services.ProjectServices;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import persistence.database.ConfigurationRepo;
import persistence.database.GameRepo;
import persistence.database.UserRepo;

import java.sql.SQLException;

@SpringBootApplication
public class RestApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class, args);
    }

    @Bean
    public UserRepo userRepo() throws SQLException {

        UserRepo userRepo = new UserRepo();
        return userRepo;
    }

    @Bean
    public GameRepo gameRepo() throws SQLException {

        GameRepo userRepo = new GameRepo();
        return userRepo;
    }

    @Bean
    public ConfigurationRepo configurationRepo() throws SQLException {

        ConfigurationRepo userRepo = new ConfigurationRepo();
        return userRepo;
    }
}
