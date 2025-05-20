package com.example.examen.service;

import com.example.examen.domain.Client;
import com.example.examen.domain.Ticket;
import com.example.examen.domain.validators.ClientValidator;
import com.example.examen.domain.validators.TicketValidator;
import com.example.examen.domain.validators.Validator;
import com.example.examen.repository.Repository;
import com.example.examen.repository.database.ClientRepository;
import com.example.examen.repository.database.TicketRepository;
import com.example.examen.utils.observer.Observable;
import com.example.examen.utils.observer.Observer;

import java.sql.SQLException;
import java.util.ArrayList;

public class ClientService  implements Observable {

    private ArrayList<Observer> observers = new ArrayList<>();
    private static ClientService instance = null;
    ClientRepository repository;

    public static ClientService getInstance() {
        if (instance == null) {
            instance = new ClientService();
        }

        return instance;
    }

    public ClientService() {
            Validator<Client> validator = new ClientValidator();
        this.repository = new ClientRepository(validator);
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

    public Client findByUsername(String username) throws SQLException {
        return repository.findByUsername(username);
    }
}
