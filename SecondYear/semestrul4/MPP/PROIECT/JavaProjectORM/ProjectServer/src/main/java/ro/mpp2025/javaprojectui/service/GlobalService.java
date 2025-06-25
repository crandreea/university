package ro.mpp2025.javaprojectui.service;

import ro.mpp2025.javaprojectui.database.*;
import ro.mpp2025.javaprojectui.database.hibernate.OrganizatorRepositoryHibernate;
import ro.mpp2025.javaprojectui.database.hibernate.ParticipantRepositoryHibernate;
import ro.mpp2025.javaprojectui.interfaces.IOrganizatorRepository;
import ro.mpp2025.javaprojectui.interfaces.IParticipantRepository;

import java.sql.SQLException;

public class GlobalService {
    private static MainService network = null;

    private GlobalService() {
    }

    public static MainService getNetwork() throws SQLException {
        if (network == null) {
//            OrganizatorRepository organizatorRepository = new OrganizatorRepository();
//            ParticipantRepository participantRepository = new ParticipantRepository();
            IOrganizatorRepository organizatorRepository = new OrganizatorRepositoryHibernate();
            IParticipantRepository participantRepository = new ParticipantRepositoryHibernate();
            ProbaRepository probaRepository = new ProbaRepository();
            InscriereRepository inscriereRepository = new InscriereRepository();
            CategorieVarstaRepository categorieVarstaRepository = new CategorieVarstaRepository();


            OrganizatorService organizatorService = new OrganizatorService(organizatorRepository);
            ParticipantService participantService = new ParticipantService(participantRepository);
            ProbaService probaService = new ProbaService(probaRepository);
            InscriereService inscriereService = new InscriereService(inscriereRepository);
            CategorieVarstaService categorieVarstaService = new CategorieVarstaService(categorieVarstaRepository);

            network = new MainService(organizatorService, participantService, probaService, inscriereService, categorieVarstaService);
        }
        return network;
    }
}
