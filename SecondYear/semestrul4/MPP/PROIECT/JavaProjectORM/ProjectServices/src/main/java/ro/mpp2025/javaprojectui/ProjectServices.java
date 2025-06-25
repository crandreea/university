package ro.mpp2025.javaprojectui;

import ro.mpp2025.javaprojectui.dto.ParticipantDTO;
import ro.mpp2025.javaprojectui.dto.ProbaDTO;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ProjectServices {
    Organizator login(String username, String password, ProjectObserver client) throws Exception;
    void logout(Organizator user, ProjectObserver client) throws ProjectException;

    Iterable<Organizator> getAllOrganizatori() throws ProjectException;
    Iterable<Proba> getAllProba() throws ProjectException;
    List<ProbaDTO> getAllProbaDTO() throws ProjectException;

    Proba getProbaByName(String name) throws ProjectException;
    Proba getProbaByNameAndRange(String name, Integer range) throws ProjectException;
    CategorieVarsta getVarstaByRange(String selectedCategory) throws ProjectException;
    List<ParticipantDTO> searchParticipants(Proba selectedProba, CategorieVarsta selectedCategory) throws ProjectException;
    String registerParticipant(String name, String cnp, String event1, String event2, String range) throws ProjectException, SQLException;
    Optional<Participant> findParticipantByCNP(String cnp) throws ProjectException;

    void refreshProbeStatistics() throws ProjectException;
}
