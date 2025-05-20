#include "IteratorColectie.h"
#include "Colectie.h"
#include <iostream>
using namespace std;

void IteratorColectie::deplasare() {
    while (curent < col.m && col.e[curent] == NIL) {
        curent++;
    }
}

IteratorColectie::IteratorColectie(const Colectie& c): col(c) {
	/* de adaugat */
    curent = 0;
    deplasare();
}

void IteratorColectie::prim() {
	/* de adaugat */
    curent = 0;
    deplasare();
}


void IteratorColectie::urmator() {
	/* de adaugat */
    if(!valid())
        throw std::exception();
    curent++;
    deplasare();
}


bool IteratorColectie::valid() const {
	/* de adaugat */
	return curent < col.m;
}



TElem IteratorColectie::element() const {
	/* de adaugat */
    if(!valid())
        throw std::exception();
	return col.e[curent];
}
