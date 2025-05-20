namespace Lab10.domain;

public abstract class Entity<TId>
{
    public TId Id { get; set; }
}