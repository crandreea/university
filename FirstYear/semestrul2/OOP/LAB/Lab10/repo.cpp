#include "headers/repo.h"
#include "headers/erori.h"

#include <iostream>
#include <fstream>
#include <sstream>

using std::ostream;
using std::stringstream;
using std::string;
using std::exception;

bool DisciplinaRepo::exist(const Disciplina& d) {
    try {
        search(d.getName(), d.getType(), d.getProf());
        return true;
    }
    catch (Exception&) {
        return false;
    }
}

/**
 * Adauga un element in lista elems
 * @param d - disciplina
 */
void DisciplinaRepo::store(const Disciplina& d){
    if(exist(d)){
        throw Exception("Exista deja o disciplina de acest fel!\n");
    }
    elems.push_back(d);

}

/**
 * Sterge un element dintr o lista elems
 * @param d - disciplina
 */
void DisciplinaRepo::sterge(Disciplina& d){
    elems.erase(std::remove_if(elems.begin(), elems.end(), [&d](const Disciplina& elem){
        return (d.getName() == elem.getName() && d.getType() == elem.getType() && d.getProf() == elem.getProf());
    }), elems.end());

}

/**
 * Cauta un element in lista
 * @param name - denumirea disciplinei
 * @param type - tipul disciplinei
 * @param prof - numele cadrului didactic
 * @return d - disciplina
 */
const Disciplina& DisciplinaRepo::search(const string& name, const string& type, const string& prof)  {
    auto f = std::find_if(elems.begin(), elems.end(), [&](const Disciplina &o) {
        return (o.getName() == name && o.getType() == type && o.getProf() == prof);
    });
    if (f != elems.end())
        return *f;
    else
        throw Exception("Nu exista aceasta disciplina!\n");
}

/**
 * Filtraza elementele dupa disciplina
 * @param name - denumirea disciplinei
 * @return rez - lista
 */
vector<Disciplina> DisciplinaRepo::searchbyName(const string& name){
    vector<Disciplina> rez;
    std::copy_if(elems.begin(), elems.end(), std::back_inserter(rez), [&](const Disciplina& d) {
        return d.getName() == name;
    });

    if(rez.empty())
        throw Exception("Nu exista aceasta disciplina!\n");

    return rez;
}


void DisciplinaRepoFile::loadFromFile() {
    std::ifstream in(fileName);
    if (!in.is_open()) throw Exception("Eroare la deschiderea fisierului!!!");
    string denumire, prof, tip;
    int ore;
    while (in>>denumire>>ore>>tip>>prof) {
        Disciplina d{denumire, ore, tip, prof};
        DisciplinaRepo::store(d);
    }
    in.close();
}

void DisciplinaRepoFile::writeToFile() {
    std::ofstream out(fileName);
    if (!out.is_open()) throw Exception("Eroare la deschiderea fisierului!!!");
    for (auto &o: DisciplinaRepo::getAll()) {
        out << o.getName()<<" "<<o.getHours()<<" "<< o.getType()<<" "<< o.getProf();
        out << std::endl;
    }
    out.close();
}


