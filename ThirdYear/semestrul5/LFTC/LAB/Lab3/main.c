#include <stdio.h>
#include <stdlib.h>
#include "tabel_simboluri.h"
#include "atomi.h"

extern FILE *yyin;
extern FILE *fip_file;
extern TabelSimboluriOrdonat *ts_id;
extern TabelSimboluriOrdonat *ts_const;
extern AtomiMap *atomi_map;

int yylex();

int main() {
    atomi_map = incarca_atomi("atom.csv");
    if (atomi_map == NULL) {
        return 1;
    }
    
    ts_id = creeaza_tabel();
    ts_const = creeaza_tabel();
    
    yyin = fopen("inputProgramEroare.txt", "r");
    if (yyin == NULL) {
        perror("Eroare la deschiderea fisierului input");
        return 1;
    }
    
    fip_file = fopen("FIP.txt", "w");
    if (fip_file == NULL) {
        perror("Eroare la deschiderea fisierului FIP");
        return 1;
    }
    
    yylex();
    
    fclose(yyin);
    fclose(fip_file);
    
    scrie_tabel(ts_id, "TS_ID.txt");
    scrie_tabel(ts_const, "TS_CONST.txt");
    
    distruge_tabel(ts_id);
    distruge_tabel(ts_const);
    distruge_atomi(atomi_map);
    
    return 0;
}