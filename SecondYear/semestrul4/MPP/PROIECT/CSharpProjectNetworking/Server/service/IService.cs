using Model;

namespace Server;

public interface IService<ID, E> where E : Entity<ID>
{
    E FindOne(ID id);
        
    IEnumerable<E> FindAll();
        
    void Save(E entity);
}