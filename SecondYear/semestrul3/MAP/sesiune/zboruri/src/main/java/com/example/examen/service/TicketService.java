package com.example.examen.service;

import com.example.examen.domain.Ticket;
import com.example.examen.domain.validators.TicketValidator;
import com.example.examen.domain.validators.Validator;
import com.example.examen.repository.Repository;
import com.example.examen.repository.database.TicketRepository;
import com.example.examen.utils.observer.Observable;
import com.example.examen.utils.observer.Observer;

import java.util.ArrayList;

public class TicketService implements Observable {

    private ArrayList<Observer> observers = new ArrayList<>();
    private static TicketService instance = null;
    TicketRepository repository;

    public static TicketService getInstance() {
        if (instance == null) {
            instance = new TicketService();
        }

        return instance;
    }
    public TicketService() {
        Validator<Ticket> validator = new TicketValidator();
        this.repository = new TicketRepository(validator);
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
}
