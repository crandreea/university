package com.example.marire.utils.observers;

public interface IObservable {
    void addObserver(IObserver e);
    void notifyObservers();
}