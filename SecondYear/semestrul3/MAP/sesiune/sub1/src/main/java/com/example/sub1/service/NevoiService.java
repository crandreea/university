package com.example.sub1.service;

import com.example.sub1.domain.Nevoi;
import com.example.sub1.domain.Persoana;
import com.example.sub1.repository.Repository;
import com.example.sub1.repository.dbRepo.NevoiRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NevoiService extends AbstractService<Long, Nevoi>{
    public NevoiService(Repository<Long, Nevoi> repository) {
        super(repository);
    }

}
