package ro.mpp2025.javaprojectui.hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ro.mpp2025.javaprojectui.Participant;
import ro.mpp2025.javaprojectui.orm.OrganizatorORM;

public class HibernateUtils {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if ((sessionFactory == null) || (sessionFactory.isClosed())) {
            sessionFactory = createNewSessionFactory();
        }
        return sessionFactory;
    }

    public static SessionFactory createNewSessionFactory() {
        try{
            return new Configuration()
                    .addAnnotatedClass(OrganizatorORM.class)
                    .addAnnotatedClass(Participant.class)
                    .buildSessionFactory();
        } catch (Exception ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static void closeSessionFactory() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}

