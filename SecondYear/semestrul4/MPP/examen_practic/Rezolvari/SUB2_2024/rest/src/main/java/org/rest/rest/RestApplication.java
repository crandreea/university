package org.rest.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class, args);
    }

//    @Bean
//    public UserRepo userRepo() throws SQLException {
//
//        UserRepo userRepo = new UserRepo();
//        return userRepo;
//    }
//
//    @Bean
//    public GameRepo gameRepo() throws SQLException {
//
//        GameRepo userRepo = new GameRepo();
//        return userRepo;
//    }
//
//    @Bean
//    public ConfigurationRepo configurationRepo() throws SQLException {
//
//        ConfigurationRepo userRepo = new ConfigurationRepo();
//        return userRepo;
//    }
}
