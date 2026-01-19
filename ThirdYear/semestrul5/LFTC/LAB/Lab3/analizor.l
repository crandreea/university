%{
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "tabel_simboluri.h"
#include "atomi.h"

extern int yylineno;
extern FILE *yyin;

TabelSimboluriOrdonat *ts_id;
TabelSimboluriOrdonat *ts_const;
AtomiMap *atomi_map;
FILE *fip_file;

int cod_ID = 100;
int cod_CONST = 101;
int cod_virgula = 8;

void adauga_fip(const char *atom, int cod, int poz);
void eroare_lexicala(const char *atom);
%}

%option yylineno
%option noyywrap

CIFRA       [0-9]
LITERA      [A-Za-z_]
ID          {LITERA}({LITERA}|{CIFRA})*
CONST       {CIFRA}+
WHITESPACE  [ \t\r]+
NEWLINE     \n

%%

"<-"        { adauga_fip(yytext, get_cod_atom(atomi_map, yytext), 0); }
"=="        { adauga_fip(yytext, get_cod_atom(atomi_map, yytext), 0); }
"!="        { adauga_fip(yytext, get_cod_atom(atomi_map, yytext), 0); }
"<="        { adauga_fip(yytext, get_cod_atom(atomi_map, yytext), 0); }
">="        { adauga_fip(yytext, get_cod_atom(atomi_map, yytext), 0); }
">>"        { adauga_fip(yytext, get_cod_atom(atomi_map, yytext), 0); }
"<<"        { adauga_fip(yytext, get_cod_atom(atomi_map, yytext), 0); }

"{"         { adauga_fip(yytext, get_cod_atom(atomi_map, yytext), 0); }
"}"         { adauga_fip(yytext, get_cod_atom(atomi_map, yytext), 0); }
"("         { adauga_fip(yytext, get_cod_atom(atomi_map, yytext), 0); }
")"         { adauga_fip(yytext, get_cod_atom(atomi_map, yytext), 0); }
";"         { adauga_fip(yytext, get_cod_atom(atomi_map, yytext), 0); }
","         { adauga_fip(yytext, cod_virgula, 0); }
"+"         { adauga_fip(yytext, get_cod_atom(atomi_map, yytext), 0); }
"-"         { adauga_fip(yytext, get_cod_atom(atomi_map, yytext), 0); }
"*"         { adauga_fip(yytext, get_cod_atom(atomi_map, yytext), 0); }
"/"         { adauga_fip(yytext, get_cod_atom(atomi_map, yytext), 0); }
":"         { adauga_fip(yytext, get_cod_atom(atomi_map, yytext), 0); }
"%"         { adauga_fip(yytext, get_cod_atom(atomi_map, yytext), 0); }
"<"         { adauga_fip(yytext, get_cod_atom(atomi_map, yytext), 0); }
">"         { adauga_fip(yytext, get_cod_atom(atomi_map, yytext), 0); }
"#"         { adauga_fip(yytext, get_cod_atom(atomi_map, yytext), 0); }

{CONST}     { 
    int poz = pozitie(ts_const, yytext);
    adauga_fip(yytext, cod_CONST, poz);
}

{ID}        {
    int cod = get_cod_atom(atomi_map, yytext);
    if (cod != -1) {
        adauga_fip(yytext, cod, 0);
    } else {
        int poz = pozitie(ts_id, yytext);
        adauga_fip(yytext, cod_ID, poz);
    }
}

{WHITESPACE} { /* Ignora spatiile */ }
{NEWLINE}    { /* Ignora newline-urile */ }

.           { eroare_lexicala(yytext); }

%%

void adauga_fip(const char *atom, int cod, int poz) {
    fprintf(fip_file, "%s, %d, %d\n", atom, cod, poz);
}

void eroare_lexicala(const char *atom) {
    printf("Eroare lexicala pe linia %d. Atom necunoscut: %s\n", yylineno, atom);
}