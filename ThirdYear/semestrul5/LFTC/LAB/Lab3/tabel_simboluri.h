#ifndef TABEL_SIMBOLURI_H
#define TABEL_SIMBOLURI_H

#define MAX_SIMBOLURI 1000

typedef struct {
    char **simboluri;          
    int dimensiune;             
    int capacitate;            
} TabelSimboluriOrdonat;

TabelSimboluriOrdonat* creeaza_tabel();
int pozitie(TabelSimboluriOrdonat *tabel, const char *simbol);
void scrie_tabel(TabelSimboluriOrdonat *tabel, const char *nume_fisier);
void distruge_tabel(TabelSimboluriOrdonat *tabel);

#endif