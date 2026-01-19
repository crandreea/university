%{
#include "mlp.tab.h"
#include <stdlib.h>
#include <string.h>

int line_no = 1;
%}

%option noyywrap

%%

"#"             { yylloc.first_line = line_no; return HASH; }
"include"       { yylloc.first_line = line_no; return INCLUDE; }
"iostream"      { yylloc.first_line = line_no; return IOSTREAM; }
"using"         { yylloc.first_line = line_no; return USING; }
"namespace"     { yylloc.first_line = line_no; return NAMESPACE; }
"std"           { yylloc.first_line = line_no; return STD; }

";"             { yylloc.first_line = line_no; return SEMI; }
","             { yylloc.first_line = line_no; return COMMA; }
"{"             { yylloc.first_line = line_no; return LBRACE; }
"}"             { yylloc.first_line = line_no; return RBRACE; }
"("             { yylloc.first_line = line_no; return LPAREN; }
")"             { yylloc.first_line = line_no; return RPAREN; }

"<<"            { yylloc.first_line = line_no; return SHL; }
">>"            { yylloc.first_line = line_no; return SHR; }
"<-"            { yylloc.first_line = line_no; return ASSIGN; }

"=="            { yylloc.first_line = line_no; return EQ; }
"!="            { yylloc.first_line = line_no; return NEQ; }
"<="            { yylloc.first_line = line_no; return LEQ; }
">="            { yylloc.first_line = line_no; return GEQ; }
"<"             { yylloc.first_line = line_no; return LT; }
">"             { yylloc.first_line = line_no; return GT; }

"+"             { yylloc.first_line = line_no; return PLUS; }
"-"             { yylloc.first_line = line_no; return MINUS; }
"*"             { yylloc.first_line = line_no; return MUL; }
"%"             { yylloc.first_line = line_no; return MOD; }

"if"            { yylloc.first_line = line_no; return IF; }
"loop"          { yylloc.first_line = line_no; return LOOP; }
"read"          { yylloc.first_line = line_no; return READ; }
"write"         { yylloc.first_line = line_no; return WRITE; }

"int"           { yylloc.first_line = line_no; return INT; }
"float"         { yylloc.first_line = line_no; return FLOAT; }
"string"        { yylloc.first_line = line_no; return STRING; }

[0-9]+          {
                    yylloc.first_line = line_no;
                    yylval.ival = atoi(yytext);
                    return CONSTVAL;
                }

[a-zA-Z_][a-zA-Z0-9_]* {
                    yylloc.first_line = line_no;
                    yylval.sval = strdup(yytext);
                    return ID;
                }

[ \t\r]+        ;

\n              { line_no++; yylloc.first_line = line_no; }

.               {
                    fprintf(stderr,
                        "Caracter necunoscut '%s' la linia %d\n",
                        yytext, line_no);
                }

%%
