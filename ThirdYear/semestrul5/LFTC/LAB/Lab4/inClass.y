%{
#include <stdio.h>
int yylex();
int parlevel = 0; 
int yyerror(const char *s) {
    fprintf(stderr,"Eroare.\n");
    return 0;
}

%}

%token NUMBER MUL LP RP

%%

expr:
      expr MUL expr
    | LP expr RP
    | NUMBER
    ;

%%

int main() {
    yyparse();

    if (parlevel == 0)
        printf("corect\n");
    else
        printf("NU se inchid corect\n");

    return 0;
}

