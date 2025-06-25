package org.services;

import model.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ProjectServices {
    User login(String username, String password, ProjectObserver client) throws Exception;
    void logout(User user, ProjectObserver client) throws ProjectException;
    Iterable<User> getAllOrganizatori() throws ProjectException;

    Position addPosition(Position position) throws SQLException, ProjectException;

    Configuration addConfiguration(Configuration configuration) throws SQLException, ProjectException;

    ConfigurationWord addConfigurationWord(ConfigurationWord configurationWord) throws SQLException, ProjectException;

    Game addGame(Game game) throws SQLException, ProjectException;

    List<Game> getAllGames() throws SQLException, ProjectException;

    List<Configuration> getAllConfigurations() throws SQLException, ProjectException;

    List<Word> getAllWords() throws SQLException, ProjectException;

    User findPlayerByAlias(String alias) throws ProjectException;
}
