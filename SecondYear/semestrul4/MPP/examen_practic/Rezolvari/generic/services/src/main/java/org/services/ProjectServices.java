package org.services;

import model.Game;
import model.Position;
import model.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ProjectServices {
    User login(String username, String password, ProjectObserver client) throws Exception;
    void logout(User user, ProjectObserver client) throws ProjectException;
    Iterable<User> getAllOrganizatori() throws ProjectException;

    Position addPosition(Position position) throws SQLException, ProjectException;

    Game addGame(Game game) throws SQLException, ProjectException;

    List<Game> getAllGames() throws SQLException, ProjectException;

    User findPlayerByAlias(String alias) throws ProjectException;

    List<Position> getAllPositions() throws SQLException, ProjectException;

    List<Position> getAllPositionsByGame(Game game) throws SQLException, ProjectException;
}
