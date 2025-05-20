#include "headers/contract.h"
#include <algorithm>
#include <random>

using std::shuffle;

void Contract::adaugaDisciplinaContract(const Disciplina &o) {
    contracte.push_back(o);
}

void Contract::adaugaDisciplinaRandom(vector<Disciplina> oferte, int numar) {
    shuffle(oferte.begin(), oferte.end(), std::default_random_engine(std::random_device{}()));
    int cnt = 0;
    while (cnt< numar && !oferte.empty()) {
        contracte.push_back(oferte.back());
        oferte.pop_back();
        cnt++;
    }
}

const vector<Disciplina> &Contract::getAllContracte() {
    return contracte;
}

void Contract::stergeContract() {
    contracte.clear();
}