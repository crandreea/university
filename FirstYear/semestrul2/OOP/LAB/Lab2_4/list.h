#ifndef LAB2_4_LIST_H
#define LAB2_4_LIST_H

#include "offer.h"

typedef Offer *TElem;
typedef struct{
    TElem* elements;
    int lung;
    int capacitate;
}List;

/*
* Creeaza o lista goala
* return: lista
*/
List * create_list();

/*
 * Distruge o lista
 * param: l (lista)
*/
void destroy_list(List* l);

/*
 * Realoca spatiul pentru lista
 * param: l (lista)
 */
void resize(List *l);

/*
  Returneaza elementul din lista de pe pozitia data
  param: poz - pozitia elementului
  return: elementul de pe pozitia poz
*/
TElem get(List* l, int poz);

/*
  Returneaza lungimea unei liste
  param: l (list)
  return: lungimea listei l
*/
int size(List* l);

/*
 * Returneaza o copie a unei liste
 * param: l (list)
 * return: lista
 */
List * copy_list(List* l);

/*
 * Adauga in lista l elementul el
 * param: l (lista)
 * param: el - elementul care se adauga in lista (TElem)
 */
void add(List* l, TElem el);

/*
 * Sterge elementul de pe o pozitie
 * param: l (list)
 * param: poz - pozitia de pe care se sterge elementul (int)
 */
void delete(List *l, int poz);

/*
 * Modifica elementul de pe o anumita pozitie
 * param l: lista
 * param poz: pozitia de pe care se modifica elementul
 * param new_o: noul element cu care se inlocuieste cel modificat
 */
void modify(List* l, int poz, TElem new_o);


#endif //LAB2_4_LIST_H
