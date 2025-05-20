#ifndef LAB2_4_SERVICE_H
#define LAB2_4_SERVICE_H

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
int add_offer(List* l, char* type, char* destination, char* date, float price);

/*
 * Sterge oferta de pe o pozitie
 * param type: tipul ofertei (char)
 * param destination: destinatia ofertei (char)
 * param date: data ofertei (char)
 * return: 1 - pozitia este invalida
 *         0 - pozitia este valida si stergerea se poate efectua
 */
int delete_offer(List *l, char *type, char *destination, char *date);

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
int modify_offer(List* l, char* type, char* destination, char* date, float price);

/*
 * Sorteaza lista de oferte crescator / descrescator dupa pret si destinatie
 * param: l (lista)
 * param mod: modul de sortare (valid_mod(mod) == 1)
 * return: list - sortata
 */
List * sort(List* v, int mod);

/*
 * Valideaza modul de sortare 1/2
 * param mod: 1 sau 2
 * return:  1 - modul este valid
 *          0 - modul este invalid
 */
int validate_mod(int mod);

/*
 * Filtreaza lista de oferte dupa destinatie
 * param l: lista
 * param destination: destinatia ofertei (char)
 * return: lista filtrata
 */
List * filter_destination(List *l, char* destination);

/*
 * Filtreaza lista de oferte dupa tip
 * param l: lista
 * param type: tipul ofertei (char)
 * return: lista filtrata
 */
List * filter_type(List *l, char* type);

/*
 * Filtreaza lista de oferte dupa pret
 * param l: lista
 * param price: pretul ofertei (char)
 * return: lista filtrata
 */
List * filter_price(List *l, float price);


#endif //LAB2_4_SERVICE_H
