using Lab10.domain;

namespace Lab10.repository;

public class TeamRepository : FileRepository<int, Team>
{
    private readonly IRepository<int, Player> _playerRepository;

    public TeamRepository() : base("Teams")
    {
        _playerRepository = RepositoryFactory.CreateRepository<int, Player>("Players");
    }

    public async Task<IEnumerable<Player>> GetTeamPlayersAsync(int teamId)
    {
        var allPlayers = await _playerRepository.GetAllAsync();
        return allPlayers.Where(p => p.TeamId == teamId);
    }
}