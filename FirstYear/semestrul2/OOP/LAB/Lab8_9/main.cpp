/*
 *  Contract de studii
 *  Disciplina: denumire, ore pe saptamana, tip, cadru didactic
 *  - adaugare, stergere, modificare, afisare
 *  - cautare disciplina
 *  - filtrare disciplina dupa: nr ore, cadru didactic
 *  - sortare displine dupa: denumire, nr ore, cadru didactic + tip
 */


#include "repo.h"
#include "service.h"
#include "ui.h"
#include "teste.h"
#include "validator.h"

using namespace std;
int main() {
    testall();
    string fileName = "/Users/croitoruandreea/Desktop/ANUL1/sem2/OOP/LAB/Lab8/discipline.txt";
    //DisciplinaRepo repo;
    DisciplinaRepoFile repo{fileName};
    Validator valid;
    Contract contract;
    DisciplinaService service{repo, valid, contract};
    UI ui{service};
    ui.run();
    return 0;
}
