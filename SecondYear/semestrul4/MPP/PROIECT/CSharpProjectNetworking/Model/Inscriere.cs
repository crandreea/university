using System.Text.Json.Serialization;

namespace Model;
[Serializable]
public class Inscriere : Entity<System.Tuple<Participant, Proba>>
{
    private Participant _idParticipant;
    private Proba _idProba;
        
    public Inscriere(){}
    [JsonConstructor]
    public Inscriere(Participant idParticipant, Proba idProba)
    {
        this._idParticipant = idParticipant;
        this._idProba = idProba;
        this.Id = new Tuple<Participant, Proba>(idParticipant, idProba);
    }

    public override string ToString()
    {
        return
            $"{nameof(_idParticipant)}: {_idParticipant}, {nameof(_idProba)}: {_idProba}, {nameof(IDParticipant)}: {IDParticipant}, {nameof(IDProba)}: {IDProba}";
    }

    public Participant IDParticipant
    {
        get { return _idParticipant; }
        set { _idParticipant = value; }
    }
        
    public Proba IDProba
    {
        get { return _idProba; }
        set { _idProba = value; }
    }
        
    public override bool Equals(object obj)
    {
        if (this == obj) return true;
        if (obj == null || GetType() != obj.GetType()) return false;
            
        Inscriere inscriere = (Inscriere)obj;
        return _idParticipant == inscriere._idParticipant && _idProba == inscriere._idProba;
    }
        
    public override int GetHashCode()
    {
        return HashCode.Combine(_idParticipant, _idProba);
    }
}