package ro.mpp2025.javaprojectui.service;


import ro.mpp2025.javaprojectui.*;

public class InscriereService extends AbstractService<Tuplu<Participant, Proba>, Inscriere>{
    public InscriereService(Repository<Tuplu<Participant, Proba>, Inscriere> repository) {
        super(repository);
    }
}
