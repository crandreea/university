using System.Text.Json.Serialization;

namespace Model;

[Serializable]
public class CategorieVarsta : Entity<int>
{
    private int varstaMin;
    private int varstaMax;
        
    public CategorieVarsta(){}
    
    [JsonConstructor]
    public CategorieVarsta(int varstaMin, int varstaMax)
    {
        this.varstaMin = varstaMin;
        this.varstaMax = varstaMax;
    }
        
    public int VarstaMin
    {
        get { return varstaMin; }
        set { varstaMin = value; }
    }

    public override string ToString()
    {
        return
            $"{nameof(varstaMin)}: {varstaMin}, {nameof(varstaMax)}: {varstaMax}, {nameof(VarstaMin)}: {VarstaMin}, {nameof(VarstaMax)}: {VarstaMax}";
    }

    public int VarstaMax
    {
        get { return varstaMax; }
        set { varstaMax = value; }
    }
        
    public override bool Equals(object obj)
    {
        if (this == obj) return true;
        if (obj == null || GetType() != obj.GetType()) return false;
            
        CategorieVarsta that = (CategorieVarsta)obj;
        return varstaMin == that.varstaMin && varstaMax == that.varstaMax;
    }
        
    public override int GetHashCode()
    {
        return HashCode.Combine(varstaMin, varstaMax);
    }
}