#pragma once
#include "repo.h"

class ServiceCaz{
private:
    RepoCaz& Repo;

public:
    explicit ServiceCaz(RepoCaz& repo):Repo{repo}{}

    void add(int id, string& bad, string& good, string& tip, string& severity);
    vector<Caz> getAll(){
        return Repo.getAll();
    };
    vector<Caz> sortByGood();
    vector<Caz> sortByTip();
    vector<Caz> sortBySeverity();

};