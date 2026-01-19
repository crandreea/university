%{
#include "inClass.tab.h"
int yylex();
int yyerror(const char*);
extern int parlevel;
%}

%%

[0-9]+        { return NUMBER; }
"*"           { return MUL; }
"("           { parlevel++; return LP; }
")"           { parlevel--; return RP; }
[ \t\n]+      ;
.             ;

%%

int yywrap() { return 1; }
