package org.services;

import model.Configuration;
import model.Game;
import model.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ProjectServices {
    User login(String username, String password, ProjectObserver client) throws Exception;
    void logout(User user, ProjectObserver client) throws ProjectException;
    Iterable<User> getAllOrganizatori() throws ProjectException;

    Game addGame(Game game) throws SQLException, ProjectException;

    List<Game> getAllGames() throws ProjectException;

    List<Configuration> getAllConfigurations() throws ProjectException;

    User findPlayerByAlias(String username) throws ProjectException;
}
