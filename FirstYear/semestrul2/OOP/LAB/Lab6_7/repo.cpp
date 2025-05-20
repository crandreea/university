#include "repo.h"
#include "erori.h"

#include <iostream>
#include <sstream>
//#include <exception>

using std::ostream;
using std::stringstream;
using std::cout;
using std::string;
using std::exception;

bool DisciplinaRepo::exist(const Disciplina& d) const {
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
    for(int i = 0; i < elems.size(); i++){
        if(d.getName() == elems.get(i).getName() && d.getType() == elems.get(i).getType() && d.getProf() == elems.get(i).getProf())
            elems.erase(i);
    }
}

/**
 * Cauta un element in lista
 * @param name - denumirea disciplinei
 * @param type - tipul disciplinei
 * @param prof - numele cadrului didactic
 * @return d - disciplina
 */
const Disciplina& DisciplinaRepo::search(const string& name, const string& type, const string& prof) const {
    for(const auto& d: elems) {
        if(d.getName() == name && d.getType() == type && d.getProf() == prof) {
            return d;
        }
    }
    throw Exception("Nu exista aceasta disciplina!\n");
}


