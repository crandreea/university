package com.example.sub1.service;

import com.example.sub1.domain.Nevoi;
import com.example.sub1.domain.Persoana;
import com.example.sub1.utils.observer.Observable;
import com.example.sub1.utils.observer.Observer;

import java.util.ArrayList;

public class Methods implements Observable {
    private final Service<Long, Persoana> persoanaService;
    private final Service<Long, Nevoi> nevoiService;
    private ArrayList<Observer> observers = new ArrayList<>();

    public Methods(Service<Long, Persoana> persoanaService, Service<Long, Nevoi> nevoiService) {
        this.persoanaService = persoanaService;
        this.nevoiService = nevoiService;
    }
    @Override
    public void addObserver(Observer e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(Object arg) {
        for (Observer observer : observers) {
            observer.update(arg);
        }
    }

    public Iterable<Nevoi> getNevoi() {
        return nevoiService.findAll();
    }

    public Iterable<Persoana> findAllPersoane() {
        return persoanaService.findAll();
    }

    public void savePersoana(Persoana newUser) {
        persoanaService.save(newUser);
    }

    public void saveNevoie(Nevoi nevoie) {
        nevoiService.save(nevoie);
    }

    public void updateNevoie(Nevoi selectedNevoie) {
        nevoiService.update(selectedNevoie);
    }


}
