#pragma once
#define MAX 13
#define NIL INT_MIN
#define STERS INT_MIN+10
typedef int TElem;

class IteratorColectie;


class Colectie
{
	friend class IteratorColectie;

private:
        TElem* e;
        int d(TElem e, int i);//functia de dispersie
        void redim();

public:
        int m;
		//constructorul implicit
		Colectie();

		//adauga un element in colectie
		void adauga(TElem e);

		//sterge o aparitie a unui element din colectie
		//returneaza adevarat daca s-a putut sterge
		bool sterge(TElem e);

		//verifica daca un element se afla in colectie
		bool cauta(TElem elem);

		//returneaza numar de aparitii ale unui element in colectie
		int nrAparitii(TElem elem) const;

		//intoarce numarul de elemente din colectie;
		int dim() const;

		//verifica daca colectia e vida;
		bool vida() const;

        //goleste toate elem din colectie
        void goleste();

		//returneaza un iterator pe colectie
		IteratorColectie iterator() const;

		// destructorul colectiei
		~Colectie();

};

