using Lab10.domain;

namespace Lab10.repository;

public class ActivePlayerRepository : FileRepository<int, ActivePlayer>
{
    public ActivePlayerRepository() : base("ActivePlayers") { }

    public async Task<IEnumerable<ActivePlayer>> GetActivePlayersByMatchAndTeamAsync(int matchId, int teamId)
    {
        var allActivePlayers = await GetAllAsync();
        var playerRepo = RepositoryFactory.CreateRepository<int, Player>("Players");
        var players = await playerRepo.GetAllAsync();
            
        return allActivePlayers.Where(ap => 
            ap.MatchId == matchId && 
            players.Any(p => p.Id == ap.PlayerId && p.TeamId == teamId));
    }

    public async Task<int> GetMatchScoreForTeamAsync(int matchId, int teamId)
    {
        var teamActivePlayers = await GetActivePlayersByMatchAndTeamAsync(matchId, teamId);
        return teamActivePlayers.Sum(p => p.PointsScored);
    }
    
    
}