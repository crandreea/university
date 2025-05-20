namespace Lab9.model;

public abstract class Task
{
    private string id;
    private string description;

    public Task(string id, string description)
    {
        id = id;
        description = description;
    }

    public string Id => id;

    public string Description => description;

    public abstract void Execute();

    public override string ToString()
    {
        return $"Task{{id='{id}', description='{description}'}}";
    }

    protected bool Equals(Task other)
    {
        return id == other.id && description == other.description;
    }

    public override bool Equals(object? obj)
    {
        if (obj is null) return false;
        if (ReferenceEquals(this, obj)) return true;
        if (obj.GetType() != GetType()) return false;
        return Equals((Task)obj);
    }

    public override int GetHashCode()
    {
        return HashCode.Combine(id, description);
    }
}