package persistence.database;

import model.Game;
import model.GamePosition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import persistence.AbstractRepository;
import persistence.interfaces.IGamePositionsRepo;
import persistence.interfaces.IGameRepo;
import persistence.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GamePositionsRepo extends AbstractRepository<Integer, GamePosition> implements IGamePositionsRepo {
    private static final Logger logger= LogManager.getLogger(GamePositionsRepo.class);

    private final Connection connection;

    public GamePositionsRepo() {
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
    protected PreparedStatement saveQuery(GamePosition entity) throws SQLException {
        String query = "INSERT INTO positions(id, gameId, x, y, status, hasBoat, isRevealed) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setObject(1, entity.getId());
        statement.setInt(2, entity.getGameId());
        statement.setInt(3, entity.getX());
        statement.setInt(4, entity.getY());
        statement.setString(5, entity.getStatus().toString());
        statement.setBoolean(6, entity.isHasBoat());
        statement.setBoolean(7, entity.isRevealed());
        return statement;
    }

    @Override
    protected GamePosition buildEntity(ResultSet resultSet) throws SQLException {
        Integer id = resultSet.getInt("id");
        Integer gameId = resultSet.getInt("gameId");
        int x = resultSet.getInt("x");
        int y = resultSet.getInt("y");

        IGameRepo repo = new GameRepo();
        Optional<Game> gameOp = repo.findOne(gameId);
        Game game;
        if(gameOp.isPresent()) {
            game = gameOp.get();
            GamePosition position = new GamePosition(game, x, y);
            position.setId(id);

            return position;
        }
        else{
            throw new RuntimeException("Game not found (GamePositionsRepo)!");
        }

    }

    @Override
    public GamePosition update(GamePosition position) throws SQLException {
        String sql = "UPDATE positions SET status = ?, hasBoat = ?, isRevealed = ? , shotOrder = ?, shotTimestamp = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, position.getStatus().toString());
            stmt.setBoolean(2, position.isHasBoat());
            stmt.setBoolean(3, position.isRevealed());
            stmt.setInt(4, position.getShotOrder());
            stmt.setString(5, position.getShotTimestamp().toString());
            stmt.setInt(6, position.getId());

            stmt.executeUpdate();
        }

        return position;
    }

    @Override
    public GamePosition findByGameAndCoordinates(Integer gameId, int x, int y) throws SQLException {
        String sql = "SELECT * FROM positions WHERE gameId = ? AND x = ? AND y = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, gameId);
            stmt.setInt(2, x);
            stmt.setInt(3, y);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return buildEntity(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<GamePosition> findByGameId(Integer gameId) throws SQLException {
        String sql = "SELECT * FROM positions WHERE gameId = ? ORDER BY x, y";
        List<GamePosition> positions = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, gameId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    positions.add(buildEntity(rs));
                }
            }
        }

        return positions;
    }

    @Override
    public List<GamePosition> findRevealedByGameId(Integer gameId) throws SQLException {
        String sql = "SELECT * FROM positions WHERE gameId = ? AND isRevealed = TRUE ORDER BY x, y";
        List<GamePosition> positions = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, gameId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    positions.add(buildEntity(rs));
                }
            }
        }

        return positions;
    }

    @Override
    public List<GamePosition> findBoatPositionsByGameId(Integer gameId) throws SQLException {
        String sql = "SELECT * FROM positions WHERE gameId = ? AND hasBoat = TRUE ORDER BY x, y";
        List<GamePosition> positions = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, gameId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    positions.add(buildEntity(rs));
                }
            }
        }

        return positions;
    }
}
