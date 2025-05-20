package com.example.sub1.service;


import com.example.sub1.domain.Nevoi;
import com.example.sub1.domain.validators.NevoiValidator;
import com.example.sub1.domain.validators.PersoanaValidator;
import com.example.sub1.repository.dbRepo.NevoiRepository;
import com.example.sub1.repository.dbRepo.PersoanaRepository;

public class GlobalService {
    private static Methods network = null;

    public static Methods getNetwork() {
        if (network == null) {
            PersoanaRepository persoanaRepo = new PersoanaRepository(new PersoanaValidator());
            NevoiRepository nevoiRepo= new NevoiRepository(new NevoiValidator(persoanaRepo));

            PersoanaService persoanaService = new PersoanaService(persoanaRepo);
            NevoiService nevoiService = new NevoiService(nevoiRepo);

            network = new Methods(persoanaService, nevoiService);

        }

        return network;
    }

}
