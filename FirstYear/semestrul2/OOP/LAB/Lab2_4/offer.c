#include "offer.h"
#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>

/*
 * Creeaza o entitate de tipul Offer
 * param type: tipul entitatii (char)
 * param destination: destinatia entitatii (char)
 * param price: pretul (float)
 * return: Offer
 */
Offer* create(char *type, char *destination, char *date, float price) {
    Offer *new = malloc(sizeof(Offer));
    unsigned long nr = strlen(type) + 1;
    new->type =  malloc(nr * sizeof(char));
    strcpy(new->type, type);

    nr = strlen(destination) + 1;
    new->destination = malloc(nr* sizeof(char));
    strcpy(new->destination, destination);

    nr = strlen(date) + 1;
    new->date = malloc(nr* sizeof(char));
    strcpy(new->date, date);

    new->price = price;

    return new;
}

/*
 * Dealoca memoria ocupata de entitatea Offer
 * param: o (struct)
 */
void destroy(Offer *o) {
    free(o->type);
    free(o->destination);
    free(o->date);
    o->price = -1;
    free(o);
}

/*
 * Valideaza data asociata entiattii de tip Offer
 * parm: o (struct)
 * return:  0 - data este invalida
 *          1 - data este valida
 */
int isValidDate(Offer *o) {
    int day, month, year;

    sscanf(o->date, "%d/%d/%d", &day, &month, &year);
    if (month < 1 || month > 12)
        return 0;

    int daysInMonth;
    if (month == 1 || month == 3 || month == 5 || month == 7|| month == 8|| month == 10|| month == 12) {
            daysInMonth = 31;

    }
    else if( month == 4|| month == 6|| month == 9|| month == 11)
            daysInMonth = 30;

    else if(month == 2) {
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)

            daysInMonth = 29;
        else
            daysInMonth = 28;
    }

    if (day < 1 || day > daysInMonth)
        return 0;

    return 1;
}

/*
 * Verifica daca un string contine doar litere
 * param str: string input
 * return:  1 - contine doar litere
 *          0 - contine si alte caractere
 */
int containsOnlyLetters(const char *str) {
    while (*str) {
        if (!isalpha(*str)) {
            return 0; // Not a letter
        }
        str++;
    }
    return 1; // Only contains letters
}

/*
 * Valideaza o entitate de tipul Offer
 * param: o (struct)
 * return:  1 - daca tipul entitatii nu este valid
 *          2 - daca destinatia entitati nu este valida (vida)
 *          3 - daca data entitatii nu este valida
 *          0 - daca entitatea este vallida
 */
int validate(Offer *o) {
    if ((strcmp(o->type, "munte") != 0 && strcmp(o->type, "mare") != 0 && strcmp(o->type, "city break") != 0) ||
        strlen(o->type) == 0)
        return 1;
    if (strcmp(o->destination, " ") == 0 || containsOnlyLetters(o->destination)==0)
        return 2;
    if (strcmp(o->date, "") == 0 || isValidDate(o) == 0)
        return 3;
    if (o->price < 0)
        return 4;
    return 0;
}



