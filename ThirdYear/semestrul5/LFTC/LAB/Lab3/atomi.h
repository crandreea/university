#ifndef ATOMI_H
#define ATOMI_H

typedef struct {
    char **atomi;
    int *coduri;
    int dimensiune;
} AtomiMap;

AtomiMap* incarca_atomi(const char *fisier_csv);
int get_cod_atom(AtomiMap *map, const char *atom);
void distruge_atomi(AtomiMap *map);

#endif