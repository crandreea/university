#pragma once
#include<vector>
#include<utility>

using namespace std;

typedef int TCheie;
typedef int TValoare;

typedef std::pair<TCheie, TValoare> TElem;

class IteratorMD;

//class Nod;


//typedef Nod *PNod;

struct Nod {
    TElem info; // Informația stocată în nod (o pereche cheie - valoare)
    Nod* urmator; // Pointer către următorul nod din listă
};

class MD
{
	friend class IteratorMD;

private:
	/* aici e reprezentarea */

    Nod* prim;
    int dimensiune;

public:
	// constructorul implicit al MultiDictionarului
	MD();

	// adauga o pereche (cheie, valoare) in MD

    //BC T(n)=theta(1)
    //AC=WC T(n)=theta(n)
	void adauga(TCheie c, TValoare v);

	//cauta o cheie si returneaza vectorul de valori asociate

    //BC=WC=AC T(n)=theta(n)
	vector<TValoare> cauta(TCheie c) const;

	//sterge o cheie si o valoare 
	//returneaza adevarat daca s-a gasit cheia si valoarea de sters

    //BC T(n)=theta(1)
    //AC=WC T(n)=theta(n)
	bool sterge(TCheie c, TValoare v);

	//returneaza numarul de perechi (cheie, valoare) din MD
    //T(n)=O(1)
	int dim() const;

	//verifica daca MultiDictionarul e vid
    //T(n)=O(1)
	bool vid() const;

	// se returneaza iterator pe MD
    //T(n)=O(1)
	IteratorMD iterator() const;

	// destructorul MultiDictionarului	
	~MD();



};

