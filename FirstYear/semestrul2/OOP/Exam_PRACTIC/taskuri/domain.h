#pragma once
#include <iostream>
using namespace std;
using std::string;

class Task{
private:
    int id;
    string descriere;
    vector<string> programatori;
    string stare;

public:
    Task() = default;
    Task(int id, string descriere, vector<string> programatori, string stare) : id{id}, descriere{descriere}, programatori{programatori}, stare{stare}{}

    int getId() const{
        return id;
    }

    string getDescriere() const{
        return descriere;
    }

    vector<string> getNames() const{
        return programatori;
    }

    string getStare() const{
        return stare;
    }

    void setStare(string& new_stare){
        stare = new_stare;
    }

};