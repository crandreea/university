namespace Lab10.domain;

public class Player : Student
{
    public int TeamId { get; set; }
    public virtual Team Team { get; set; }
    
}