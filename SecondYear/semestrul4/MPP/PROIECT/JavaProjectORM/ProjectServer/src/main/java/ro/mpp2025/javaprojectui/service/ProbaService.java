package ro.mpp2025.javaprojectui.service;


import ro.mpp2025.javaprojectui.Proba;
import ro.mpp2025.javaprojectui.database.ProbaRepository;

import java.sql.SQLException;
import java.util.Optional;

public class ProbaService extends AbstractService<Integer, Proba>{
    private final ProbaRepository probaRepository;

    public ProbaService(ProbaRepository repository) {
        super(repository);
        this.probaRepository = repository;
    }

    public Proba findProbaByName(String name) {
        return probaRepository.findProbaByName(name);
    }

    public Proba findProbaByNameAndRange(String name, Integer range) {
        return probaRepository.findProbaByNameAndRange(name, range);
    }

    public Optional<Proba> update(Proba p) throws SQLException {
        return probaRepository.update(p);
    }

    public void delete(Integer id) throws SQLException {
        probaRepository.delete(id);
    }
}
