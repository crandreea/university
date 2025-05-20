package com.example.examen.service;

import com.example.examen.domain.Client;
import com.example.examen.domain.Flight;
import com.example.examen.domain.validators.ClientValidator;
import com.example.examen.domain.validators.FlightValidator;
import com.example.examen.domain.validators.Validator;
import com.example.examen.repository.Repository;
import com.example.examen.repository.database.ClientRepository;
import com.example.examen.repository.database.FlightRepository;
import com.example.examen.utils.observer.Observable;
import com.example.examen.utils.observer.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FlightService  implements Observable {
    private ArrayList<Observer> observers = new ArrayList<>();
    private static FlightService instance = null;
    FlightRepository repository;

    public static FlightService getInstance() {
        if (instance == null) {
            instance = new FlightService();
        }

        return instance;
    }

    public FlightService() {
        Validator<Flight> validator = new FlightValidator();
        this.repository = new FlightRepository(validator);
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

    public void updateAvailableSeats(Long flightId) {
        Flight flight = repository.findOne(flightId);
        if (flight != null) {
            flight.setSeats(flight.getSeats() - 1);
            repository.update(flight);
            notifyObservers(flight);
        }
    }

    public ObservableList<Flight> searchFlights(String fromLocation, String toLocation, LocalDate selectedDate) throws SQLException {
        List<Flight> allFlights = (List<Flight>) getAllFlights();

        // Add null check and proper property value comparison
        List<Flight> filteredFlights = allFlights.stream()
                .filter(flight -> flight != null) // Add null check
                .filter(flight -> flight.fromProperty().getValue().equals(fromLocation)) // Get value from property
                .filter(flight -> flight.toProperty().getValue().equals(toLocation))
                .filter(flight -> flight.departureTimeProperty().getValue().equals(selectedDate))
                .collect(Collectors.toList());

        return FXCollections.observableArrayList(filteredFlights);
    }

    private Iterable<Flight> getAllFlights() {
        return repository.findAll();
    }


}
