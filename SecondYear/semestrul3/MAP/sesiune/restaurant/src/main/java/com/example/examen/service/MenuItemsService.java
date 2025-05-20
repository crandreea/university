package com.example.examen.service;

import com.example.examen.domain.MenuItem;
import com.example.examen.domain.Order;
import com.example.examen.domain.validators.MenuItemValidator;
import com.example.examen.domain.validators.OrderValidator;
import com.example.examen.domain.validators.Validator;
import com.example.examen.repository.database.MenuItemRepo;
import com.example.examen.repository.database.OrderRepo;
import com.example.examen.utils.observer.Observable;
import com.example.examen.utils.observer.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MenuItemsService implements Observable {
    private ArrayList<Observer> observers = new ArrayList<>();
    private static MenuItemsService instance = null;
    MenuItemRepo repository;

    public static MenuItemsService getInstance() {
        if (instance == null) {
            instance = new MenuItemsService();
        }

        return instance;
    }

    private MenuItemsService() {
        Validator<MenuItem> validator = new MenuItemValidator();
        this.repository = new MenuItemRepo(validator);
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

    public Iterable<MenuItem> getMenuItems() {
        return repository.findAll();
    }

    public Map<String, List<MenuItem>> getMenuGroupedByCategory() {
        List<MenuItem> items = (List<MenuItem>) repository.findAll();
        return items.stream().collect(Collectors.groupingBy(MenuItem::getCategory));
    }

}
