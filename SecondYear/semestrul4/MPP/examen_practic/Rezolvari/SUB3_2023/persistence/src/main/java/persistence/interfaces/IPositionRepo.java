package persistence.interfaces;

import model.Game;
import model.Position;
import persistence.Repository;

import java.sql.SQLException;

public interface IPositionRepo extends Repository<Integer, Position> {
    Iterable<Position> findAllByGame(Game game) throws SQLException;
}
