package ro.mpp2025.javaprojectui.database.hibernate;
import org.hibernate.Session;
import ro.mpp2025.javaprojectui.Organizator;
import ro.mpp2025.javaprojectui.hibernate.HibernateUtils;
import ro.mpp2025.javaprojectui.interfaces.IOrganizatorRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ro.mpp2025.javaprojectui.orm.OrganizatorORM;

import java.util.List;
import java.util.Optional;

public class OrganizatorRepositoryHibernate implements IOrganizatorRepository {

    private static final Logger logger= LogManager.getLogger(OrganizatorRepositoryHibernate.class);

    private Organizator toOrganizator(OrganizatorORM entityORM) {
        if (entityORM == null) return null;
        Organizator entity = new Organizator(entityORM.getUsername(), entityORM.getPassword());
        entity.setId(entityORM.getId());
        return entity;
    }

    private OrganizatorORM toOrganizatorOrm(Organizator entity) {
        if (entity == null) return null;
        OrganizatorORM entityORM = new OrganizatorORM(entity.getUsername(), entity.getPassword());
        entityORM.setId(entity.getId());
        return entityORM;
    }

    @Override
    public Optional<Organizator> findOne(Integer integer) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            logger.info("Apel findOne organizatori");
            OrganizatorORM entityORM = session.get(OrganizatorORM.class, integer);
            return Optional.ofNullable(toOrganizator(entityORM));
        }
    }

    @Override
    public Iterable<Organizator> findAll() {
        logger.info("Apel findAll organizatori");
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            List<OrganizatorORM> entities = session.createQuery("from OrganizatorORM", OrganizatorORM.class).getResultList();
            return entities.stream().map(this::toOrganizator).toList();
        }
    }

    @Override
    public Optional<Organizator> save(Organizator entity) {
        return HibernateUtils.getSessionFactory().fromTransaction(session -> {
            if(entity.getId() != null) {
                OrganizatorORM employeeOrm = session.get(OrganizatorORM.class, entity.getId());
                if (employeeOrm != null) {
                    return Optional.of(entity);
                }
            }
            session.persist(toOrganizatorOrm(entity));
            return Optional.empty();
        });
    }

}
