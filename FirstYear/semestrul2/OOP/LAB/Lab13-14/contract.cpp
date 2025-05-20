#include "contract.h"
#include <algorithm>
#include <random>

using std::shuffle;

void Contract::adaugaDisciplinaContract(const Disciplina &o) {
    contracte.push_back(o);
    notify();
}

void Contract::adaugaDisciplinaRandom(vector<Disciplina> oferte, int numar) {
    shuffle(oferte.begin(), oferte.end(), std::default_random_engine(std::random_device{}()));
    while (contracte.size() < numar && !oferte.empty()) {
        contracte.push_back(oferte.back());
        oferte.pop_back();
    }
    notify();
}

const vector<Disciplina> &Contract::getAllContracte() {
    return contracte;
}

void Contract::stergeContract() {
    contracte.clear();
    notify();
}