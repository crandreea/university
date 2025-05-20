#include "service.h"

vector<Elev> ServiceElev::getAll() {
    return repo.getAll();
}

void ServiceElev::addElev(int nr, const std::string &name, const std::string &scoala, const std::vector<string> &club) {
    Elev e{nr, name, scoala, club};
    repo.add(e);
}

vector<Elev> ServiceElev::filter(const function<bool(const Elev &)> &fct) {
    const vector<Elev> diss = getAll();
    vector<Elev> rez;
    std::copy_if(diss.begin(), diss.end(), back_inserter(rez), fct);
    return rez;
}


vector<Elev> ServiceElev::filterByClub(std::string club) {
    return filter([club](const Elev&e) {
        for(auto const elem: e.get_club())
            if(elem == club)
                return true;
        return false;
    });
}

bool sortName(Elev& e1, Elev& e2){
    return e1.get_name()> e2.get_name();
}

vector<Elev> ServiceElev::sortByName() {
    vector<Elev> lista_sortata = repo.getAll();
    sort(lista_sortata.begin(), lista_sortata.end(), sortName);
    return lista_sortata;
}

Elev ServiceElev::searchElev(string name, string scoala) {
    return repo.searchList(name, scoala);
}
