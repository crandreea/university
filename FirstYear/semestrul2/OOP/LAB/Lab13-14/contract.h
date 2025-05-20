#pragma once
#include "disciplina.h"
#include <vector>
#include <algorithm>
#include <random>
#include <chrono>
#include "observer.h"
using std::vector;

class Contract : public Observable {
private:
    vector<Disciplina> contracte;
public:
    /*
     * Constructorul cosului.
     */
    Contract() = default;

    /*
     * Adauga o oferta in cos.
     * @param o: oferta ce trebuie adaugata in cos.
     */
    void adaugaDisciplinaContract(const Disciplina &o);

    /*
     * Adauga un numar de oferte in cos.
     * @param oferte: ofertele random ce trebuie adaugate in cos.
     * @param numar: numarul de oferte ce trebuie adaugate.
     */
    void adaugaDisciplinaRandom(vector<Disciplina> oferte, int numar);

    /*
     * Returneaza un vector cu toate ofertele din cos.
     */
    const vector<Disciplina> &getAllContracte();

    /*
     * Sterge toate ofertele din cos.
     */
    void stergeContract();
};
