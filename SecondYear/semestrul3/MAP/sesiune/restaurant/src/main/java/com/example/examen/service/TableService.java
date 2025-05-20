package com.example.examen.service;

import com.example.examen.domain.Table;
import com.example.examen.domain.validators.TableValidator;
import com.example.examen.domain.validators.Validator;
import com.example.examen.repository.database.TableRepo;
import com.example.examen.utils.observer.Observable;
import com.example.examen.utils.observer.Observer;

import java.util.ArrayList;
import java.util.List;

public class TableService implements Observable {

    private ArrayList<Observer> observers = new ArrayList<>();
    private static TableService instance = null;
    TableRepo repository;

    public static TableService getInstance() {
        if (instance == null) {
            instance = new TableService();
        }

        return instance;
    }

    private TableService() {
        Validator<Table> validator = new TableValidator();
        this.repository = new TableRepo(validator);
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


    public List<Table> getTables() {  // Changed return type to List<Table>
        return (List<Table>) repository.findAll();  // Cast to List<Table> (Safe because findAll() returns a List)
    }
}
