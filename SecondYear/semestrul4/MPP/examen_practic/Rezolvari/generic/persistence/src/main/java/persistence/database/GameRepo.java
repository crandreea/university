package persistence.database;

import model.Game;
import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import persistence.AbstractRepository;
import persistence.interfaces.IGameRepo;
import persistence.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GameRepo extends AbstractRepository<Integer, Game> implements IGameRepo {
    private static final Logger logger= LogManager.getLogger(GameRepo.class);

    private final Connection connection;

    public GameRepo() throws SQLException {
        logger.info("Initializing OrganizatorRepository");
        this.connection = JdbcUtils.getInstance().getConnection();
    }

    @Override
    protected PreparedStatement findOneQuery(Integer id) throws SQLException {
        String query = "SELECT * FROM games WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setObject(1, id);

        return statement;
    }

    @Override
    protected PreparedStatement findAllQuery() throws SQLException {
        String query = "SELECT * FROM games";
        return connection.prepareStatement(query);
    }

    @Override
    protected PreparedStatement saveQuery(Game entity) throws SQLException {
        String query = "INSERT INTO games(player_id, no_of_seconds, score)VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setObject(1, entity.getPlayer().getId());
        statement.setInt(2, entity.getNoOfSeconds());
        statement.setInt(3, entity.getScore());
        return statement;
    }

    @Override
    protected Game buildEntity(ResultSet resultSet) throws SQLException {
        Integer id = resultSet.getInt("id");
        Integer userid = resultSet.getInt("player_id");
        Integer noOfSeconds = resultSet.getInt("no_of_seconds");
        Integer score = resultSet.getInt("score");

        UserRepo userRepo = new UserRepo();
        Optional<User> user = userRepo.findOne(userid);

        if (user.isPresent()) {
            Game game = new Game(user.get(), noOfSeconds, score);
            game.setId(id);
            return game;
        }

        return null;
    }

    private PreparedStatement findAllByPlayerQuery(User player) throws SQLException {
        String query = "SELECT * FROM games WHERE player_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setObject(1, player.getId());

        return statement;
    }

    @Override
    public List<Game> findAllByPlayer(User player) throws SQLException {
        logger.trace("Loading entities...");
        List<Game> entities = new ArrayList<>();

        try (PreparedStatement statement = findAllByPlayerQuery(player)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Game entity = buildEntity(resultSet);
                    entities.add(entity);
                }
            }
        } catch (SQLException e) {
            logger.error("Error loading entities: {}", e.getMessage());
            throw new RuntimeException(e);
        }

        return entities;
    }
}
