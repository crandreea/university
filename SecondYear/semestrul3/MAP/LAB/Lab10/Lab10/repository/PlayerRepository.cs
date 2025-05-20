using Lab10.domain;

namespace Lab10.repository;

public class PlayerRepository : FileRepository<int, Player>
{
    public PlayerRepository() : base("Players") { }
    
}