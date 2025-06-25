using Model;
using Persistence;

namespace Server;
public class AbstractService<ID, E> : IService<ID, E> where E : Entity<ID>
{
    protected IRepository<ID, E> repository;

    public AbstractService(IRepository<ID, E> repository)
    {
        this.repository = repository;
    }

    public virtual E FindOne(ID id)
    {
        return repository.FindOne(id);
    }

    public virtual IEnumerable<E> FindAll()
    {
        return repository.FindAll();
    }

    public virtual void Save(E entity)
    {
        repository.Save(entity);
    }
}