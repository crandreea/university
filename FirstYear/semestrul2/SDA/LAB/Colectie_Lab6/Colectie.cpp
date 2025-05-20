#include "Colectie.h"
#include "IteratorColectie.h"
#include <exception>
#include <iostream>

using namespace std;

int hasdCode(TElem elem) {
    return abs(elem) ;
}

//theta(1)
int Colectie::d(TElem elem, int i) {
    unsigned long c1, c2;
    c1 = hasdCode(elem) % m;
    c2 = (1 + hasdCode(elem) % (m - 2)) % m;

    return int((c1 + (i * c2) % m) % m);
}

Colectie::Colectie() {
	/* de adaugat */
    m = MAX;
    e = new TElem [m];
    for(int i = 0; i< m; i++)
    {
        e[i] = NIL;
    }

}

bool prim(int m){
    if(m <= 1 || m % 2 == 0)
        return false;
    for(int d = 3; d*d <= m; d+=2)
        if(m%d == 0)
            return false;
    return true;

}
//theta(m^2)
void Colectie::redim() {
    int mv = m;
    m = m + 2;
    while(!prim(m))
        m+=2;
    TElem* eNou = new int[m];
    TElem* copie = new int[mv];

    for (int i = 0; i < mv; i++) {
        copie[i] = e[i];
    }

    for (int i = 0; i < m; i++) {
        eNou[i] = NIL;
    }

    delete[] e;

    e = eNou;

    for (int i = 0; i < mv; i++) {
        adauga(copie[i]);
    }

    delete[] copie;

}

//BC: theta(1)
//wc=AC: theta(m)
void Colectie::adauga(TElem el) {
	/* de adaugat */
    int i = 0;
    bool gasit = false;
    do {
        int j = d(el, i);
        if (e[j] == NIL || e[j] == STERS) {
            e[j] = el;
            gasit = true;
        }
        else i++;
    } while (i<m && !gasit);

    if (i == m) {
        //depasire tabela
        redim();
        adauga(el);
    }
}

//BC: theta(1)
//wc=AC: theta(m)
bool Colectie::sterge(TElem el) {
    /* de adaugat */
    int i = 0;
    bool gasit=false;
    do {
        int j = d(el, i);
        if (e[j] == NIL)
            break;
        if (e[j] == el) {
            e[j] = STERS;
            gasit = true;
        }
        else i++;
    } while (i < m && !gasit);
    return gasit;
}

//BC: theta(1)
//wc=AC: theta(m)
bool Colectie::cauta(TElem el) {
	/* de adaugat */
    int i = 0;
    bool gasit = false;
    do {
        int j = d(el, i);
        if (e[j] == NIL)
            return false;
        if (e[j] == el) {
            gasit = true;
        }
        else i++;
    } while (i < m && !gasit);
    return gasit;

}

//theta(m)
int Colectie::nrAparitii(TElem el) const {
	/* de adaugat */
    int nrAparitii = 0;
    for (int i = 0; i < m; i++)
        if (e[i] == el)
            nrAparitii++;
    return nrAparitii;

}

//theta(m)
void Colectie::goleste() {
    for (int i = 0; i < m; i++)
        if (e[i] != NIL)
            e[i]=NIL;

}

/*
 * pre: c - colectie
 * post: c = c' (c' - colectie vida)
 * Subalgoritm goleste(c)
 *      pentru i<-0, m executa
 *          daca e[i] != NIL atunci
 *              e[i]<-NIL
 *          sfdaca
 *      sfpentru
 */


int Colectie::dim() const {
	/* de adaugat */
    int nrElem = 0;
    for (int i = 0; i < m; i++)
        if (e[i] != NIL && e[i] != STERS)
            nrElem++;
    return nrElem;
}


bool Colectie::vida() const {
	/* de adaugat */
	return dim() == 0;
}

IteratorColectie Colectie::iterator() const {
	return  IteratorColectie(*this);
}


Colectie::~Colectie() {
	/* de adaugat */
    delete [] e;
}




