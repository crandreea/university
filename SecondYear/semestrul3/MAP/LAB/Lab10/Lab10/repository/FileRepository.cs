using System.Text.Json;
using Lab10.domain;

namespace Lab10.repository;

public class FileRepository<TId, TEntity> : IRepository<TId, TEntity> where TEntity : Entity<TId>
{
    private readonly string _filePath;
    private readonly object _lockObject = new object();
    private int _nextId = 1;
    
    public FileRepository(string entityName)
    {
        var directory = Path.Combine(AppDomain.CurrentDomain.BaseDirectory, "Data");
        Directory.CreateDirectory(directory);
        _filePath = Path.Combine(directory, $"{entityName}.json");
        
        if (!File.Exists(_filePath))
        {
            File.WriteAllText(_filePath, "[]");
        }
        
    }
    
    private async Task<List<TEntity>> ReadAllAsync()
    {
        using var streamReader = new StreamReader(_filePath);
        var json = await streamReader.ReadToEndAsync();
        return JsonSerializer.Deserialize<List<TEntity>>(json) ?? new List<TEntity>();
    }

    private async Task WriteAllAsync(List<TEntity> entities)
    {
        var json = JsonSerializer.Serialize(entities, new JsonSerializerOptions 
        { 
            WriteIndented = true 
        });
        await File.WriteAllTextAsync(_filePath, json);
    }
    

    public async Task<IEnumerable<TEntity>> GetAllAsync()
    {
        return await ReadAllAsync();
    }

    public async Task<TEntity> GetByIdAsync(TId id)
    {
        var entities = await ReadAllAsync();
        return entities.FirstOrDefault(e => EqualityComparer<TId>.Default.Equals(e.Id, id));
    }

    public async Task AddAsync(TEntity entity)
    {
        var entities = await ReadAllAsync();
        
        if (entity.Id is int intId && intId == 0)
        {
            _nextId = entities.Count > 0 ? 
                entities.Max(e => (int)(object)e.Id) + 1 : 1;
            entity.Id = (TId)(object)_nextId;
        }

        entities.Add(entity);
        await WriteAllAsync(entities);
    }

    public async Task UpdateAsync(TEntity entity)
    {
        var entities = await ReadAllAsync();
        var index = entities.FindIndex(e => 
            EqualityComparer<TId>.Default.Equals(e.Id, entity.Id));
        
        if (index != -1)
        {
            entities[index] = entity;
            await WriteAllAsync(entities);
        }
    }

    public async Task DeleteAsync(TId id)
    {
        var entities = await ReadAllAsync();
        entities.RemoveAll(e => EqualityComparer<TId>.Default.Equals(e.Id, id));
        await WriteAllAsync(entities);
    }
}