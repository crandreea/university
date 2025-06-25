
using Model;

namespace Persistence;
public interface IRepository<TId, TE> where TE : Entity<TId>
{
  
    TE? FindOne(TId id);
    
    IEnumerable<TE> FindAll();
    
    TE? Save(TE entity);
    
}