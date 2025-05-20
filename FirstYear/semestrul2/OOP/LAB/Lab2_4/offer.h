#ifndef LAB2_4_OFFER_H
#define LAB2_4_OFFER_H
typedef struct {
    char* type;
    char* destination;
    char* date;
    float price;
}Offer;

/*
 * Creeaza o entitate de tipul Offer
 * param type: tipul entitatii (char)
 * param destination: destinatia entitatii (char)
 * param price: pretul (float)
 * return: Offer
 */
Offer* create(char* type, char* destination, char* date, float price);

/*
 * Dealoca memoria ocupata de entitatea Offer
 * param: o (struct)
 */
void destroy(Offer *o);


/*
 * Valideaza o entitate de tipul Offer
 * param: o (struct)
 * return:  1 - daca tipul entitatii nu este valid
 *          2 - daca destinatia entitati nu este valida (vida)
 *          3 - daca data entitatii nu este valida
 *          0 - daca entitatea este vallida
 */
int validate(Offer*);

/*
 * Valideaza data asociata entiattii de tip Offer
 * parm: o (struct)
 * return:  0 - data este invalida
 *          1 - data este valida
 */
int isValidDate(Offer *);

/*
 * Verifica daca un string contine doar litere
 * param str: string input
 * return:  1 - contine doar litere
 *          0 - contine si alte caractere
 */
int containsOnlyLetters(const char *str);


#endif //LAB2_4_OFFER_H
