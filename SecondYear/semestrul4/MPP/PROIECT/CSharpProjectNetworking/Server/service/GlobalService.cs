
using Persistence.database;

namespace Server;

public static class GlobalService
{
    private static MainService network ;

    public static MainService GetNetwork()
    {
        if (network == null)
        {
            var organizatorRepository = new OrganizatorRepository();
            var participantRepository = new ParticipantRepository();
            var probaRepository = new ProbaRepository();
            var inscriereRepository = new InscriereRepository();
            var categorieVarstaRepository = new CategorieVarstaRepository();

            var organizatorService = new OrganizatorService(organizatorRepository);
            var participantService = new ParticipantService(participantRepository);
            var probaService = new ProbaService(probaRepository);
            var inscriereService = new InscriereService(inscriereRepository);
            var categorieVarstaService = new CategorieVarstaService(categorieVarstaRepository);

            network = new MainService(organizatorService, participantService, probaService, inscriereService, categorieVarstaService);
        }
        return network;
    }
}