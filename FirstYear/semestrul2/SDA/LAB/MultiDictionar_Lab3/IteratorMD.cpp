#include "IteratorMD.h"
#include "MD.h"

using namespace std;

IteratorMD::IteratorMD(const MD& _md): md(_md) {
	/* de adaugat */
    curent = md.prim;
}

TElem IteratorMD::element() const{
	/* de adaugat */
    if(!valid())
        throw std::invalid_argument("Iteratorul nu e valid");
    else
        return pair<TCheie, TValoare> (curent->info.first, curent->info.second);
}

bool IteratorMD::valid() const {
	/* de adaugat */
	return curent != nullptr;
}

void IteratorMD::urmator() {
	/* de adaugat */
    if(!valid())
        throw std::invalid_argument("Iteratorul nu e valid");
    else
    {
        curent = curent->urmator;
    }

}

void IteratorMD::prim() {
	/* de adaugat */
    curent = md.prim;
}

//Bc theta(1)
//Ac = Wc T(n) = O(n*k)
void IteratorMD::revinoKPasi(int k) {
    if (k <= 0 or !valid())
        throw std::invalid_argument("invalid..");
    else
        if(md.dimensiune <= k)
            curent = nullptr;
        else{

            Nod* start = md.prim;
            Nod* prec = nullptr;

            while (start != curent) {
                prec = start;
                start = start->urmator;
            }

            for (int i = 0; i < k; i++) {
                curent = prec;
                prec = md.prim;
                while (prec->urmator != curent) {
                    prec = prec->urmator ;
                }
            }
        }


}

/**
 * pre: k - intreg , curent - iterator, md - multidictionar
 * post: exceptie daca iterator nu e valid sau k<=0
 *       curent' = null (daca nu exista mai mult de k elem) sau curent' = curent - k
 *
 * Subalgoritm revinoKPasi(k, curent, md)
 *      daca !valid(curent) sau k<=0 atunci
 *          @ arunca exceptie
 *      altfel
 *          daca md.dimensiune <= k atunci
 *              curent <- nil
 *          altfel
 *              start <- md.prim
 *              prec <- nil
 *
 *              cat timp (start != curent)
 *                  prec <- start
 *                  start <- [start].urmator
 *
 *               pentru i<-0, k executa
 *                  curent <- prec
 *                  prec <- md.prim
 *
 *                  cat timp [prec].urmator != curent executa
 *                      prec = [prec]. urmator
 *
 */

