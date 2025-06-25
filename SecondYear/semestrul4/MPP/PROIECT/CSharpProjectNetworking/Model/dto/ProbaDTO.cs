namespace Model;
public class ProbaDTO
{
    public Proba Proba { get; set; }
    public int NumarInregistrati { get; set; }

    public ProbaDTO() {}
    public ProbaDTO(Proba proba, int numarInregistrati)
    {
        Proba = proba;
        NumarInregistrati = numarInregistrati;
    }
    

    public Proba GetProba()
    {
        return Proba;
    }

    public string GetNumeEveniment()
    {
        return Proba.Tip;
    }

    public string GetGrupaVarsta()
    {
        CategorieVarsta cv = Proba.Varsta;
        return cv.VarstaMin + "-" + cv.VarstaMax + " ani";
    }

    public int GetNumarInregistrati()
    {
        return NumarInregistrati;
    }
    
    public override string ToString()
    {
        return $"Proba: {Proba}, NumarInregistrati: {NumarInregistrati}";
    }
}