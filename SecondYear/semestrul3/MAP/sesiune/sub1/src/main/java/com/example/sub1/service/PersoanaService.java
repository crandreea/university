package com.example.sub1.service;

import com.example.sub1.domain.Persoana;
import com.example.sub1.repository.Repository;

public class PersoanaService extends AbstractService<Long,Persoana> {
    public PersoanaService(Repository<Long, Persoana> repository) {
        super(repository);
    }
}
