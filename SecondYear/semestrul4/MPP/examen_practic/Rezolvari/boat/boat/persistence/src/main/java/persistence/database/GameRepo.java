package persistence.database;

import model.Game;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import persistence.hibernate.HibernateUtils;
import persistence.interfaces.IGameRepo;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class GameRepo implements IGameRepo {
    private static final Logger logger= LogManager.getLogger(GameRepo.class);

    @Override
    public Optional<Game> findOne(Integer integer) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            logger.info("Apel findOne repo...");
            Game entityORM = session.get(Game.class, integer);
            return Optional.ofNullable(entityORM);
        }
    }

    @Override
    public Iterable<Game> findAll() {
        logger.info("Apel findAll repo...");
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            List<Game> entities = session.createQuery("from Game", Game.class).getResultList();
            return entities;
        }
    }

    @Override
    public Optional<Game> save(Game entity) throws SQLException {
        return HibernateUtils.getSessionFactory().fromTransaction(session -> {
            if(entity.getId() != null) {
                Game entityOrm = session.get(Game.class, entity.getId());
                if (entityOrm != null) {
                    return Optional.of(entity);
                }
            }
            logger.debug("Saving game with user: {}", entity.getUser_id());

            session.persist(entity);
            return Optional.empty();
        });
    }

    @Override
    public List<Game> findByUserId(Integer userId) throws SQLException {
        try(Session session = HibernateUtils.getSessionFactory().openSession()) {
            List<Game> entities = session.createQuery("from Game where user_id = ? ORDER BY start_time DESC ", Game.class).setParameter(1, userId).list();
            return entities;
        }
    }

    @Override
    public List<Game> findByStatus(String status) throws SQLException {
        try(Session session = HibernateUtils.getSessionFactory().openSession()) {
            List<Game> entities = session.createQuery("from Game where status = ? ", Game.class).setParameter(1, status).list();
            return entities;
        }
    }

    @Override
    public Game update(Game entity) throws SQLException {
        logger.info("Entering update for game: {}", entity);
        Transaction transaction = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Game existing = session.get(Game.class, entity.getId());
            if (existing == null) {
                logger.error("Game not found with id {} for update.", entity.getId());
                throw new SQLException("Game not found with id: " + entity.getId());
            }

            existing.setStatus(entity.getStatus());
            existing.setTotal_shots(entity.getTotal_shots());
            existing.setSuccessful_shots(entity.getSuccessful_shots());
            existing.setScore(entity.getScore());
            existing.setEnd_time(entity.getEnd_time());

            session.merge(existing);
            transaction.commit();
            logger.info("Updated game: {}", existing);
            return existing;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error updating game {}: {}", entity, e.getMessage(), e);
            throw new SQLException("Error updating game: " + e.getMessage(), e);
        }
    }
}
