using Model;
using Persistence;
namespace Server;
public class ParticipantService : AbstractService<int, Participant>
{
    public ParticipantService(IRepository<int, Participant> repository) : base(repository)
    {
    }
}