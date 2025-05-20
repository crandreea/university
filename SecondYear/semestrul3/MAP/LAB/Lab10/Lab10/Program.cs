using Lab10.domain;
using Lab10.service;

namespace Lab10;
class Program
{
    private static readonly GeneralService _service = new GeneralService();
    
    static async Task Main(string[] args)
    {
        while (true)
        {
            Console.Clear();
            Console.WriteLine("Basketball Management System");
            Console.WriteLine("1. Team Players");
            Console.WriteLine("2. Active Players by Team and Match");
            Console.WriteLine("3. Matches by period");
            Console.WriteLine("4. Match Score");
            Console.WriteLine("0. Exit");
            Console.Write("Select an option: ");

            var choice = Console.ReadLine();

            switch (choice)
            {
                case "1":
                    Console.Write("Enter team ID: ");
                    if (int.TryParse(Console.ReadLine(), out int playersTeamId))
                    {
                        var players = await _service.GetPlayersByTeamAsync(playersTeamId);
                        foreach (var player in players)
                        {
                            Console.WriteLine($"ID: {player.Id}, Name: {player.Name}");
                        }
                    }
                    break;
                case "2":
                    Console.Write("Enter match ID: ");
                    int matchTeamId = int.Parse(Console.ReadLine());
                    Console.Write("Enter team ID: ");
                    int teamId = int.Parse(Console.ReadLine());
                        
                    var matchTeamPlayers = await _service.GetActivePlayersByMatchAndTeamAsync(matchTeamId, teamId);
                    foreach (var ap in matchTeamPlayers)
                    {
                        Console.WriteLine($"ID: {ap.Id}, Player ID: {ap.PlayerId}, Points: {ap.PointsScored}, Type: {ap.Status}");
                    }
                    break;
                
                case "3":
                    Console.Write("Enter start date (yyyy-MM-dd): ");
                    if (DateTime.TryParse(Console.ReadLine(), out DateTime startDate))
                    {
                        Console.Write("Enter end date (yyyy-MM-dd): ");
                        if (DateTime.TryParse(Console.ReadLine(), out DateTime endDate))
                        {
                            var matchesByDate = await _service.GetMatchesByDateRangeAsync(startDate, endDate);
                            foreach (var match in matchesByDate)
                            {
                                Console.WriteLine($"ID: {match.Id}, Date: {match.Date}, Teams: {match.HomeTeamId} vs {match.AwayTeamId}");
                            }
                        }
                    }
                    break;
                case "4":
                    Console.Write("Enter match ID: ");
                    if (int.TryParse(Console.ReadLine(), out int scoreMatchId))
                    {
                        var (team1Score, team2Score) = await _service.GetMatchScoreAsync(scoreMatchId);
                        Console.WriteLine($"Team 1 Score: {team1Score}");
                        Console.WriteLine($"Team 2 Score: {team2Score}");
                    }
                    break;
                case "5":
                    var teams = await _service.GetAllTeamsAsync();
                    foreach (var team in teams)
                    {
                        Console.WriteLine($"ID: {team.Id}, Name: {team.Name}");
                    }
                    
                    var matches = await _service.GetAllMatchesAsync();
                    foreach (var match in matches)
                    {
                        Console.WriteLine($"ID: {match.Id}, Date: {match.Date}, Teams: {match.HomeTeamId} vs {match.AwayTeamId}");
                    }
                    
                    var playerss = await _service.GetAllPlayerAsync();
                    foreach (var player in playerss)
                    {
                        Console.WriteLine($"ID: {player.Id}, Name: {player.Name}, Team ID: {player.TeamId}, School: {player.School}");
                    }
                    
                    var activePlayers = await _service.GetAllActivePlayersAsync();
                    foreach (var ap in activePlayers)
                    {
                        Console.WriteLine($"ID: {ap.Id}, Player ID: {ap.PlayerId}, Match ID: {ap.MatchId}, Points: {ap.PointsScored}, Type: {ap.Status}");
                    }
                    break;
                case "0":
                    return;
                default:
                    Console.WriteLine("Invalid option. Press any key to continue...");
                    Console.ReadKey();
                    break;
            }
        }
    }
    
}