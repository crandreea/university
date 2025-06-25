package persistence.interfaces;

import model.Game;
import model.User;
import persistence.Repository;

import java.util.Optional;

public interface IGameRepo extends Repository<Integer, Game> {
    Optional<Game> delete(Long aLong);

    Optional<Game> update(Game entity);

    Iterable<Game> findAllByPlayer(User player);
}
