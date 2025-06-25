using System.Collections.ObjectModel;
using System.Collections.Concurrent;
using Services;
using Model;

namespace Server;

public class MainService : IProjectServices
{
    private readonly IService<int, Organizator> organizatorService;
    private readonly IService<int, Participant> participantService;
    private readonly IService<int, Proba> probaService;
    private readonly IService<Tuple<Participant, Proba>, Inscriere> inscriereService;
    private readonly IService<int, CategorieVarsta> categorieVarstaService;

    private readonly ObservableCollection<ProbaDTO> probeStatisticsData;

    private Organizator currentOrganizator;

    private readonly IDictionary<string, IProjectObserver> loggedClients;
    // private readonly ConcurrentDictionary<string, IProjectObserver> _loggedInClients;
    private readonly object _lockObject = new object();
    public MainService(
        IService<int, Organizator> organizatorService,
        IService<int, Participant> participantService,
        IService<int, Proba> probaService,
        IService<Tuple<Participant, Proba>, Inscriere> inscriereService,
        IService<int, CategorieVarsta> categorieVarstaService)
    {
        this.organizatorService = organizatorService;
        this.participantService = participantService;
        this.probaService = probaService;
        this.inscriereService = inscriereService;
        this.categorieVarstaService = categorieVarstaService;
        
        probeStatisticsData = new ObservableCollection<ProbaDTO>();
        this.loggedClients = new Dictionary<string, IProjectObserver>();
        // _loggedInClients = new ConcurrentDictionary<string, IProjectObserver>();
        Console.WriteLine("MainService initialized."); 
        RefreshProbeStatistics(); 
    }

    public Organizator Login(string username, string password, IProjectObserver client)
    {
        lock (this)
        {
            Console.WriteLine($"Users now logged: {loggedClients.Count}");
            Organizator? org = Autentificator.Login(username, password);
            Console.WriteLine($"Authenticating user: {org.Username}, password: {org.Password}");

            if (org != null)
            {
                
                if (loggedClients.ContainsKey(username))
                {
                    Console.WriteLine($"User {username} is already logged in.");
                    throw new ProjectException("User already logged in.");
                }

                if (org.Password == password)
                {
                    loggedClients.Add(username, client);
                    Console.WriteLine($"User {username} logged in successfully. Observer added."); 
                    
                    return org;
                }
                else
                {
                    Console.WriteLine($"Failed to add user {username} to logged in clients (concurrent issue?)."); 
                    throw new ProjectException("Login failed due to an internal error.");
                }
            }
            else
            {
                Console.WriteLine($"User not found or authentication failed for: {username}"); 
                throw new ProjectException("Authentication failed! User not found or incorrect password.");
            }
        }
        
        
    }
    public void Logout(Organizator user, IProjectObserver client)
    {
        lock (this)
        {
            IProjectObserver localClient = loggedClients[user.Username];
            Console.WriteLine($"Attempting logout for user: {user.Username}"); 
            if (localClient == null)
            {
                Console.WriteLine($"User {user.Username} is not logged in."); // Logging substitut
            }
            
            loggedClients.Remove(user.Username);
        }
    }

    private void NotifyClients(Inscriere inscriere)
    {
        Console.WriteLine($"Preparing to notify clients about inscriere: {inscriere?.IDParticipant?.Nume} -> {inscriere?.IDProba?.Tip}"); // Logging substitut
        
        foreach (IProjectObserver client in loggedClients.Values)
        {
            
            Task.Run(() =>
            {
                try
                {
                    client.InscriereAdded(inscriere); 
                }
                catch (Exception ex)
                {
                    Console.WriteLine($"Error during notification for client {client.GetType().Name}: {ex.Message}"); // Logging substitut
                }
            });
        }
        Console.WriteLine($"Submitted notification tasks for {inscriere?.IDParticipant?.Nume} to {loggedClients.Count} clients."); // Logging substitut
    }

    public IEnumerable<Organizator> GetAllOrganizatori()
    {
        lock (_lockObject)
        {
            try
            {
                return organizatorService.FindAll();
            }
            catch (Exception e)
            {
                Console.WriteLine($"Failed to find all organizatori: {e.Message}"); // Logging substitut
                throw new ProjectException("Failed to find all organizatori.", e);
            }
        }
        
    }

