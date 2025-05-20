using Lab10.domain;

namespace Lab10.repository;

public class MatchRepository : FileRepository<int, Match>
{
    public MatchRepository() : base("Matches") { }

    public async Task<IEnumerable<Match>> GetMatchesByDateRangeAsync(DateTime startDate, DateTime endDate)
    {
        var allMatches = await GetAllAsync();
        return allMatches.Where(m => m.Date >= startDate && m.Date <= endDate);
    }
    
    
}