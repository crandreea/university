#include "TestScurt.h"
#include <assert.h>
#include "Matrice.h"
#include "Iterator.h"
#include <iostream>

using namespace std;

void testAll() { //apelam fiecare functie sa vedem daca exista
	Matrice m(4,4);
	assert(m.nrLinii() == 4);
	assert(m.nrColoane() == 4);
	//adaug niste elemente
	m.modifica(1,1,5);
	assert(m.element(1,1) == 5);
	m.modifica(1,1,6);
    assert(m.element(1,2) == NULL_TELEMENT);
    m.modifica(1,2,6);
    m.modifica(1,3,7);
    m.modifica(2,1,6);
    IteratorMatrice it = m.iterator(1);
    while (it.valid(it)) {
        //TElem e = it.element();
        assert(it.element() != 0);
        it.urmator();
    }

}
