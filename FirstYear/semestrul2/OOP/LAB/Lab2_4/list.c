#include "list.h"
#include <stdlib.h>

/*
* Creeaza o lista goala
* return: lista
*/
List * create_list() {
    List *rez = malloc(sizeof(List));
    rez->lung = 0;
    rez->capacitate = 2;
    rez->elements = malloc(rez->capacitate * sizeof(TElem));
    return rez;
}

/*
 * Distruge o lista
 * param: l (lista)
*/
void destroy_list(List *l) {

    for(int i = 0; i < size(l); i++)
    {
        destroy(l->elements[i]);
    }
    l->lung = 0;
    free(l->elements);
    free(l);
}

/*
  Returneaza elementul din lista de pe pozitia data
  param: poz - pozitia elementului
  return: elementul de pe pozitia poz
*/
TElem get(List *l, int poz) {
    return l->elements[poz];
}

/*
  Returneaza lungimea unei liste
  param: l (list)
  return: lungimea listei l
*/
int size(List *l) {
    return l->lung;
}

/*
 * Realoca spatiul pentru lista
 * param: l (lista)
 */
void resize(List *l){
    int new_capacitate =  2 * l->capacitate;
    TElem *new_elements = malloc(new_capacitate * sizeof(TElem));
    for(int i = 0; i < l->lung; i++)
    {
        new_elements[i] = l->elements[i];
    }
    free(l->elements);
    l->elements = new_elements;
    l->capacitate = new_capacitate;
}



/*
 * Adauga in lista l elementul el
 * param: l (lista)
 * param: el - elementul care se adauga in lista (TElem)
 */
void add(List *l, TElem el) {
    if(l->lung == l->capacitate){
        resize(l);
    }
    l->elements[l->lung] = el;
    l->lung++;
}

/*
 * Sterge elementul de pe o pozitie
 * param: l (list)
 * param: poz - pozitia de pe care se sterge elementul (int)
 */
void delete(List *l, int poz) {
    for (int i = poz; i < size(l) - 1; i++) {
        l->elements[i] = l->elements[i + 1];
    }
    l->lung--;
}

/*
 * Modifica elementul de pe o anumita pozitie
 * param l: lista
 * param poz: pozitia de pe care se modifica elementul
 * param new_type: tipul entitatii (char)
 * param new_destination: destinatia entitatii (char)
 * param new_date: data entitatii (char)
 * param new_price: pretul (float)
 */
void modify(List *l, int poz, TElem new_o) {
    destroy(l->elements[poz]);
    l->elements[poz] = new_o;

}

/*
 * Returneaza o copie a unei liste
 * param: l (list)
 * return: lista
 */
List * copy_list(List* l) {
    List * rez = create_list();
    for (int i = 0; i < size(l); i++) {
        TElem o = get(l, i);
        TElem new_o = create(o->type, o->destination, o->date, o->price);
        add(rez, new_o);
    }

    return rez;
}
