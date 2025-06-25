
using Model;
using Persistence.database;

namespace Server;
public class ProbaService : AbstractService<int, Proba>
{
    private readonly ProbaRepository probaRepository;

    public ProbaService(ProbaRepository repository) : base(repository)
    {
        this.probaRepository = repository;
    }

    public Proba FindProbaByName(string name)
    {
        return probaRepository.FindProbaByName(name);
    }

    public Proba FindProbaByNameAndRange(string name, int range)
    {
        return probaRepository.FindProbaByNameAndRange(name, range);
    }
}