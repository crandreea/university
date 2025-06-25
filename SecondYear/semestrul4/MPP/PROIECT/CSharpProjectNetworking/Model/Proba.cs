using System.Text.Json.Serialization;

namespace Model;
[Serializable]
public class Proba : Entity<int>
{
    private string tip;
    private CategorieVarsta varsta;
        
    public Proba(){}
    [JsonConstructor]
    public Proba(string tip, CategorieVarsta varsta)
    {
        this.tip = tip;
        this.varsta = varsta;
    }

    public override string ToString()
    {
        return $"{nameof(tip)}: {tip}, {nameof(varsta)}: {varsta}, {nameof(Tip)}: {Tip}, {nameof(Varsta)}: {Varsta}";
    }

    public string Tip
    {
        get { return tip; }
        set { tip = value; }
    }
        
    public CategorieVarsta Varsta
    {
        get { return varsta; }
        set { varsta = value; }
    }
        
    public override bool Equals(object obj)
    {
        if (this == obj) return true;
        if (obj == null || GetType() != obj.GetType()) return false;
            
        Proba proba = (Proba)obj;
        return Equals(tip, proba.tip) && Equals(varsta, proba.varsta);
    }
        
    public override int GetHashCode()
    {
        return HashCode.Combine(tip, varsta);
    }
}