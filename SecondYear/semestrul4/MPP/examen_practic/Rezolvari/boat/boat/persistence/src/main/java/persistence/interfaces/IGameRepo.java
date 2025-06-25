package persistence.interfaces;

import model.Game;
import persistence.Repository;

import java.sql.SQLException;
import java.util.List;

public interface IGameRepo extends Repository<Integer, Game> {
    public Game update(Game entity) throws SQLException;

    List<Game> findByUserId(Integer userId) throws SQLException;

    List<Game> findByStatus(String status) throws SQLException;
}
