/*
9. Agentie de turism

Creati o aplicatie care permite gestiunea ofertelor de la o agentie de turism.
Fiecare oferta are: tip (munte, mare, city break), destinatie, data plecare, pret
Aplicatia permite:
 a) Adaugarea de noi oferte
 b) Actualizare oferte
 c) Stergere oferta
 d) Vizualizare oferte ordonat dupa pret, destinatie (crescator/descrescator)
 e) Vizualizare oferta filtrate dupa un criteriu (destinatie, tip, pret)
 */

#include "offer.h"
#include "list.h"
#include "service.h"
#include "tests.h"

#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>
#include <unistd.h>

void addUI(List* l){
    char tip[100];
    char destination[50];
    char date[11];
    float price;

    printf("Introduceti datele ofertei: \n");
    printf("Tip (munte/mare/city break): \n");

    fgets(tip, sizeof(tip), stdin);

    if (tip[strlen(tip) - 1] == '\n') {
        tip[strlen(tip) - 1] = '\0';
    }

    printf("Destinatie:\n");
    scanf("%s",destination);

    printf("Data plecare (dd/mm/yy):\n");
    scanf("%s", date);

    printf("Pret:\n");
    scanf("%f", &price);

    int error = add_offer(l, tip, destination, date, price);
    if (error != 0) {
        printf("Oferta nu este valida...\n");
    }
    else {
        printf("Oferta a fost adaugata!\n");
    }
    printf("\n");
}

void modifyUI(List* l){
    char tip[100];
    char destination[50];
    char date[11];
    float price;

    printf("Introduceti datele ofertei: \n");
    printf("Tip (munte/mare/city break):\n");
    fgets(tip, sizeof(tip), stdin);

    if (tip[strlen(tip) - 1] == '\n') {
        tip[strlen(tip) - 1] = '\0';
    }

    printf("Destinatie:\n");
    scanf("%s",destination);

    printf("Data plecare (dd/mm/yy):\n");
    scanf("%s", date);

    printf("Noul pret:\n");
    scanf("%f", &price);

    int error = modify_offer(l, tip, destination, date, price);
    if (error != 0) {
        printf("Oferta nu este valida...\n");
    }
    else {
        printf("Oferta a fost modificata!\n");
    }
    printf("\n");
}

void deleteUI(List* l){
    char tip[100];
    char destination[50];
    char date[11];

    printf("Introduceti datele ofertei: \n");
    printf("Tip (munte/mare/city break):\n");
    fgets(tip, sizeof(tip), stdin);

    if (tip[strlen(tip) - 1] == '\n') {
        tip[strlen(tip) - 1] = '\0';
    }

    printf("Destinatie:\n");
    scanf("%s",destination);

    printf("Data plecare (dd/mm/yy):\n");
    scanf("%s", date);

    int error = delete_offer(l, tip, destination, date);
    if (error != 0) {
        printf("Oferta nu exista...\n");
    }
    else {
        printf("Oferta a fost stearsa!\n");
    }
    printf("\n");
}

void sortUI(List* l){
    int mod;
    printf("Introduceti modul de sortare (1 - crescator/ 2 - descrescator): \n");
    scanf("%d", &mod);
    if(validate_mod(mod) == 0)
        printf("Modul introdus este invalid...\n");
    else
    {
        List * lista_sortata = sort(l, mod);
        printf("Oferte sortate:\n");
        for (int i = 0; i < size(lista_sortata); i++) {
            Offer * o = get(lista_sortata, i);
            printf("Tip:%s Destinatie:%s Data:%s Pret:%.3f\n", o->type, o->destination, o->date, o->price);
        }
        destroy_list(lista_sortata);
    }
    printf("\n");
}

