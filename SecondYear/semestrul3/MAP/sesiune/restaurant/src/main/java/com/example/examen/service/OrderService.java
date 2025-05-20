package com.example.examen.service;

import com.example.examen.domain.Order;
import com.example.examen.domain.validators.OrderValidator;
import com.example.examen.domain.validators.Validator;
import com.example.examen.repository.database.OrderRepo;
import com.example.examen.repository.database.TableRepo;
import com.example.examen.utils.observer.Observable;
import com.example.examen.utils.observer.Observer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class OrderService implements Observable {
    private ArrayList<Observer> observers = new ArrayList<>();
    private static OrderService instance = null;
    OrderRepo repository;

    public static OrderService getInstance() {
        if (instance == null) {
            instance = new OrderService();
        }

        return instance;
    }

    private OrderService() {
        Validator<Order> validator = new OrderValidator();
        this.repository = new OrderRepo(validator);
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

    public Optional<Order> save(Order order) {
        Optional<Order> saved = repository.save(order);
        notifyObservers(saved);
        return saved;
    }


    public List<Order> getAllPlacedOrders() {
        // Inițializăm o listă pentru a stoca comenzile
        List<Order> orders = (List<Order>) repository.findAll();
        // Ordonați comenzile crescător după data plasării
        return orders.stream()
                .sorted(Comparator.comparing(Order::getDate))
                .collect(Collectors.toList());
    }

}
