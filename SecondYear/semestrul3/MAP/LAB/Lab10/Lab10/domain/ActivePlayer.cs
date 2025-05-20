namespace Lab10.domain;

public class ActivePlayer : Entity<int>
{
    public int PlayerId { get; set; }
    public int MatchId { get; set; }
    public int PointsScored { get; set; }
    public PlayerStatus Status{ get; set; }
    public virtual Player Player { get; set; }
    public virtual Match Match { get; set; }
    
}