#pragma once
#include "domain.h"

class RepoCaz{
private:
    string fileName;

    void read();

    vector<Caz> cazuri;
    bool exist(Caz& caz){
        for(auto const& el : cazuri){
            if(el.get_id() == caz.get_id())
                return true;
        }
        return false;
    }
public:
    RepoCaz(string filename):fileName(filename){
        read();
    }
    //void write();
    void add(Caz caz);

    vector<Caz> getAll(){
        return cazuri;
    }
};
