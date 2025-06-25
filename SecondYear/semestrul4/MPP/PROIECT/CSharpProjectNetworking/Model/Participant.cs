using System.Text.Json.Serialization;

namespace Model;
[Serializable]
public class Participant : Entity<int>
{
    private string _nume;
    private string _cnp;
        
    public Participant(){}
    [JsonConstructor]
    public Participant(string nume, string cnp)
    {
        this._nume = nume;
        this._cnp = cnp;
    }

    public override string ToString()
    {
        return $"{nameof(_nume)}: {_nume}, {nameof(_cnp)}: {_cnp}, {nameof(Nume)}: {Nume}, {nameof(Cnp)}: {Cnp}";
    }

    public string Nume
    {
        get { return _nume; }
        set { _nume = value; }
    }
        
    public string Cnp
    {
        get { return _cnp; }
        set { _cnp = value; }
    }
        
    public override bool Equals(object obj)
    {
        if (this == obj) return true;
        if (obj == null || GetType() != obj.GetType()) return false;
            
        Participant that = (Participant)obj;
        return Equals(_nume, that._nume) && Equals(_cnp, that._cnp);
    }
        
    public override int GetHashCode()
    {
        return HashCode.Combine(_nume, _cnp);
    }
}