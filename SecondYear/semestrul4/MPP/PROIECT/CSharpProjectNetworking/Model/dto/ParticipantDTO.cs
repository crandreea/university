namespace Model;
public class ParticipantDTO
{
    
    public string Nume { get; set; }
    public int Varsta { get; set; }

    public ParticipantDTO() {}
    public ParticipantDTO(string nume, int varsta)
    {
        Nume = nume;
        Varsta = varsta;
    }

    public string GetNume()
    {
        return Nume;
    }

    public int GetVarsta()
    {
        return Varsta;
    }
}