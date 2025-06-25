package persistence.database;

import model.Game;
import model.Position;
import model.Word;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import persistence.AbstractRepository;
import persistence.interfaces.IPositionRepo;
import persistence.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PositionRepo extends AbstractRepository<Integer, Position> implements IPositionRepo {
    private static final Logger logger= LogManager.getLogger(PositionRepo.class);

    private final Connection connection;

    public PositionRepo() throws SQLException {
        logger.info("Initializing OrganizatorRepository");
        this.connection = JdbcUtils.getInstance().getConnection();
    }

    @Override
    protected PreparedStatement findOneQuery(Integer id) throws SQLException {
        String query = "SELECT * FROM positions WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setObject(1, id);

        return statement;
    }

    @Override
    protected PreparedStatement findAllQuery() throws SQLException {
        String query = "SELECT * FROM positions";
        return connection.prepareStatement(query);
    }

    @Override
    protected PreparedStatement saveQuery(Position entity) throws SQLException {
        String query = "INSERT INTO positions(game_id, coordinateX, coordinateY, position_index) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setObject(1, entity.getGame().getId());
        statement.setObject(2, entity.getCoordinateX());
        statement.setObject(3, entity.getCoordinateY());
        statement.setObject(4, entity.getPositionIndex());
        return statement;
    }

    @Override
    protected Position buildEntity(ResultSet resultSet) throws SQLException {
        Integer id = resultSet.getInt("id");
        Integer gameId = resultSet.getInt("game_id");
        Integer coordinateX = resultSet.getInt("coordinateX");
        Integer coordinateY = resultSet.getInt("coordinateY");
        Integer positionIndex = resultSet.getInt("position_index");

        GameRepo gameRepo = new GameRepo();
        Optional<Game> game = gameRepo.findOne(gameId);

        if(game.isPresent()) {
            Game gameObj = game.get();
            Position position = new Position(gameObj, coordinateX, coordinateY, positionIndex);
            position.setId(id);
            return position;
        }

        return null;
    }

    private PreparedStatement findAllByGameQuery(Game game) throws SQLException {
        String query = "SELECT * FROM positions WHERE game_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setObject(1, game.getId());

        return statement;
    }

    @Override
    public Iterable<Position> findAllByGame(Game game) throws SQLException {
        logger.trace("Loading entities...");
        List<Position> entities = new ArrayList<>();

        try (PreparedStatement statement = findAllByGameQuery(game)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Position entity = buildEntity(resultSet);
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
