using Model;

namespace Server;
public static class Autentificator
{
    public static Organizator Login(string username, string password)
    {
        MainService network = GlobalService.GetNetwork();
            
        if (username == null)
        {
            throw new Exception("Numele de utilizator nu poate fi null");
        }
            
        if (password == null)
        {
            throw new Exception("Parola nu poate fi null");
        }

        Console.WriteLine("Încercare de autentificare cu: " + username);

        foreach (var u in network.GetAllOrganizatori())
        {
            Console.WriteLine("Utilizator: " + u.Username + ", Parola: " + u.Password);
        }

        Organizator user = network.GetAllOrganizatori()
            .Where(u => string.Equals(u.Username, username) && string.Equals(u.Password, password))
            .FirstOrDefault();

        return user;
    }
}