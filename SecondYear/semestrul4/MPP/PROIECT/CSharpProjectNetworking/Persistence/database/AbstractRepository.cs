using System.Data;
using log4net;
using Model;
namespace Persistence.database;
public abstract class AbstractRepository<ID, E> where E : Entity<ID>
{
    protected readonly IDbConnection connection;
    private static readonly ILog logger = LogManager.GetLogger(typeof(AbstractRepository<ID, E>));

    protected AbstractRepository()
    {
        connection = JdbcUtils.GetInstance().GetConnection();
    }

    protected abstract IDbCommand FindOneQuery(ID id);
    protected abstract IDbCommand FindAllQuery();
    protected abstract IDbCommand SaveQuery(E entity);
    protected abstract E BuildEntity(IDataReader reader);

    public E? FindOne(ID id)
    {
        logger.InfoFormat("Loading entity with ID: {0}", id);
        if (id == null)
            throw new ArgumentException("ID-ul nu poate fi null!");

        using var command = FindOneQuery(id);
        using var reader = command.ExecuteReader();
        return reader.Read() ? BuildEntity(reader) : null;
    }

    public IEnumerable<E> FindAll()
    {
        logger.InfoFormat("Loading entities...");
        List<E> entities = new();
        using var command = FindAllQuery();
        using var reader = command.ExecuteReader();
        while (reader.Read())
        {
            entities.Add(BuildEntity(reader));
        }
        return entities;
    }

    public E Save(E entity)
    {
        if (entity == null)
            throw new ArgumentException("Entity-ul nu poate fi null!");

        logger.InfoFormat("Saving entity: {}", entity);
        using var command = SaveQuery(entity);
        command.ExecuteNonQuery();
        return entity;
    }
}