#pragma once
#include "repo.h"

class ServiceRochie{
private:
    RepoRochie& Repo;
public:
    ServiceRochie()=default;
    ServiceRochie(RepoRochie& repo):Repo{repo}{

    }
//    void addRochie(int id, string name, string marime, double pret, bool disp);
    vector<Rochie> getAll(){
        return Repo.getAll();
    }
    vector<Rochie> sortByMarime();
    vector<Rochie> sortByPret();
};