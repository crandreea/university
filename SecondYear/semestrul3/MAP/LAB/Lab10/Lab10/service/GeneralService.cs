using Lab10.domain;
using Lab10.repository;

namespace Lab10.service;

public class GeneralService
{
    private readonly ActivePlayerRepository _activePlayerRepository;
    private readonly IRepository<int, Player> _playerRepository;
    private readonly MatchRepository _matchRepository;
    private readonly IRepository<int, Team> _teamRepository;
    
    public GeneralService()
    {
        _teamRepository = new TeamRepository();
        _matchRepository = new MatchRepository();
        _activePlayerRepository = new ActivePlayerRepository();
        _playerRepository = new PlayerRepository();
        _playerRepository = RepositoryFactory.CreateRepository<int, Player>("Players");
        _teamRepository = RepositoryFactory.CreateRepository<int, Team>("Teams");
    }
    
    
    public async Task<IEnumerable<Match>> GetMatchesByDateRangeAsync(DateTime startDate, DateTime endDate)
    {
        if (startDate > endDate)
        {
            throw new ArgumentException("Start date must be before end date");
        }

        return await _matchRepository.GetMatchesByDateRangeAsync(startDate, endDate);
    }
    
    public async Task<IEnumerable<Player>> GetPlayersByTeamAsync(int teamId)
    {
        var team = await _teamRepository.GetByIdAsync(teamId);
        if (team == null)
        {
            throw new ArgumentException($"Team with ID {teamId} does not exist.");
        }
        
        var allPlayers = await _playerRepository.GetAllAsync();
        return allPlayers.Where(p => p.TeamId == teamId);
    }
    
    public async Task<(int Team1Score, int Team2Score)> GetMatchScoreAsync(int matchId)
    {
        var match = await _matchRepository.GetByIdAsync(matchId);
        if (match == null)
        {
            throw new ArgumentException($"Match with ID {matchId} does not exist.");
        }

        var team1Score = await _activePlayerRepository.GetMatchScoreForTeamAsync(matchId, match.HomeTeamId);
        var team2Score = await _activePlayerRepository.GetMatchScoreForTeamAsync(matchId, match.AwayTeamId);
        return (team1Score, team2Score);
    }
    
    public async Task<IEnumerable<ActivePlayer>> GetActivePlayersByMatchAsync(int matchId)
    {
        var match = await _matchRepository.GetByIdAsync(matchId);
        if (match == null)
        {
            throw new ArgumentException($"Match with ID {matchId} does not exist.");
        }

        var allActivePlayers = await _activePlayerRepository.GetAllAsync();
        return allActivePlayers.Where(ap => ap.MatchId == matchId);
    }

    public async Task<IEnumerable<ActivePlayer>> GetActivePlayersByMatchAndTeamAsync(int matchId, int teamId)
    {
        var activePlayers = await GetActivePlayersByMatchAsync(matchId);
        var players = await _playerRepository.GetAllAsync();

        return activePlayers.Where(ap => 
            players.Any(p => p.Id == ap.PlayerId && p.TeamId == teamId));
    }
    
    public async Task<IEnumerable<ActivePlayer>> GetAllActivePlayersAsync()
    {
        return await _activePlayerRepository.GetAllAsync();
    }
    
    public async Task<IEnumerable<Player>> GetAllPlayerAsync()
    {
        return await _playerRepository.GetAllAsync();
    }
    
    public async Task<IEnumerable<Match>> GetAllMatchesAsync()
    {
        return await _matchRepository.GetAllAsync();
    }
    
    public async Task<IEnumerable<Team>> GetAllTeamsAsync()
    {
        return await _teamRepository.GetAllAsync();
    }
    

}




