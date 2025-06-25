package persistence.interfaces;

import model.Game;
import model.Position;
import persistence.Repository;

import java.util.Optional;

public interface IPositionRepo extends Repository<Integer, Position> {
    Optional<Position> delete(Integer aLong);

    Optional<Position> update(Position entity);

    Iterable<Position> findByGameId(Integer gameId);

    Iterable<Position> findTraps(Integer gameId);

    Iterable<Position> getAllPositionsByGame(Game game);
}
