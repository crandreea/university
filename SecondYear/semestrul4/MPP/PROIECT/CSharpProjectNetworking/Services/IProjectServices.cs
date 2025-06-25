using Model;
namespace Services;

public interface IProjectServices
{
    Organizator Login(string username, string password, IProjectObserver client); 
    void Logout(Organizator user, IProjectObserver client); 
    IEnumerable<Organizator> GetAllOrganizatori(); 
    IEnumerable<Proba> GetAllProba(); 
    IList<ProbaDTO> GetAllProbaDto();
    Proba GetProbaByName(string name); 
    Proba GetProbaByNameAndRange(string name, int range); 
    CategorieVarsta GetVarstaByRange(string selectedCategory); 
    IList<ParticipantDTO> SearchParticipants(Proba selectedProba, CategorieVarsta selectedCategory); 
    string RegisterParticipant(string name, string cnp, string event1, string event2, string range); 
    Participant? FindParticipantByCNP(string cnp); 
    void RefreshProbeStatistics(); 
}
