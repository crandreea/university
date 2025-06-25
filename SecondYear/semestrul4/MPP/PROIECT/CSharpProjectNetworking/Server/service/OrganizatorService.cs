using Model;
using Persistence;
namespace Server;
public class OrganizatorService : AbstractService<int, Organizator>
{
    public OrganizatorService(IRepository<int, Organizator> repository) : base(repository)
    {
    }
}