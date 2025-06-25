package ro.mpp2025.javaprojectui.database.hibernate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import ro.mpp2025.javaprojectui.Participant;
import ro.mpp2025.javaprojectui.hibernate.HibernateUtils;
import ro.mpp2025.javaprojectui.interfaces.IParticipantRepository;

import java.util.List;
import java.util.Optional;

public class ParticipantRepositoryHibernate implements IParticipantRepository {

    private static final Logger logger= LogManager.getLogger(OrganizatorRepositoryHibernate.class);


    @Override
    public Optional<Participant> findOne(Integer integer) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            logger.info("Apel findOne organizatori");
            ro.mpp2025.javaprojectui.Participant entityORM = session.get(ro.mpp2025.javaprojectui.Participant.class, integer);
            return Optional.ofNullable(entityORM);
        }
    }

    @Override
    public Iterable<Participant> findAll() {
        logger.info("Apel findAll organizatori");
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            List<ro.mpp2025.javaprojectui.Participant> entities = session.createQuery("from Participant", ro.mpp2025.javaprojectui.Participant.class).getResultList();
            return entities;
        }
    }

    @Override
    public Optional<Participant> save(Participant entity) {
        return HibernateUtils.getSessionFactory().fromTransaction(session -> {
            if(entity.getId() != null) {
                ro.mpp2025.javaprojectui.Participant employeeOrm = session.get(ro.mpp2025.javaprojectui.Participant.class, entity.getId());
                if (employeeOrm != null) {
                    return Optional.of(entity);
                }
            }
            session.persist(entity);
            return Optional.empty();
        });
    }
}
