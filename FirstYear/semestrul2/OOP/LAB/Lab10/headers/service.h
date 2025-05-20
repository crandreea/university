#pragma once
#include <string>
#include <functional>

#include "disciplina.h"
#include "repo.h"
#include "validator.h"
#include "contract.h"
#include "undo.h"

using std::string;
using std::function;
using std::vector;

typedef int(*Sort)(const Disciplina&, const Disciplina&);

class DisciplinaService{
private:
    RepoAbstract& Repo;
    Validator& Valid;
    Contract& ContractCurr;
    vector<std::unique_ptr<ActiuneUndo>> undoActions;
    vector<Disciplina> filtrare(const function<bool(const Disciplina&)>& fct);

public:
    explicit DisciplinaService(RepoAbstract& repo, Validator& valid, Contract& contract_curr):Repo{repo}, Valid{valid}, ContractCurr{contract_curr}{
    }
    DisciplinaService() = delete;
    DisciplinaService(const DisciplinaService& ot) = default;

    void addDisciplina(const string& name, int h, const string& type, const string& prof);
    void modifyDisciplina(const string& name, int h, const string& type, const string& prof);
    void deleteDisciplina(const string& name, const string& type, const string& prof);
    const Disciplina& searchDisciplina(const string& name, const string& type, const string& prof);
    vector<Disciplina> filtrareProf(const std::string &prof) ;
    vector<Disciplina> filtrareH(int h);

    vector<Disciplina> sortareDisciplineDenumire();
    vector<Disciplina> sortareDisciplineOre();
    vector<Disciplina> sortareDisciplineTipProf();

    vector<Disciplina>& getAll() noexcept{
        return Repo.getAll();
    };

    void contractAdauga(const string &denumire);

    int contractAdaugaRandom(int nr);

    const vector<Disciplina> &getAllContract();

    void contractSterge();

    void contractExport(const string &filename);

    void undo();
};