void filterUI(List* l){
    int crt;
    char destination[30];
    char type[30];
    float price;

    while(true) {

        printf("Alegeti creteriul de filtrare: \n");
        printf("0.Meniu principal\n1.Tip\n2.Destinatie\n3.Pret\n");
        scanf("%d", &crt);
        printf("\n");
        getchar();

        if (crt == 0) {
            return;
        } else if (crt == 1) {
            printf("Introduceti tipul ofertei (mare/munte/city break): \n");
            fgets(type, sizeof(type), stdin);

            if (type[strlen(type) - 1] == '\n') {
                type[strlen(type) - 1] = '\0';
            }
            List *rez = filter_type(l, type);

            if (size(rez) == 0) {
                printf("Nu exista oferte de acest tip...\n");
            } else {
                printf("Oferte filtrate dupa tip:\n");
                for (int i = 0; i < size(rez); i++) {
                    Offer *o = get(rez, i);
                    printf("Tip: %s  Destinatie: %s  Data: %s  Pret: %.3f\n", o->type, o->destination, o->date,
                           o->price);
                }
            }
        } else if (crt == 2) {
            printf("Introduceti destinatia: \n");
            scanf("%s", destination);
            List *rez = filter_destination(l, destination);

            if (size(rez) == 0) {
                printf("Nu exista oferte cu aceasta destinatie...\n");
            } else {
                printf("Oferte filtrate dupa destinatie:\n");
                for (int i = 0; i < size(rez); i++) {
                    Offer *o = get(rez, i);
                    printf("Tip: %s  Destinatie: %s  Data: %s  Pret: %.3f\n", o->type, o->destination, o->date,
                           o->price);
                }
            }

        } else if (crt == 3) {
            printf("Introduceti pretul: \n");
            scanf("%f", &price);
            List *rez = filter_price(l, price);

            if (size(rez) == 0) {
                destroy_list(rez);
                printf("Nu exista oferte cu acest pret...\n");
            } else {
                printf("Oferte filtrate dupa pret:\n");
                for (int i = 0; i < size(rez); i++) {
                    Offer *o = get(rez, i);
                    printf("Tip: %s  Destinatie: %s  Data: %s  Pret: %.3f\n", o->type, o->destination, o->date,
                           o->price);
                }
            }

        } else {
            printf("Criteriu invalid...\n");
        }
        printf("\n");
    }

}

void printUI(List* l){
    if(size(l)==0)
    {
        printf("Nu exista oferte...\n");
    }
    else{
        printf("Oferte:\n");
        for (int i = 0; i < size(l); i++) {
            Offer * o = get(l, i);
            printf("Tip: %s  Destinatie: %s  Data: %s  Pret: %.3f\n", o->type, o->destination, o->date, o->price);
        }
    }
    printf("\n");
}
void run()
{
    printf("Agentie de turism\n");
    printf("\n");
    printf("1.Adaugare\n2.Modificare\n3.Stergere\n4.Sortare\n5.Filtrare\n6.Afisare\n7.Iesire\n");
    printf("\n");
    List *l = create_list();
    add_offer(l, "munte", "austria", "02/03/2023", 234);
    add_offer(l, "munte", "italia", "02/02/2024", 2304);
    add_offer(l, "mare", "grecia", "02/08/2024", 134);
    add_offer(l, "city break", "paris", "16/05/2023", 1004);

    while(true)
    {
        int option = 0;
        printf("Introduceti optiunea: \n");
        scanf("%d", &option);
        printf("\n");
        getchar();
        if (option == 1)
        {
            addUI(l);
        }

        else if (option == 2)
        {
            modifyUI(l);
        }

        else if (option == 3)
        {
            deleteUI(l);
        }

        else if (option == 4)
        {
            sortUI(l);
        }

        else if (option == 5)
        {
            filterUI(l);
        }
        else if (option == 6)
        {
            printUI(l);
        }
        else if (option == 7)
        {
            printf("EXIT...");
            sleep(1);
            destroy_list(l);
            exit(0);
        }

        else
        {
            printf("Optiune invalida..\n");
        }

    }
}

void test_final()
{
    testAddOffer();
    testCreateDestroy();
    testCreateList();
    testIterateList();
    testDelete();
    testDeleteOffer();
    testModifyOffer();
    testValidate();
    testValidDate();
    testModify();
    testCopyList();
    testMod();
    testSort();
    testFilterDestination();
    testFilterType();
    testFilterPrice();
    testOnlyLetters();
}

int main() {
    run();
    //test_final();
    return 0;
}
