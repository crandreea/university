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
    DisciplinaRepo repo;
    Validator valid;
    DisciplinaService service{repo, valid};
    UI ui{service};
    ui.run();
    return 0;
}
