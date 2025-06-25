package persistence.interfaces;

import model.GamePosition;
import persistence.Repository;

import java.sql.SQLException;
import java.util.List;

public interface IGamePositionsRepo extends Repository<Integer, GamePosition> {
    public GamePosition update(GamePosition position) throws SQLException;


    GamePosition findByGameAndCoordinates(Integer gameId, int x, int y) throws SQLException;

    List<GamePosition> findByGameId(Integer gameId) throws SQLException;

    List<GamePosition> findRevealedByGameId(Integer gameId) throws SQLException;

    List<GamePosition> findBoatPositionsByGameId(Integer gameId) throws SQLException;
}
