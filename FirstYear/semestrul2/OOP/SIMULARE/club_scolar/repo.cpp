#include "repo.h"

void RepoElev::add(Elev elev) {
    if(exist(elev))
        throw Exception("Elevul exista");
    else
        elevi.push_back(elev);
}

vector<Elev> RepoElev::getAll() {
    return elevi;
}

Elev RepoElev::search(int nr) {
    auto f = std::find_if(elevi.begin(), elevi.end(), [&](Elev& e){
        return e.get_nr_matricol() == nr;
    });
    if(f != elevi.end())
        return *f;
    else
        throw Exception("Nu exista acest elev!");
}

Elev RepoElev::searchList(string name, string scoala){
    auto f = std::find_if(elevi.begin(), elevi.end(), [&](Elev& e){
        return e.get_name() == name && e.get_scoala() == scoala;
    });
    if(f != elevi.end())
        return *f;
    else
        throw Exception("Nu exista acest elev!");
}

bool RepoElev::exist(Elev &elev) {
    try{
        search(elev.get_nr_matricol());
        return true;
    }
    catch (Exception&){
        return false;
    }
}
