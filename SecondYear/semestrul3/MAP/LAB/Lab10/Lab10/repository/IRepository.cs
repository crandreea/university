using Lab10.domain;

namespace Lab10.repository;

public interface IRepository<TId, TEntity> where TEntity : Entity<TId>
{
    
    Task<IEnumerable<TEntity>> GetAllAsync();
    Task<TEntity> GetByIdAsync(TId id);
    Task AddAsync(TEntity entity);
    Task UpdateAsync(TEntity entity);
    Task DeleteAsync(TId id);
}