#pragma once
#include "MD.h"

class MD;

class IteratorMD
{
	friend class MD;

private:

	//constructorul primeste o referinta catre Container
	//iteratorul va referi primul element din container
	IteratorMD(const MD& c);

	//contine o referinta catre containerul pe care il itereaza
	const MD& md;

	/* aici e reprezentarea  specifica a iteratorului */
    Nod* curent;

public:

		//reseteaza pozitia iteratorului la inceputul containerului
        //T(n)=O(1)
		void prim();

		//muta iteratorul in container
		// arunca exceptie daca iteratorul nu e valid

        //T(n)=O(1)
		void urmator();

		//verifica daca iteratorul e valid (indica un element al containerului)
        //T(n)=O(1)
		bool valid() const;

		//returneaza valoarea elementului din container referit de iterator
		//arunca exceptie daca iteratorul nu e valid
        //T(n)=O(1)
		TElem element() const;


        void revinoKPasi(int k);
};

