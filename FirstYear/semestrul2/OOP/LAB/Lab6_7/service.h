#pragma once
#include <string>
#include <functional>

#include "disciplina.h"
#include "repo.h"
#include "validator.h"
#include "vector_dinamic.h"

using std::string;
using std::function;

typedef int(*Sort)(const Disciplina&, const Disciplina&);

class DisciplinaService{
private:
    DisciplinaRepo& Repo;
    Validator& Valid;
    VectorDinamic<Disciplina> filtrare(const function<bool(const Disciplina&)>& fct);

public:
    explicit DisciplinaService(DisciplinaRepo& repo, Validator& valid):Repo{repo}, Valid{valid}{
    }
    DisciplinaService() = delete;
    DisciplinaService(const DisciplinaService& ot) = default;

    void addDisciplina(const string& name, int h, const string& type, const string& prof);
    void modifyDisciplina(const string& name, int h, const string& type, const string& prof);
    void deleteDisciplina(const string& name, const string& type, const string& prof);
    const Disciplina& searchDisciplina(const string& name, const string& type, const string& prof);
    VectorDinamic<Disciplina> filtrareProf(const std::string &prof) ;
    VectorDinamic<Disciplina> filtrareH(int h);

    VectorDinamic<Disciplina> sortareDiscipline(Sort functieSortare);

    VectorDinamic<Disciplina>& getAll() noexcept{
        return Repo.getAll();
    };
};