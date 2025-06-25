package org.server.service;

import model.Game;
import model.GamePosition;
import org.hibernate.Session;
import persistence.Repository;
import persistence.database.GameRepo;
import persistence.hibernate.HibernateUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class GameService extends AbstractService<Integer, Game> {
    private final GameRepo gameRepo;
    public GameService(GameRepo gameRepo) {
        super(gameRepo);
        this.gameRepo = gameRepo;
    }

    public List<Game> findByUserId(Integer userId) throws SQLException {
       return gameRepo.findByUserId(userId);
    }

    public List<Game> findByStatus(String status) throws SQLException {
        return gameRepo.findByStatus(status);
    }

    public Optional<Game> update(Game game) throws SQLException {
        return Optional.ofNullable(gameRepo.update(game));
    }
}
