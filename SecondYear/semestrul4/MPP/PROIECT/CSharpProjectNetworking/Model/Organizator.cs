using System.Text.Json.Serialization;

namespace Model;
[Serializable]
public class Organizator : Entity<int>
{
    private string _username;
    private string _password;
    
    public Organizator(){}
    
    [JsonConstructor]
    public Organizator(string username, string password)
    {
        this._username = username;
        this._password = password;
    }

    public override string ToString()
    {
        return
            $"{nameof(_username)}: {_username}, {nameof(_password)}: {_password}, {nameof(Username)}: {Username}, {nameof(Password)}: {Password}";
    }

    public string Username
    {
        get { return _username; }
        set { _username = value; }
    }
        
    public string Password
    {
        get { return _password; }
        set { _password = value; }
    }
        
    public override bool Equals(object obj)
    {
        if (this == obj) return true;
        if (obj == null || GetType() != obj.GetType()) return false;
            
        Organizator that = (Organizator)obj;
        return Equals(_username, that._username) && Equals(_password, that._password);
    }
        
    public override int GetHashCode()
    {
        return HashCode.Combine(_username, _password);
    }
}