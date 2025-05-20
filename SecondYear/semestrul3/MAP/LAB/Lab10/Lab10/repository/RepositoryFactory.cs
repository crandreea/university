using Lab10.domain;

namespace Lab10.repository;

public static class RepositoryFactory
{
    public static IRepository<TId, TEntity> CreateRepository<TId, TEntity>(string entityName) 
        where TEntity : Entity<TId>
    {
        return new FileRepository<TId, TEntity>(entityName);
    }
}