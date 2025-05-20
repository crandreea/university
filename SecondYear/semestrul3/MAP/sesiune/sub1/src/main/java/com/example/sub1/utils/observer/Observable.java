package com.example.sub1.utils.observer;

public interface Observable {
    void addObserver(Observer e);

    void removeObserver(Observer e);

    void notifyObservers(Object arg);
}