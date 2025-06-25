//package ro.mpp2025.javaprojectui.database;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import ro.mpp2025.javaprojectui.AbstractRepository;
//import ro.mpp2025.javaprojectui.ParticipantORM;
//import ro.mpp2025.javaprojectui.interfaces.IParticipantRepository;
//import ro.mpp2025.javaprojectui.utils.JdbcUtils;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//public class ParticipantRepository extends AbstractRepository<Integer, ParticipantORM> implements IParticipantRepository {
//
//    private static final Logger logger= LogManager.getLogger(ParticipantRepository.class);
//
//    private final Connection connection;
//
//    public ParticipantRepository() throws SQLException {
//        logger.info("Initializing ParticipantRepository ");
//        this.connection = JdbcUtils.getInstance().getConnection();
//    }
//
//    @Override
//    protected PreparedStatement findOneQuery(Integer id) throws SQLException {
//        String query = "SELECT * FROM participanti WHERE id = ?";
//        PreparedStatement statement = connection.prepareStatement(query);
//        statement.setObject(1, id);
//
//        return statement;
//    }
//
//    @Override
//    protected PreparedStatement findAllQuery() throws SQLException {
//        String query = "SELECT * FROM participanti";
//        return connection.prepareStatement(query);
//    }
//
//    @Override
//    protected PreparedStatement saveQuery(ParticipantORM entity) throws SQLException {
//        String query = "INSERT INTO participanti(id, nume, cnp) VALUES (?, ?, ?)";
//        PreparedStatement statement = connection.prepareStatement(query);
//        statement.setObject(1, entity.getId());
//        statement.setString(2, entity.getNume());
//        statement.setString(3, entity.getCnp());
//        return statement;
//    }
//
//    @Override
//    protected ParticipantORM buildEntity(ResultSet resultSet) throws SQLException {
//        Integer id = resultSet.getInt("id");
//        String nume = resultSet.getString("nume");
//        String cnp = resultSet.getString("cnp");
//
//        ParticipantORM participant = new ParticipantORM(nume, cnp);
//        participant.setId(id);
//
//        return participant;
//    }
//
//}
