package persistence.interfaces;

import model.Game;
import model.User;
import persistence.Repository;

import java.sql.SQLException;
import java.util.List;

public interface IGameRepo extends Repository<Integer, Game> {
    List<Game> findAllByPlayer(User player) throws SQLException;
}
