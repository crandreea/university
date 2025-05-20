#pragma once
#include "repo.h"

class ServiceElev{
private:
    RepoElev& repo;
public:
    ServiceElev() = default;
    ServiceElev(RepoElev& repo): repo(repo){
    }


    void addElev(int nr, const string& name, const string& scoala, const vector<string>& club);
    vector<Elev> getAll();
    Elev searchElev(string name, string scoala);
    vector<Elev> sortByName();
    vector<Elev> filter(const function<bool(const Elev &)> &fct);
    vector<Elev> filterByClub(string club);

};