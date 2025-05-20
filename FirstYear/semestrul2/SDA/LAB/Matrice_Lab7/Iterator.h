#include "Matrice.h"
#include <iostream>
#include <exception>
using namespace std;
class IteratorMatrice {
private:
    const Matrice& matrice;
    int row;
    int col;

public:
    IteratorMatrice(const Matrice& m, int linie):matrice(m), row(linie), col(0){
    };

    //O(1)
    bool valid(const IteratorMatrice& other) const{
        return row != other.row|| col != other.col;
    };

    //O(1)
    TElem element() const{
        if(!valid(*this))
            throw std::exception();
        return matrice.element(row, col);
    };

    //O(1)
    void urmator(){
        if(!valid(*this))
            throw std::exception();
        ++col;
    };

};


/*
 * IteratorMatrice:
        Matrice& mat;
        row - intreg
        col - intreg

        Subalg element(){- returneaza elementul curent
            return matrice.element(row, col);
        }

        Subalg urmator(){ - se deplaseaza pe coloane
            col++;
        }

        Subalg valid(Iterator it){ - returneaza true daca iterator e valid, false altfel
            return row != other.row sau col != other.col;
        }

 */

