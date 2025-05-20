package com.example.examen.service;

import com.example.examen.domain.Order;
import com.example.examen.repository.database.OrderRepo;
import com.example.examen.utils.observer.ObservableImplementation;
import com.example.examen.utils.observer.Observer;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderService extends ObservableImplementation {

    private static OrderService Service = null;
    private final OrderRepo repository;

    public OrderService(OrderRepo repository) {
        this.repository = repository;
    }


    public static OrderService getInstance(OrderRepo repository) {
        if (Service == null) {
            Service = new OrderService(repository);
        }

        return Service;
    }


    public Optional<Order> addOrder(Order order) throws SQLException {
        return repository.save(order);
    }


    public List<Order> activeOrdersByIdDriverId(Integer id) throws SQLException {
        return repository.getActiveOrdersByDriverId(id);
    }


    public void markOrderAsFinished(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID-ul nu poate fi null! SERVICE");
        }

        Optional<Order> optionalOrder = repository.findOne(id);

        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            if (order.getStatus().equals("IN_PROGRESS")) {
                order.setStatus("FINISHED");
                order.setEndDate(LocalDateTime.now());
                repository.update(order);
            }
        } else {
            System.out.println("Comanda cu ID-ul " + id + " nu a fost găsită.");
        }
    }


}
