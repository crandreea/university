package ro.mpp2025.javaprojectui.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ro.mpp2025.javaprojectui.*;
import ro.mpp2025.javaprojectui.dto.ParticipantDTO;
import ro.mpp2025.javaprojectui.dto.ProbaDTO;
import ro.mpp2025.javaprojectui.Participant;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainService implements ProjectServices {

    private final Service<Integer, Organizator> organizatorService;
    private final Service<Integer, Participant> participantService;
    private final Service<Integer, Proba> probaService;
    private final Service<Tuplu<Participant, Proba>, Inscriere> inscriereService;
    private final Service<Integer, CategorieVarsta> categorieVarstaService;

    private final ObservableList<ProbaDTO> probeStatisticsData = FXCollections.observableArrayList();

    private final Map<String, ProjectObserver> loggedInClients;
    private  ExecutorService notificationExecutor;

    private Organizator currentOrganizator;

    private static final Logger logger = LogManager.getLogger(MainService.class);

    public MainService(Service<Integer, Organizator> organizatorService, Service<Integer, Participant> participantService, Service<Integer, Proba> probaService, Service<Tuplu<Participant, Proba>, Inscriere> inscriereService, Service<Integer, CategorieVarsta> categorieVarstaService) {
        this.organizatorService = organizatorService;
        this.participantService = participantService;
        this.probaService = probaService;
        this.inscriereService = inscriereService;
        this.categorieVarstaService = categorieVarstaService;

        this.loggedInClients = new ConcurrentHashMap<>();
//        this.notificationExecutor = Executors.newFixedThreadPool(5);
    }

    @Override
    public synchronized Organizator login(String username, String password, ProjectObserver client) throws Exception {
        logger.info("Attempting login for user: {}", username);

        Optional<Organizator> orgOpt = Optional.ofNullable(Authenticator.login(username, password));
        if (orgOpt.isPresent()) {
            Organizator org = orgOpt.get();
            if (org.getPassword().equals(password)) {
                if (loggedInClients.containsKey(username)) {
                    logger.warn("User {} is already logged in.", username);
                    throw new ProjectException("User already logged in.");
                }
                loggedInClients.put(username, client);

                logger.info("User {} logged in successfully. Observer added.", username);
                return org;
            } else {
                logger.warn("Incorrect password for user: {}", username);
                throw new ProjectException("Authentication failed! Incorrect password.");
            }
        } else {
            logger.warn("User not found: {}", username);
            throw new ProjectException("Authentication failed! User not found.");
        }
    }

    @Override
    public synchronized void logout(Organizator user, ProjectObserver client) throws ProjectException {
        logger.info("Attempting logout for user: {}", user.getUsername());
        ProjectObserver removedClient = loggedInClients.remove(user.getUsername());
        if (removedClient == null) {
            logger.warn("User {} was not logged in or already logged out.", user.getUsername());
            throw new ProjectException("User " + user.getUsername() + " is not logged in.");
        }
        logger.info("User {} logged out successfully. Observer removed.", user.getUsername());
    }


    private void notifyClients(Inscriere inscriere) {
//        //////
        notificationExecutor = Executors.newFixedThreadPool(5);
        for (ProjectObserver client : loggedInClients.values()) {
            notificationExecutor.execute(() -> {
                try {
                    client.inscriereAdded(inscriere);
                    logger.info("Successfully notified client (observer) about update: {}", client.getClass().getName());
                } catch (Exception e) {
                    logger.error("Unexpected error during notification for client (observer) {}: {}", client.getClass().getName(), e.getMessage(), e);
                }
            });
        }
        notificationExecutor.shutdown();

    }

    @Override
    public Iterable<Organizator> getAllOrganizatori() throws ProjectException {
        try{
            logger.debug("Getting all organizatori...");
            var x =  organizatorService.findAll();
            logger.debug("Organizator found: {}", x);
            return x;
        }catch (Exception e){
            throw new ProjectException("Failed to find all organizatori." + e);
        }

    }

    @Override
    public Iterable<Proba> getAllProba() throws ProjectException {
        try{
            return probaService.findAll();
        }
        catch (Exception e){
            throw new ProjectException("Failed to find all probe." + e.getMessage());
        }
    }

    public Optional<Proba> getProbaById(Integer id) throws SQLException {
        logger.info("Getting proba by id: {}", id);
        return probaService.findOne(id);
    }

    public int createProba(Proba proba) throws SQLException {
        logger.info("Creating new proba: {}", proba);
        probaService.save(proba);
        return proba.getId();
    }

    public Optional<Proba> updateProba(Proba proba) throws SQLException {
        logger.info("Updating proba: {}", proba);
        return probaService.update(proba);
    }

    public void deleteProba(Integer probaId) throws SQLException {
        logger.info("Deleting proba: {}", probaId);
        probaService.delete(probaId);
    }

    @Override
    public synchronized List<ProbaDTO> getAllProbaDTO() throws ProjectException {
        try{
            if (probeStatisticsData == null) {
                return new ArrayList<>();
            }
            return new ArrayList<>(probeStatisticsData);
        }catch (Exception e){
            throw new ProjectException("Failed to find all probedto:" + e.getMessage());
        }

    }

    public synchronized void refreshProbeStatistics() throws ProjectException {
        try{

            List<ProbaDTO> newData = new ArrayList<>();
            Iterable<Proba> probe = getAllProba();

            for (Proba proba : probe) {
                int count = countParticipantsForProba(proba);
                newData.add(new ProbaDTO(proba, count));
            }

            probeStatisticsData.setAll(newData);
        }catch (Exception e){
            throw new ProjectException("Failed to refreshData:" + e.getMessage());
        }

    }

    public int countParticipantsForProba(Proba proba) {
        Iterable<Inscriere> inscrieri = inscriereService.findAll();
        int count = 0;
        for (Inscriere inscriere : inscrieri) {
            if (inscriere.getIDproba().getId().equals(proba.getId())) {
                count++;
            }
        }
        return count;
    }

    @Override
    public Proba getProbaByName(String name) throws ProjectException {
        try{
            if (probaService instanceof ProbaService) {
                return ((ProbaService) probaService).findProbaByName(name);
            }
            return null;
        }catch (Exception e){
            throw new ProjectException("Failed to find probaby by name: " + name);
        }

    }

    @Override
    public Proba getProbaByNameAndRange(String name, Integer range) throws ProjectException {
        try {
            if (probaService instanceof ProbaService) {
                return ((ProbaService) probaService).findProbaByNameAndRange(name, range);
            }
            return null;
        }catch (Exception e){
            throw new ProjectException("Failed to find probaby by name: " + name + " range: " + range);
        }

    }


    @Override
    public CategorieVarsta getVarstaByRange(String selectedCategory) throws ProjectException {
        try{
            if (categorieVarstaService instanceof CategorieVarstaService) {
                return ((CategorieVarstaService) categorieVarstaService).findVarstaByRange(selectedCategory);
            }
            return null;
        }catch (Exception e){
            throw new ProjectException("Failed to find varsta by selectedCategory: " + selectedCategory);
        }

    }


    @Override
    public List<ParticipantDTO> searchParticipants(Proba selectedProba, CategorieVarsta selectedCategory) throws ProjectException {
        try{
            List<ParticipantDTO> results = new ArrayList<>();
            Iterable<Inscriere> inscrieri = inscriereService.findAll();

            for (Inscriere inscriere : inscrieri) {
                Proba proba = inscriere.getIDproba();
                Participant participant = inscriere.getIDparticipant();

                if(proba.getTip().equals(selectedProba.getTip()) && proba.getVarsta().getId().equals(selectedCategory.getId())){
                    int age = calculateAgeFromCNP(participant.getCnp());
                    results.add(new ParticipantDTO(participant.getNume(), age));
                }
            }

            return results;
        }catch (Exception e){
            throw new ProjectException("Failed to find participants by selectedCategory: " + selectedCategory);
        }

    }

    private int calculateAgeFromCNP(String cnp) {

        int year;
        int firstDigit = Character.getNumericValue(cnp.charAt(0));
        int yearDigits = Integer.parseInt(cnp.substring(1, 3));

        if (firstDigit == 1 || firstDigit == 2) {
            year = 1900 + yearDigits;
        } else if (firstDigit == 5 || firstDigit == 6) {
            year = 2000 + yearDigits;
        } else {
            year = 1900 + yearDigits;
        }

        int currentYear = LocalDate.now().getYear();
        return currentYear - year;
    }

    @Override
    public synchronized String registerParticipant(String name, String cnp, String event1, String event2, String range) throws ProjectException, SQLException {

        if (name == null || name.trim().isEmpty()) {
            return "Name must not be null or empty";
        }

        if (cnp == null || cnp.trim().isEmpty() || cnp.length() != 13) {
            return "CNP must have 13 characters";
        }

        if (event1 == null) {
            return "You need to select at least one event!";
        }

        if(event1.equals(event2)){
            return "You need to select different events!";
        }

        int age = calculateAgeFromCNP(cnp);


        CategorieVarsta varsta1 = getVarstaByRange(range);
        Proba proba1 = getProbaByNameAndRange(event1, varsta1.getId());

        System.out.println(proba1);
        System.out.println(varsta1);

        if (isAgeInCategory(age, varsta1)) {
            return "Not in the age range!";
        }

        Proba proba2 = getProbaByNameAndRange(event2, varsta1.getId());

        System.out.println(proba2);


        Optional<Participant> existingParticipant = findParticipantByCNP(cnp);
        Participant participant;

        if (existingParticipant.isPresent()) {
            participant = existingParticipant.get();
            int existingRegistrations = countRegistrationsForParticipant(participant);
            if (existingRegistrations >= 2 || (existingRegistrations == 1 && event2 != null)) {
                return "Already registered at two events!";
            }
        } else {
            participant = new Participant(name, cnp);
            participantService.save(participant);

        }

        synchronized (this) {


            Inscriere inscriere1 = new Inscriere(participant, proba1);
            inscriereService.save(inscriere1);

            refreshProbeStatistics();

            logger.info("Notifying {} clients about the update for Proba ID {}", loggedInClients.size(), proba1.getId());
            notifyClients(inscriere1);

            if (event2 != null) {
                Inscriere inscriere2 = new Inscriere(participant, proba2);
                inscriereService.save(inscriere2);

                refreshProbeStatistics();

                logger.info("Notifying {} clients about the update for Proba ID {}", loggedInClients.size(), proba2.getId());
                notifyClients(inscriere2);
            }
        }


        return "Registration successful!";
    }

    @Override
    public Optional<Participant> findParticipantByCNP(String cnp) throws ProjectException{
        try{
            Iterable<Participant> participants = participantService.findAll();
            for (Participant p : participants) {
                if (p.getCnp().equals(cnp)) {
                    return Optional.of(p);
                }
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new ProjectException(e.getMessage());
        }

    }

    private synchronized int countRegistrationsForParticipant(Participant participant) {
        Iterable<Inscriere> inscrieri = inscriereService.findAll();
        int count = 0;
        for (Inscriere inscriere : inscrieri) {
            if (inscriere.getIDparticipant().getId().equals(participant.getId())) {
                count++;
            }
        }
        return count;
    }

    private boolean isAgeInCategory(int age, CategorieVarsta category) {
        return age < category.getVarstaMin() || age > category.getVarstaMax();
    }


}
