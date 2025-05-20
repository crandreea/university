#include "MD.h"
#include "IteratorMD.h"
#include <exception>
#include <iostream>

using namespace std;

void redimensionare(){

}

MD::MD() {
	/* de adaugat */
    dimensiune = 0;
    prim = new Nod;
    prim = nullptr;
}


void MD::adauga(TCheie c, TValoare v) {
	/* de adaugat */
    Nod* q = new Nod;
    q->info = TElem(c, v);
    q->urmator = nullptr;

    if(prim == nullptr){
        prim = q;
        dimensiune ++;
        return;
    }

    auto start = prim;
    while(start -> urmator != nullptr)
        start = start->urmator;

    start->urmator = q;
    dimensiune++;

}


bool MD::sterge(TCheie c, TValoare v) {
	/* de adaugat */
    if(prim==NULL){
        return false;
    }
    //doar un element si e cel cautat
    if(prim->urmator==NULL){
        if(prim->info== make_pair(c,v)){
            prim=NULL;
            dimensiune--;
            return true;
        }
        return false;
    }
    //primul elem se sterge
    if(prim->info == make_pair(c,v)){
        Nod* deleted = prim;
        prim = prim->urmator;
        delete deleted;
        dimensiune--;
        return true;
    }
    auto head=prim;
    while(head->urmator!=NULL &&  head->urmator->info!= make_pair(c,v)){
        head=head->urmator;
    }
    if(head->urmator==NULL){
        return false;
    } else {
        Nod* deleted = head->urmator;

        head->urmator=head->urmator->urmator;

        delete deleted;
        dimensiune--;
        return true;
    }

}


vector<TValoare> MD::cauta(TCheie c) const {
	/* de adaugat */
    vector<TValoare> rez;
    auto start = prim;
    while(start!= nullptr){
        if(start->info.first == c)
            rez.push_back(start->info.second);
        start=start->urmator;
    }
	return rez;
}


int MD::dim() const {
	/* de adaugat */
    return dimensiune;
}


bool MD::vid() const {
	/* de adaugat */
	return dimensiune == 0;
}

IteratorMD MD::iterator() const {
	return IteratorMD(*this);
}

MD::~MD() {
    /* de adaugat */
    Nod* current = prim;
    while (current != nullptr) {
        Nod* next = current->urmator;
        delete current;
        current = next;
    }
}