    public IEnumerable<Proba> GetAllProba()
    {
        lock (_lockObject)
        {
            try
            {
                return probaService.FindAll();
            }
            catch (Exception e)
            {
                Console.WriteLine($"Failed to find all probe: {e.Message}"); // Logging substitut
                throw new ProjectException("Failed to find all probe.", e);
            }
        }
        
    }

    public IList<ProbaDTO> GetAllProbaDto()
    {
        lock (_lockObject)
        {
            return new List<ProbaDTO>(probeStatisticsData);
        }
    }


    public void RefreshProbeStatistics()
    {
            Console.WriteLine("Refreshing probe statistics...");
            try
            {
                var newData = new List<ProbaDTO>();
                IEnumerable<Proba> probeList = GetAllProba();

                foreach (Proba proba in probeList)
                {
                    int count = CountParticipantsForProba(proba);
                    newData.Add(new ProbaDTO(proba, count));
                }

                probeStatisticsData.Clear();
                foreach (ProbaDTO item in newData)
                {
                    probeStatisticsData.Add(item);
                }

                Console.WriteLine($"Probe statistics refreshed. {probeStatisticsData.Count} items.");
            }
            catch (Exception e)
            {
                Console.WriteLine($"Failed to refresh probe statistics: {e.Message}\n{e.StackTrace}");
            }
        
    }


    private int CountParticipantsForProba(Proba proba)
    {
        if (proba == null) 
            return 0;

        var inscrieri = inscriereService.FindAll() ?? new List<Inscriere>(); 
        int count = 0;

        foreach (var inscriere in inscrieri)
        {
            if (inscriere?.IDProba?.Id != null && inscriere.IDProba.Id.Equals(proba.Id))
            {
                count++;
            }
        }

        return count;
    }


    public Proba GetProbaByName(string name)
    {
        lock (_lockObject)
        {
            try
            {
                if (probaService is ProbaService)
                {
                    return ((ProbaService) probaService).FindProbaByName(name); 
                }
                Console.WriteLine($"ProbaService instance not available for GetProbaByName. Service type: {probaService.GetType().Name}"); // Logging
                return null; 
            }
            catch (Exception e)
            {
                Console.WriteLine($"Failed to find proba by name '{name}': {e.Message}"); // Logging substitut
                throw new ProjectException($"Failed to find proba by name: {name}", e);
            }
        }
        
    }

    public Proba GetProbaByNameAndRange(string name, int range)
    {
        lock (_lockObject)
        {
            try
            {
                if (probaService is ProbaService ps)
                {
                    return ps.FindProbaByNameAndRange(name, range); 
                }
                Console.WriteLine($"ProbaService instance not available for GetProbaByNameAndRange. Service type: {probaService.GetType().Name}"); // Logging
                return null; 
            }
            catch (Exception e)
            {
                Console.WriteLine($"Failed to find proba by name '{name}' and rangeId '{range}': {e.Message}"); // Logging substitut
                throw new ProjectException($"Failed to find proba by name: {name} and rangeId: {range}", e);
            } 
        }
        
    }

    public CategorieVarsta GetVarstaByRange(string selectedCategory)
    {
        lock (_lockObject)
        {
            try
            {
                if (categorieVarstaService is CategorieVarstaService cvs)
                {
                    return cvs.FindVarstaByRange(selectedCategory);
                }
                Console.WriteLine($"CategorieVarstaService instance not available. Service type: {categorieVarstaService.GetType().Name}"); // Logging
                return null;
            }
            catch (Exception e)
            {
                Console.WriteLine($"Failed to find varsta by category '{selectedCategory}': {e.Message}"); // Logging substitut
                throw new ProjectException($"Failed to find varsta by category: {selectedCategory}", e);
            } 
        }
        
    }

