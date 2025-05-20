namespace Lab10.domain;

public class Match : Entity<int>
{
    public int HomeTeamId { get; set; }
    public int AwayTeamId { get; set; }
    public DateTime Date { get; set; }
    public virtual Team HomeTeam { get; set; }
    public virtual Team AwayTeam { get; set; }
    
}