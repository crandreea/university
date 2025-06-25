package ro.mpp2025.javaprojectui.service;


import ro.mpp2025.javaprojectui.Organizator;
import ro.mpp2025.javaprojectui.Repository;

public class OrganizatorService extends AbstractService<Integer, Organizator>{
    public OrganizatorService(Repository<Integer, Organizator> repository) {
        super(repository);
    }
}
