package org.services;

import model.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ProjectServices {
    User login(String username, String password, ProjectObserver client) throws Exception;
    void logout(User user, ProjectObserver client) throws ProjectException;
    Iterable<User> getAllOrganizatori() throws ProjectException;

    Optional<User> findUserById(Integer userId) throws ProjectException, SQLException;

    Game createGame(User user) throws ProjectException;
    ShotResultDTO makeShot(Integer gameId, int x, int y) throws ProjectException;
    List<Game> getUserGames(User user) throws ProjectException;
    List<GamePosition> getGamePositions(Integer gameId) throws ProjectException;
    List<GamePosition> getRevealedGamePositions(Integer gameId) throws ProjectException;
    List<GamePosition> getBoatGamePositions(Integer gameId) throws ProjectException;
    Game updateGameStatus(Integer gameId, GameStatus newStatus) throws ProjectException;
    Game getGameById(Integer gameId) throws ProjectException;
    List<Game> getFinishedGames() throws ProjectException;

    Game update(Game game) throws ProjectException, SQLException;

    GamePosition findByGameAndCoordinates(Integer id, int x, int y) throws SQLException;
}
