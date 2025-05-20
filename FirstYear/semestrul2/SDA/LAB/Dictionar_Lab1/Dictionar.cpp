#include "Dictionar.h"
#include <iostream>
#include "IteratorDictionar.h"

/*
 * BC = WC = AC
 * CT: theta(n)
 * CS: theta(n)
 */
void Dictionar::redimensionare() {
    auto perechiNoi = new TElem[2*capacitate];
    for(int i = 0; i<dimensiune; i++)
        perechiNoi[i] = perechi[i];

    capacitate = 2*capacitate;
    delete[] perechi;
    perechi = perechiNoi;

}

/*
 * BC = WC = AC
 * CT: theta(1)
 * CS: theta(1)
 */
Dictionar::Dictionar() {
	/* de adaugat */
    capacitate = 10;
    dimensiune = 0;
    perechi = new TElem [capacitate];
}

/*
 * BC = WC = AC
 * CT: theta(1)
 * CS: theta(1)
 */
Dictionar::~Dictionar() {
	/* de adaugat */
    delete[] perechi;
}

//BC: theta(1) - nu necesita redim, elem pe prima poz
//WC = AC: O(n) - necesita redim, elem pe ultima poz/nu exista
TValoare Dictionar::adauga(TCheie c, TValoare v){
	/* de adaugat */
    int aux;
    if (dimensiune == capacitate)
        redimensionare();

    for(int i=0; i<dimensiune; i++)
        if(perechi[i].first == c)
        {
            aux = perechi[i].second;
            perechi[i].second = v;
            return aux;
        }

    perechi[dimensiune]  = std::make_pair(c, v);
    dimensiune++;
	return NULL_TVALOARE;
}


//WC = AC: T(n) = theta(n)  - cheia nu exista
//BC: T(n) = theta(1)  - cheia exista si este prima din dictionar
TValoare Dictionar::cauta(TCheie c) const{
	/* de adaugat */
    for(int i=0; i<dimensiune; i++)
        if(perechi[i].first == c)
            return perechi[i].second;

	return NULL_TVALOARE;
}

//WC = AC = BC
//T(n) = theta(n)
TValoare Dictionar::sterge(TCheie c){
	/* de adaugat */
    for(int i=0; i<dimensiune; i++)
        if(perechi[i].first == c){
            TValoare val = perechi[i].second;
            for(int j = i; j<dimensiune - 1; j++)
                perechi[j] = perechi[j+1];
            dimensiune--;
            return val;
        }
	return NULL_TVALOARE;
}

/*
 * BC = WC = AC
 * CT: theta(1)
 * CS: theta(1)
 */
int Dictionar::dim() const {
	/* de adaugat */
	return dimensiune;
}

/*
 * BC = WC = AC
 * CT: theta(1)
 * CS: theta(1)
 */
bool Dictionar::vid() const{
	/* de adaugat */
    if(dimensiune == 0)
	    return true;
    return false;
}

/*
 * BC = WC = AC
 * CT: theta(1)
 * CS: theta(1)
 */
IteratorDictionar Dictionar::iterator() const {
	return  IteratorDictionar(*this);
}