    public IList<ParticipantDTO> SearchParticipants(Proba selectedProba, CategorieVarsta selectedCategory)
    {
        lock (_lockObject)
        {
            try
            {
                var results = new List<ParticipantDTO>();
                var inscrieri = inscriereService.FindAll(); // Poate arunca excepție

                foreach (var inscriere in inscrieri)
                {
                    Proba? proba = inscriere.IDProba;
                    Participant? participant = inscriere.IDParticipant;

                    if (proba != null && participant != null &&
                        proba.Tip != null && selectedProba.Tip != null && proba.Varsta != null && // Null checks
                        proba.Tip.Equals(selectedProba.Tip) && proba.Varsta.Id.Equals(selectedCategory.Id))
                    {
                        if (participant.Cnp != null)
                        {
                            int age = CalculateAgeFromCNP(participant.Cnp);
                            results.Add(new ParticipantDTO(participant.Nume ?? "N/A", age)); // Folosim ?? pentru Nume null
                        }
                    }
                }
                return results;
            }
            catch (Exception e)
            {
                Console.WriteLine($"Failed to search participants for category '{selectedCategory.Id}': {e.Message}"); // Logging substitut
                throw new ProjectException($"Failed to search participants for category: {selectedCategory.Id}", e);
            }
        }
        
    }

    private int CalculateAgeFromCNP(string cnp)
    {
        // CNP format: SAALLZZJJNNNC
        int year;
        int firstDigit = int.Parse(cnp[0].ToString());
        int yearDigits = int.Parse(cnp.Substring(1, 2));

        if (firstDigit == 1 || firstDigit == 2)
        {
            year = 1900 + yearDigits;
        }
        else if (firstDigit == 5 || firstDigit == 6)
        {
            year = 2000 + yearDigits;
        }
        else
        {
            year = 1900 + yearDigits;
        }

        int currentYear = DateTime.Now.Year;
        return currentYear - year;
    }

    public string RegisterParticipant(string name, string cnp, string event1, string event2, string range)
    {
        if (string.IsNullOrWhiteSpace(name))
        {
            return "Name must not be null or empty";
        }

        if (string.IsNullOrWhiteSpace(cnp) || cnp.Length != 13)
        {
            return "CNP must have 13 characters";
        }

        if (event1.Equals(event2))
        {
            return "You need to select different events!";
        }

        int age = CalculateAgeFromCNP(cnp);

        CategorieVarsta varsta1 = GetVarstaByRange(range);
        Proba proba1 = GetProbaByNameAndRange(event1, varsta1.Id);

        if (IsAgeInCategory(age, varsta1))
        {
            return "Not in the age range!";
        }

        Proba proba2 = GetProbaByNameAndRange(event2, varsta1.Id);

        lock (_lockObject)
        {
            Participant participant = FindParticipantByCNP(cnp);
            if (participant != null)
            {
                int existingRegistrations = CountRegistrationsForParticipant(participant);
                if (existingRegistrations >= 2 || (existingRegistrations == 1 && event2 != " "))
                {
                    return "Already registered at two events!";
                }
            }
            else
            {
                participant = new Participant(name, cnp);
                participantService.Save(participant);
            }

            var inscriere1 = new Inscriere(participant, proba1);
            inscriereService.Save(inscriere1);
            RefreshProbeStatistics();
            NotifyClients(inscriere1);

            if (event2 != " ")
            {
                var inscriere2 = new Inscriere(participant, proba2);
                inscriereService.Save(inscriere2);
                RefreshProbeStatistics();
                NotifyClients(inscriere2);
                
            }

            
        }
        
        return "Registration successful!";
    }

    public Participant FindParticipantByCNP(string cnp)
    {
        lock (_lockObject)
        {
            var participants = participantService.FindAll();
            foreach (var p in participants)
            {
                if (p.Cnp.Equals(cnp))
                {
                    return p;
                }
            }
            return null;
        }
        
    }

    private int CountRegistrationsForParticipant(Participant participant)
    {
        lock (_lockObject)
        {
            var inscrieri = inscriereService.FindAll();
            int count = 0;
            foreach (var inscriere in inscrieri)
            {
                if (inscriere.IDParticipant.Id.Equals(participant.Id))
                {
                    count++;
                }
            }
            return count;
        }
        
    }

    private bool IsAgeInCategory(int age, CategorieVarsta category)
    {
        return age < category.VarstaMin || age > category.VarstaMax;
    }

}

