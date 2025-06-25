package persistence.database;

import model.Game;
import model.Position;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import persistence.hibernate.HibernateUtils;
import persistence.interfaces.IPositionRepo;

import java.sql.SQLException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Repository
public class PositionRepo implements IPositionRepo {
    private static final Logger logger = LogManager.getLogger(PositionRepo.class);

    public PositionRepo() {
    }

    @Override
    public Optional<Position> findOne(Integer id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.createQuery("FROM Position WHERE id=:idM", Position.class)
                    .setParameter("idM", id)
                    .getSingleResult());
        }
    }

    @Override
    public Iterable<Position> findAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("FROM Position ", Position.class).getResultList();
        } catch (Exception e) {
            logger.error("Error finding all Games", e);
            return null;
        }
    }

    @Override
    public Optional<Position> save(Position entity) throws SQLException {
        HibernateUtils.getSessionFactory().inTransaction(session -> session.merge(entity));
        return Optional.of(entity);
    }

    @Override
    public Optional<Position> delete(Integer aLong) {
        return Optional.empty();
    }

    @Override
    public Optional<Position> update(Position entity) {
        final AtomicReference<Optional<Position>> result = new AtomicReference<>();

        HibernateUtils.getSessionFactory().inTransaction(session -> {
            Position updatedGame = (Position) session.merge(entity);
            result.set(Optional.ofNullable(updatedGame));
        });

        return result.get();
    }

    @Override
    public Iterable<Position> findByGameId(Integer gameId) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("FROM Position WHERE game =: gameId", Position.class).setParameter("gameId", gameId)
                    .getResultList();
        } catch (Exception e) {
            logger.error("Error finding all Positions", e);
            return null;
        }
    }

    @Override
    public Iterable<Position> findTraps(Integer gameId) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("FROM Position WHERE trap = true and game =: gameId", Position.class).setParameter("gameId", gameId)
                    .getResultList();
        } catch (Exception e) {
            logger.error("Error finding all Positions", e);
            return null;
        }
    }

    @Override
    public Iterable<Position> getAllPositionsByGame(Game game) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("FROM Position WHERE game.id =: gameId", Position.class).setParameter("gameId", game.getId())
                    .getResultList();
        } catch (Exception e) {
            logger.error("Error finding all Positions", e);
            return null;
        }
    }
}
