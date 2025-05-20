#pragma once
#include <vector>
using std::vector;

class Observer{
private:
public:
    virtual void update() = 0;
};

class Observable{
private:
    vector<Observer*> observers;
public:
    void add_observer(Observer* obs){
        observers.push_back(obs);
    }

    void remove_observer(Observer* obs){
        observers.erase(remove (observers.begin(), observers.end(), obs), observers.end());
    }

    void notify(){
        for (auto obs: observers)
            obs->update();
    }

};