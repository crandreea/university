#include <stdexcept>
#include "IteratorColectie.h"
#include "Colectie.h"

using namespace std;

IteratorColectie::IteratorColectie(const Colectie& c): col(c) {
	/* de adaugat */
    curent = col.prim;
}

//T(n) = O(1)
TElem IteratorColectie::element() const{
	/* de adaugat */
    if(!valid())
        throw std::invalid_argument("Iteratorul nu e valid");
    else
        return col.elems[curent].first;
}

//T(n) = O(1)
bool IteratorColectie::valid() const {
	/* de adaugat */
	return curent != -1;
}

//T(n) = O(1)
void IteratorColectie::urmator() {
	/* de adaugat */
    if(!valid())
        throw std::invalid_argument("Iteratorul nu e valid");
    else
    {
        curent = col.urmator[curent];
    }
}

//T(n) = O(1)
void IteratorColectie::prim() {
	/* de adaugat */
    curent = col.prim;
}
