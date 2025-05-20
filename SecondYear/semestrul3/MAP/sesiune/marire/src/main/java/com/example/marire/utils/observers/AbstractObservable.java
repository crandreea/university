package com.example.marire.utils.observers;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractObservable implements IObservable {
    List<IObserver> lst = new ArrayList<>();

    @Override
    public void addObserver(IObserver o) {
        lst.add(o);
    }

    @Override
    public void notifyObservers() {
        for(IObserver o:lst){
            o.update();
        }
    }
}
