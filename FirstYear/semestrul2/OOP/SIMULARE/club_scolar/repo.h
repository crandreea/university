#pragma once
#include "domain.h"
using namespace std;

class RepoElev{
private:
    vector<Elev> elevi;
    bool exist(Elev& elev);
public:

    RepoElev() = default;

    void add(Elev elev);
    vector<Elev> getAll();
    Elev search(int nr);
    Elev searchList(string name, string scoala);

};