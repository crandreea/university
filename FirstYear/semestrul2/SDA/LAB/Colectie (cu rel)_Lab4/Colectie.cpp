#include "Colectie.h"
#include "IteratorColectie.h"
#include <iostream>

using namespace std;

//T(n) = O(1)
bool rel(TElem e1, TElem e2) {
	/* de adaugat */
	return e1 <= e2;
}

//T(n) = O(capacitate)
Colectie::Colectie() {
	/* de adaugat */
    capacitate = 100;
    dimensiune = 0;
    prim = -1;
    primLiber = 0;
    elems = new std::pair<TElem, int>[capacitate];
    urmator = new int[capacitate];

    for (int i = 0; i < capacitate - 1; i++)
        urmator[i] = i + 1;

    urmator[capacitate - 1] = -1;
}

//BC = AC = WC
// T(n) = O(capacitate)
void Colectie::redim() {
    int newCapacity = capacitate * 2;
    auto* newElements = new std::pair<TElem, int>[newCapacity];
    auto* newUrmator = new int[newCapacity];

    for (int i = 0; i < capacitate; ++i) {
        newElements[i] = elems[i];
        newUrmator[i] = urmator[i];
    }
    for (int i = capacitate; i < newCapacity - 1; ++i) {
        newUrmator[i] = i + 1;
    }
    newUrmator[newCapacity - 1] = -1;

    delete[] elems;
    delete[] urmator;

    elems = newElements;
    urmator = newUrmator;
    primLiber = capacitate;
    capacitate = newCapacity;
}
//T(n) = O(1)
int Colectie::aloca() {
    int newPos = primLiber;
    primLiber = urmator[primLiber];
    return newPos;
}

//T(n) = O(1)
void Colectie::dealoca(int pos) {
    urmator[pos] = primLiber;
    primLiber = pos;
}

int Colectie::creeazaNod(TElem e) {
    int newPos = aloca();
    elems[newPos] = std::make_pair(e, 1);
    urmator[newPos] = -1;
    return newPos;
}

//BC: T(n) = theta(1) - primul elem
//WC = AC: theta(cap)
void Colectie::adauga(TElem e) {
	/* de adaugat */
    int curent = prim;
    int prec = -1;
    while (curent != -1 && rel(elems[curent].first, e)) {
        if (elems[curent].first == e) {
            elems[curent].second++;
            return;
        }
        prec = curent;
        curent = urmator[curent];
    }

    if (primLiber == -1) {
        redim();
    }

    int newPos = creeazaNod(e);
    if(prec == -1){
        urmator[newPos] = prim;
        prim = newPos;
    }
    else{
        urmator[newPos] = urmator[prec];
        urmator[prec] = newPos;
    }

    dimensiune++;
}


//BC: T(n) = theta(1) - primul elem
//AC = WC: T(n) = theta(cap)
bool Colectie::sterge(TElem e) {
    /* de adaugat */
    int current = prim;
    int prev = -1;

    while (current != -1 && rel(elems[current].first, e)) {
        if (elems[current].first == e) {
            if (elems[current].second > 1) {
                elems[current].second--;
            } else {
                if (prev == -1) {
                    prim = urmator[current];
                } else {
                    urmator[prev] = urmator[current];
                }
                dealoca(current);
                dimensiune--;
            }
            return true;
        }
        prev = current;
        current = urmator[current];
    }
    return false;
}

//BC: T(n) = theta(1) - primul elem
//AC = WC: T(n) = theta(cap)
bool Colectie::cauta(TElem elem) const {
	/* de adaugat */
    int current = prim;
    while (current != -1 && rel(elems[current].first, elem)) {
        if (elems[current].first == elem) {
            return true;
        }
        current = urmator[current];
    }
    return false;
}

//BC: T(n) = theta(1) - primul elem
//AC = WC: T(n) = theta(cap)
int Colectie::nrAparitii(TElem elem) const {
	/* de adaugat */
    int current = prim;
    while (current != -1 && rel(elems[current].first, elem)) {
        if (elems[current].first == elem) {
            return elems[current].second;
        }
        current = urmator[current];
    }
    return 0;
}

//BC = AC = WC
//T(n) = O(cap)
int Colectie::dim() const {
	/* de adaugat */
    int curent = prim;
    int sum = 0;
    while(curent!=-1){
        sum+=elems[curent].second;
        curent = urmator[curent];
    }
    return sum;
}

//T(n) = O(1)
bool Colectie::vida() const {
	/* de adaugat */
	return prim == -1;
}


//BC = AC = WC
//T(n) = O(cap)
int Colectie::transformăÎnMulțime(){
    int current = prim;
    int transf = 0;
    while (current != -1) {
        if (elems[current].second > 1) {
            transf += elems[current].second - 1;
            elems[current].second = 1;
        }
        current = urmator[current];
    }
    return transf;
}


/*
 * pre: col - colectie
 * post: col = col' (unde col' este multime)
 * returneaza: transf - intreg
 Subalgoritm transformaInMultime(col)
    curent <- prim
    transf <- 0
    cat timp curent != -1 executa
        daca elems[curent].second > 1 atunci
            transf += elems[curent].second - 1
            elems[curent].second = 1
        curent = urmator[curent]

    return transf
 */

//T(n) = O(1)
IteratorColectie Colectie::iterator() const {
	return  IteratorColectie(*this);
}


Colectie::~Colectie() {
	/* de adaugat */
    delete[] elems;
    delete[] urmator;
}
