package ro.mpp2025.javaprojectui.service;


import ro.mpp2025.javaprojectui.Participant;
import ro.mpp2025.javaprojectui.Repository;

public class ParticipantService extends AbstractService<Integer, Participant>{
    public ParticipantService(Repository<Integer, Participant> repository) {
        super(repository);
    }
}
