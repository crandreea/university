%locations

%{
#include <stdio.h>
#include <stdlib.h>

extern int yylex();
extern int line_no;
int found_error = 0;
void yyerror(const char *s);
%}

%union {
    int ival;
    char* sval;
}

/* tokenii */
%token HASH INCLUDE IOSTREAM USING NAMESPACE STD
%token SEMI COMMA
%token LBRACE RBRACE LPAREN RPAREN
%token SHL SHR
%token ASSIGN
%token PLUS MINUS MUL MOD
%token EQ NEQ LT GT LEQ GEQ
%token IF LOOP READ WRITE
%token INT FLOAT STRING
%token <sval> ID
%token <ival> CONSTVAL


%left PLUS MINUS
%left MUL MOD
%left EQ NEQ LT GT LEQ GEQ

%start program

%%

program
    : antet_program functie
    ;

antet_program
    : HASH INCLUDE LT IOSTREAM GT USING NAMESPACE STD SEMI
    ;

functie
    : antet_functie corp
    ;

antet_functie
    : tip ID LPAREN RPAREN
    | tip ID LPAREN lista_decl RPAREN
    ;

declarare_fct
    : tip ID
    ;

lista_decl
    : declarare_fct COMMA lista_decl
    | declarare_fct
    ;

declarare
    : tip lista_decl_simpla SEMI
    | tip lista_decl_cu_atribuiri SEMI
    ;

lista_decl_simpla
    : ID
    | ID COMMA lista_decl_simpla
    ;

lista_decl_cu_atribuiri
    : decl_attr
    | decl_attr COMMA lista_decl_cu_atribuiri
    ;

decl_attr
    : ID ASSIGN expr_aritmetica
    ;

corp
    : LBRACE instruct_compusa RBRACE
    | LBRACE error RBRACE {
        found_error = 1;
          fprintf(stderr,
            "Eroare in bloc la linia %d.\n", @2.first_line);
          yyerrok;
      }
    ;

instruct_compusa
    : instruct instruct_compusa
    | instruct
    ;

instruct
    : declarare
    | atribuire
    | citire
    | scriere
    | instruct_if
    | instruct_while
    | error SEMI {
        found_error = 1;
          fprintf(stderr,
             "Eroare la linia %d: instructiune invalida.\n",
             @1.first_line);
          yyerrok;
      }
    ;

atribuire
    : ID ASSIGN expr_aritmetica SEMI
    ;

citire
    : READ SHR ID SEMI
    ;

scriere
    : WRITE SHL ID SEMI
    ;

instruct_if
    : IF LPAREN conditie RPAREN corp
    | IF LPAREN error RPAREN corp {
        found_error = 1;
          fprintf(stderr,
            "Eroare in conditia IF la linia %d.\n",
            @3.first_line);
          yyerrok;
      }
    ;

instruct_while
    : LOOP LPAREN conditie RPAREN corp
    | LOOP LPAREN error RPAREN corp {
        found_error = 1;
          fprintf(stderr,
            "Eroare in conditia LOOP la linia %d.\n",
            @3.first_line);
          yyerrok;
      }
    ;

conditie
    : expr_aritmetica op_relationale expr_aritmetica
    | expr_aritmetica
    ;

op_relationale
    : EQ
    | NEQ
    | LT
    | GT
    | LEQ
    | GEQ
    ;

tip
    : INT
    | FLOAT
    | STRING
    ;

expr_aritmetica
    : expr_aritmetica PLUS expr_aritmetica
    | expr_aritmetica MINUS expr_aritmetica
    | expr_aritmetica MUL expr_aritmetica
    | expr_aritmetica MOD expr_aritmetica
    | LPAREN expr_aritmetica RPAREN
    | ID
    | CONSTVAL
    ;

%%

void yyerror(const char *s) {
  
}

int main(void) {
    yyparse();
    if (found_error)
        printf("Program incorect sintactic.\n");
    else
        printf("Program corect sintactic.\n");
}

