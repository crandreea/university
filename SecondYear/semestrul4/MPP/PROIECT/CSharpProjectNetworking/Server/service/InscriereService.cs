using Model;
using Persistence;

namespace Server;

public class InscriereService : AbstractService<Tuple<Participant, Proba>, Inscriere>
{
    public InscriereService(IRepository<Tuple<Participant, Proba>, Inscriere> repository) : base(repository)
    {
    }
}