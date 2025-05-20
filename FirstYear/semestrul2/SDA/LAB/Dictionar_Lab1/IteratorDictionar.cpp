#include "IteratorDictionar.h"
#include "Dictionar.h"
#include <iostream>

using namespace std;

/*
 * BC = WC = AC
 * CT: theta(1)
 */
IteratorDictionar::IteratorDictionar(const Dictionar& d) : dict(d){
	/* de adaugat */
    index = 0;
}

/*
 * BC = WC = AC
 * CT: theta(1)
 */
void IteratorDictionar::prim() {
	/* de adaugat */
    this->index = 0;
}

/*
 * BC = WC = AC
 * CT: theta(1)
 */
void IteratorDictionar::urmator() {
	/* de adaugat */
    if(!this->valid())
        throw std::invalid_argument("Iterator nu e valid");
    index ++;
}

/*
 * BC = WC = AC
 * CT: theta(1)
 */
TElem IteratorDictionar::element() const{
	/* de adaugat */
    if(!this->valid())
        throw std::invalid_argument("Iterator nu e valid");
	return dict.perechi[index];
}

/*
 * BC = WC = AC
 * CT: theta(1)
 */
bool IteratorDictionar::valid() const {
	/* de adaugat */
    return index>=0 && index<dict.dim();
}

/*
 * BC =  WC = AC
 * T(n) = theta(1)
 */
void IteratorDictionar::anterior(){
    if(!this->valid())
        throw std::invalid_argument("Iterator nu e valid");
    else
        if(index == 0)
            index = -1;
        else
            dict.perechi[index] = dict.perechi[index-1];
}

/**
 * pre:  d -dictionar,it-iterator
 * post: exceptie daca iterator nu e valid, d'[index] = d[index-1]
 SubalgoritmAnterior(d,it)
    daca valid(it) atunci
        @ arunca exceptie
    altfel
        daca index = 0 atunci
            index <- -1
        altfel
            dict.perechi[index] <- dict.perechi[index-1]
 **/
