#include "service.h"
#include <string>


void DisciplinaService::addDisciplina(const string& name, int h, const string& type, const string& prof){
    Validator::validateDisciplina(name, h, type, prof);
    Disciplina d{name, h, type, prof};
    Repo.store(d);
}

void DisciplinaService::modifyDisciplina(const string& name, int h, const string& type, const string& prof){
    Validator::validateDisciplina(name, h, type, prof);
    Disciplina d = Repo.search(name, type, prof);
    Repo.sterge(d);
    Disciplina new_d{name, h, type, prof};
    Repo.store(new_d);
}

void DisciplinaService::deleteDisciplina(const string& name, const string& type, const string& prof){
    Validator::validateDisciplina(name, 1, type, prof);
    Disciplina d = Repo.search(name, type, prof);
    Repo.sterge(d);
}

const Disciplina& DisciplinaService::searchDisciplina(const string& name, const string& type, const string& prof){
    return Repo.search(name, type, prof);
}


VectorDinamic<Disciplina> DisciplinaService::filtrare(const function<bool(const Disciplina&)>& fct){
    VectorDinamic<Disciplina> rez;
    for(const auto& oferta: Repo.getAll()) {
        if(fct(oferta)) {
            rez.push_back(oferta);
        }
    }
    return (rez);
}

VectorDinamic<Disciplina> DisciplinaService::filtrareProf(const std::string &prof){
    return filtrare([prof](const Disciplina& d) {
        return d.getProf() == prof;
    });
}

VectorDinamic<Disciplina> DisciplinaService::filtrareH(int h) {
    return filtrare([h](const Disciplina& d) {
        return d.getHours() <= h;
    });
}

VectorDinamic<Disciplina> DisciplinaService::sortareDiscipline(Sort functieSortare) {
    VectorDinamic<Disciplina> lista_sortata;
    VectorDinamic<Disciplina> lista = Repo.getAll();

    for(int i = 0; i<lista.size(); i++) {
        lista_sortata.push_back(Disciplina(lista.get(i)));
    }

    bool sorted = false;
    while(!sorted) {
        sorted = true;
        for(int i = 0; i<lista_sortata.size()-1; i++) {
            if(functieSortare(lista_sortata.get(i), lista_sortata.get(i+1))>0) {
                Disciplina aux = lista_sortata.get(i);
                lista_sortata.get(i) = lista_sortata.get(i+1);
                lista_sortata.get(i+1) = aux;
                sorted = false;
            }
        }
    }
    return lista_sortata;
}
