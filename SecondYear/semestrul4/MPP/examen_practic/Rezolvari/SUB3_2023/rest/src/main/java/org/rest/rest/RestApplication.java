package org.rest.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import persistence.database.*;
import persistence.interfaces.*;

import java.sql.SQLException;

@SpringBootApplication
public class RestApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class, args);
    }
    @Bean
    public IUserRepo userRepo() throws SQLException {
        return new UserRepo();
    }

    @Bean
    public IConfigurationRepo configurationRepo() throws SQLException {
        return new ConfigurationRepo();
    }

    @Bean
    public IConfigurationWordRepo configurationWordRepo() throws SQLException {
        return new ConfigurationWordRepo();
    }

    @Bean
    public IGameRepo gameRepo() throws SQLException {
        return new GameRepo();
    }

    @Bean
    public IPositionRepo positionRepo() throws SQLException {
        return new PositionRepo();
    }


}
