#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "tabel_simboluri.h"

TabelSimboluriOrdonat* creeaza_tabel() {
    TabelSimboluriOrdonat *tabel = (TabelSimboluriOrdonat*)malloc(sizeof(TabelSimboluriOrdonat));
    tabel->capacitate = 100;
    tabel->dimensiune = 0;
    tabel->simboluri = (char**)malloc(tabel->capacitate * sizeof(char*));
    return tabel;
}

int cauta_pozitie_insertie(TabelSimboluriOrdonat *tabel, const char *simbol) {
    int stanga = 0;
    int dreapta = tabel->dimensiune - 1;
    
    while (stanga <= dreapta) {
        int mijloc = (stanga + dreapta) / 2;
        int cmp = strcmp(simbol, tabel->simboluri[mijloc]);
        
        if (cmp == 0) {
            return mijloc; 
        } else if (cmp < 0) {
            dreapta = mijloc - 1;
        } else {
            stanga = mijloc + 1;
        }
    }
    
    return stanga; 
}

int cauta_simbol(TabelSimboluriOrdonat *tabel, const char *simbol) {
    int stanga = 0;
    int dreapta = tabel->dimensiune - 1;
    
    while (stanga <= dreapta) {
        int mijloc = (stanga + dreapta) / 2;
        int cmp = strcmp(simbol, tabel->simboluri[mijloc]);
        
        if (cmp == 0) {
            return mijloc;
        } else if (cmp < 0) {
            dreapta = mijloc - 1;
        } else {
            stanga = mijloc + 1;
        }
    }
    
    return -1; 
}

void insereaza_simbol(TabelSimboluriOrdonat *tabel, const char *simbol, int pozitie_insertie) {
    if (tabel->dimensiune >= tabel->capacitate) {
        tabel->capacitate *= 2;
        tabel->simboluri = (char**)realloc(tabel->simboluri, tabel->capacitate * sizeof(char*));
    }
    
    for (int i = tabel->dimensiune; i > pozitie_insertie; i--) {
        tabel->simboluri[i] = tabel->simboluri[i - 1];
    }
    
    tabel->simboluri[pozitie_insertie] = strdup(simbol);
    tabel->dimensiune++;
}

int pozitie(TabelSimboluriOrdonat *tabel, const char *simbol) {
    int poz = cauta_simbol(tabel, simbol);
    
    if (poz != -1) {
        return poz; 
    }
    
    int poz_insertie = cauta_pozitie_insertie(tabel, simbol);
    
    insereaza_simbol(tabel, simbol, poz_insertie);
    
    return poz_insertie;
}

void scrie_tabel(TabelSimboluriOrdonat *tabel, const char *nume_fisier) {
    FILE *f = fopen(nume_fisier, "w");
    if (f == NULL) {
        perror("Eroare la deschiderea fisierului");
        return;
    }
    
    for (int i = 0; i < tabel->dimensiune; i++) {
        fprintf(f, "%d,%s\n", i, tabel->simboluri[i]);
    }
    
    fclose(f);
}

void distruge_tabel(TabelSimboluriOrdonat *tabel) {
    for (int i = 0; i < tabel->dimensiune; i++) {
        free(tabel->simboluri[i]);
    }
    free(tabel->simboluri);
    free(tabel);
}