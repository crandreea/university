package ro.mpp2025.javaprojectui.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ro.mpp2025.javaprojectui.*;
import ro.mpp2025.javaprojectui.database.hibernate.ParticipantRepositoryHibernate;
import ro.mpp2025.javaprojectui.interfaces.IInscriereRepository;
import ro.mpp2025.javaprojectui.interfaces.IParticipantRepository;
import ro.mpp2025.javaprojectui.Participant;
import ro.mpp2025.javaprojectui.utils.JdbcUtils;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class InscriereRepository extends AbstractRepository<Tuplu<Participant, Proba>, Inscriere> implements IInscriereRepository {

    private static final Logger logger= LogManager.getLogger(InscriereRepository.class);

    private final Connection connection;

    public InscriereRepository() throws SQLException {
        logger.info("Initializing InscriereRepository");
        this.connection = JdbcUtils.getInstance().getConnection();
    }

    @Override
    protected PreparedStatement findOneQuery(Tuplu<Participant, Proba> id) throws SQLException {
        String query = " SELECT * FROM inscrieri" +
                " INNER JOIN main.participanti p on p.id = inscrieri.participant" +
                " INNER JOIN main.probe p2 on p2.proba_id = inscrieri.proba" +
                " INNER JOIN main.categorievarsta c on c.id = p2.varsta" +
                " WHERE proba = ? AND participant = ? ";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setObject(1, id.getE1());
        statement.setObject(2, id.getE2());
        return statement;
    }

    @Override
    protected PreparedStatement findAllQuery() throws SQLException {
        String query = " SELECT * FROM inscrieri" +
                        " INNER JOIN main.participanti p on p.id = inscrieri.participant" +
                        " INNER JOIN main.probe p2 on p2.proba_id = inscrieri.proba" +
                        " INNER JOIN main.categorievarsta c on c.id = p2.varsta";
        return connection.prepareStatement(query);
    }

    @Override
    protected PreparedStatement saveQuery(Inscriere entity) throws SQLException {
        String query = "INSERT INTO inscrieri(participant, proba) VALUES (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, entity.getIDparticipant().getId());
        preparedStatement.setLong(2, entity.getIDproba().getId());
        return preparedStatement;
    }

    @Override
    protected Inscriere buildEntity(ResultSet resultSet) throws SQLException {
        Integer part_id = resultSet.getInt("id");
        Integer proba_id = resultSet.getInt("proba_id");

        //schimbare
        IParticipantRepository participantRepository = new ParticipantRepositoryHibernate();
        Optional<Participant> participantOp = participantRepository.findOne(part_id);

        ProbaRepository probaRepository = new ProbaRepository();
        Optional<Proba> probaOp = probaRepository.findOne(proba_id);

        if (participantOp.isPresent() && probaOp.isPresent()) {
            Participant participant = participantOp.get();
            Proba proba = probaOp.get();
            return new Inscriere(participant, proba);
        } else {
            throw new RuntimeException("Participant and Proba not found");
        }

    }

    @Override
    public Optional<Inscriere> save(Inscriere entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity-ul nu poate fi null!");
        }
        logger.trace("Saving entity: {}", entity);
        try (PreparedStatement statement = saveQuery(entity)) {
            int result = statement.executeUpdate();
            if(result == 0){
                logger.error("Failed to save entity: No rows affected");
                return Optional.empty();
            }
            return Optional.of(entity);
        } catch (SQLException e) {
            logger.error("Error saving entity: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }


}
