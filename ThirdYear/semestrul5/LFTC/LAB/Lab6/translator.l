%{
#include "translator.tab.h"
#include <stdlib.h>
#include <string.h>

int line_no = 1;
%}

%option noyywrap

%%

"#"             { return HASH; }
"include"       { return INCLUDE; }
"iostream"      { return IOSTREAM; }
"using"         { return USING; }
"namespace"     { return NAMESPACE; }
"std"           { return STD; }

";"             { return SEMI; }
","             { return COMMA; }
"{"             { return LBRACE; }
"}"             { return RBRACE; }
"("             { return LPAREN; }
")"             { return RPAREN; }

"<<"            { return SHL; }
">>"            { return SHR; }
"<-"            { return ASSIGN; }

"=="            { return EQ; }
"!="            { return NEQ; }
"<="            { return LEQ; }
">="            { return GEQ; }
"<"             { return LT; }
">"             { return GT; }

"+"             { return PLUS; }
"-"             { return MINUS; }
"*"             { return MUL; }
"%"             { return MOD; }

"if"            { return IF; }
"loop"          { return LOOP; }
"read"          { return READ; }
"write"         { return WRITE; }

"int"           { return INT; }
"float"         { return FLOAT; }
"string"        { return STRING; }

[0-9]+          {
                    yylval.ival = atoi(yytext);
                    return CONSTVAL;
                }

[a-zA-Z_][a-zA-Z0-9_]* {
                    yylval.sval = strdup(yytext);
                    return ID;
                }

[ \t\r]+        ;
\n              { line_no++; }

.               { printf("Eroare lexicala la linia %d\n", line_no); }

%%