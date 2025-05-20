#include "service.h"
#include <string.h>
#include "list.h"

/*
 * Adauga o oferta in lista
 * param l: lista
 * param type: tipul ofertei (char)
 * param destination: destinatia ofertei (char)
 * param date: data ofertei (char)
 * param price: pretul ofertei(float)
 * return: 1 - daca s-a efectuat adaugarea
 *         0 - daca datele au fost invalide
 */
int add_offer(List *l, char *type, char *destination, char *date, float price) {
    Offer* o = create(type, destination, date, price);

    int valid = validate(o);
    if (valid != 0) {
        destroy(o);
        return valid;
    }
    for(int i = 0; i< size(l); i++) {
        Offer *el = get(l,i);
        if(strcmp(el->type,type)==0 && strcmp(el->destination, destination)==0 && strcmp(el->date, date) == 0) {
            destroy(o);
            return 1;
        }
    }

    add(l, o);
    return 0;
}

/*
 * Sterge oferta de pe o pozitie
 * param type: tipul ofertei (char)
 * param destination: destinatia ofertei (char)
 * param date: data ofertei (char)
 * return: 1 - pozitia este invalida
 *         0 - pozitia este valida si stergerea se poate efectua
 */
int delete_offer(List *l, char *type, char *destination, char *date) {

    for (int pos = 0; pos < size(l); pos++) {
        Offer* o = get(l, pos);
        if (strcmp(o->type, type) == 0 && strcmp(o->destination, destination)==0 && strcmp(o->date, date)==0) {
            destroy(o);
            delete(l, pos);
            return 0;
        }
    }

    return 1;
}

/*
 * Modifica o oferta in functie de pozitia ei
 * param l: lista
 * param type: tipul ofertei (char)
 * param destination: destinatia ofertei (char)
 * param date: data ofertei (char)
 * param price: pretul ofertei(float)
 * return:  1 - pozitia este invalida
 *          0 - pozitia este valida si modificarea se poate efectua
 */
int modify_offer(List *l, char *type, char *destination, char *date, float price) {

    Offer* new_o = create(type, destination, date, price);
    int valid = validate(new_o);
    if (valid != 0) {
        destroy(new_o);
        return valid;
    }

    for (int pos = 0; pos < size(l); pos++) {
        Offer* o = get(l, pos);
        if (strcmp(o->type, type) == 0 && strcmp(o->destination, destination)==0 && strcmp(o->date, date)==0) {
            modify(l, pos, new_o);
            return 0;
        }
    }
    destroy(new_o);
    return 1;

}

/*
 * Valideaza modul de sortare 1/2
 * param mod: 1 sau 2
 * return:  1 - modul este valid
 *          0 - modul este invalid
 */
int validate_mod(int mod)
{
    if(mod != 1 && mod != 2)
        return 0;
    else
        return 1;

}

/*
 * Sorteaza lista de oferte crescator / descrescator dupa pret si destinatie
 * param: l (lista)
 * param mod: modul de sortare (valid_mod(mod) == 1)
 * return: list - sortata
 */

List * sort(List* v,  int mod) {
    List * lista_sortata = copy_list(v);
    TElem  aux;
        for (int i = 0; i < size(lista_sortata) - 1; i++)
        {
            for (int j = i + 1; j < size(lista_sortata); j++)
            {
                if ((mod == 1 && (lista_sortata->elements[i]->price > lista_sortata->elements[j]->price)) || (mod == 2 && (lista_sortata->elements[i]->price < lista_sortata->elements[j]->price)) )
                {
                    aux = get(lista_sortata, i);
                    lista_sortata->elements[i] = lista_sortata->elements[j];
                    lista_sortata->elements[j] = aux;
                }
                else if (lista_sortata->elements[i]->price == lista_sortata->elements[j]->price)
                {
                    if ((mod == 1 && strcmp(lista_sortata->elements[i]->destination, lista_sortata->elements[j]->destination) > 0) || (mod == 2 && strcmp(lista_sortata->elements[i]->destination, lista_sortata->elements[j]->destination) <= 0))
                    {
                        aux = lista_sortata->elements[i];
                        lista_sortata->elements[i] = lista_sortata->elements[j];
                        lista_sortata->elements[j] = aux;
                    }
                }
            }
        }

    return lista_sortata;
}

/*
 * Filtreaza lista de oferte dupa destinatie
 * param l: lista
 * param destination: destinatia ofertei (char)
 * return: lista filtrata
 */
List * filter_destination(List *l, char* destination){
    List * rez = create_list();
    for( int i = 0; i< size(l); i++)
    {
        TElem elem = get(l, i);
        if(strcmp(elem->destination, destination) == 0) {
            TElem new_o = create(elem->type, elem->destination, elem->date, elem->price);
            add(rez, new_o);
        }

    }
    return rez;
}

/*
 * Filtreaza lista de oferte dupa tip
 * param l: lista
 * param type: tipul ofertei (char)
 * return: lista filtrata
 */
List * filter_type(List *l, char* type){
    List *rez = create_list();
    for( int i = 0; i< size(l); i++)
    {
        TElem elem = get(l, i);
        if(strcmp(elem->type, type) == 0)
        { TElem new_o = create(elem->type, elem->destination, elem->date, elem->price);
        add(rez, new_o);}

    }
    return rez;
}

/*
 * Filtreaza lista de oferte dupa pret
 * param l: lista
 * param price: pretul ofertei (char)
 * return: lista filtrata
 */
List * filter_price(List *l, float price){
    List * rez = create_list();
    for( int i = 0; i< size(l); i++)
    {
        TElem elem = get(l, i);
        if(elem->price == price)
        { TElem new_o = create(elem->type, elem->destination, elem->date, elem->price);
            add(rez, new_o);}

    }
    return rez;
}
