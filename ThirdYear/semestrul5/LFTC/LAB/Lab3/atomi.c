#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "atomi.h"

AtomiMap* incarca_atomi(const char *fisier_csv) {
    FILE *f = fopen(fisier_csv, "r");
    if (f == NULL) {
        perror("Eroare la deschiderea fisierului atom.csv");
        return NULL;
    }
    
    AtomiMap *map = (AtomiMap*)malloc(sizeof(AtomiMap));
    map->dimensiune = 0;
    map->atomi = NULL;
    map->coduri = NULL;
    
    char linie[256];
    fgets(linie, sizeof(linie), f);
    
    int capacitate = 10;
    map->atomi = (char**)malloc(capacitate * sizeof(char*));
    map->coduri = (int*)malloc(capacitate * sizeof(int));
    
    while (fgets(linie, sizeof(linie), f)) {
        char atom[128];
        int cod;
        
        if (sscanf(linie, "%d,%127[^\n]", &cod, atom) == 2) {
            if (map->dimensiune >= capacitate) {
                capacitate *= 2;
                map->atomi = (char**)realloc(map->atomi, capacitate * sizeof(char*));
                map->coduri = (int*)realloc(map->coduri, capacitate * sizeof(int));
            }
            
            map->atomi[map->dimensiune] = strdup(atom);
            map->coduri[map->dimensiune] = cod;
            map->dimensiune++;
        }
    }
    
    fclose(f);
    return map;
}

int get_cod_atom(AtomiMap *map, const char *atom) {
    for (int i = 0; i < map->dimensiune; i++) {
        if (strcmp(map->atomi[i], atom) == 0) {
            return map->coduri[i];
        }
    }
    return -1;
}

void distruge_atomi(AtomiMap *map) {
    for (int i = 0; i < map->dimensiune; i++) {
        free(map->atomi[i]);
    }
    free(map->atomi);
    free(map->coduri);
    free(map);
}