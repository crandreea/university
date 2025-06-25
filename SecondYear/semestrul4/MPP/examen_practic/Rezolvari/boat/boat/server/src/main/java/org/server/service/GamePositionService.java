package org.server.service;

import model.GamePosition;
import persistence.database.GamePositionsRepo;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class GamePositionService extends AbstractService<Integer, GamePosition> {
    private final GamePositionsRepo gamePositionsRepo;

    public GamePositionService(GamePositionsRepo repository) {
        super(repository);
        this.gamePositionsRepo = repository;
    }

    public List<GamePosition> findBoatPositionsByGameId(Integer gameId) throws SQLException {
        return gamePositionsRepo.findBoatPositionsByGameId(gameId);
    }

    public Optional<GamePosition> update(GamePosition position) throws SQLException {
        return Optional.ofNullable(gamePositionsRepo.update(position));
    }

    public GamePosition findByGameAndCoordinates(Integer gameId, int x, int y) throws SQLException {
        return gamePositionsRepo.findByGameAndCoordinates(gameId, x, y);
    }

    public List<GamePosition> findByGameId(Integer gameId) throws SQLException {
        return gamePositionsRepo.findByGameId(gameId);
    }

    public List<GamePosition> findRevealedByGameId(Integer gameId) throws SQLException {
        return gamePositionsRepo.findRevealedByGameId(gameId);
    }
}
