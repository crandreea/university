%{
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

extern int yylex();
extern int line_no;
void yyerror(const char *s);

FILE *asm_file;
int label_count = 0;

char vars[100][50];
int var_count = 0;

void add_var(char* name) {
    for(int i=0; i<var_count; i++)
        if(strcmp(vars[i], name) == 0) return;
    strcpy(vars[var_count++], name);
}
%}

%union {
    int ival;
    char* sval;
}

%token HASH INCLUDE IOSTREAM USING NAMESPACE STD
%token SEMI COMMA LBRACE RBRACE LPAREN RPAREN
%token SHL SHR ASSIGN
%token PLUS MINUS MUL MOD
%token EQ NEQ LT GT LEQ GEQ
%token IF LOOP READ WRITE
%token INT FLOAT STRING
%token <sval> ID
%token <ival> CONSTVAL

%start program

%%

program
    : antet_program functie
    ;

antet_program
    : HASH INCLUDE LT IOSTREAM GT USING NAMESPACE STD SEMI {
        fprintf(asm_file, "section .data\n");
        fprintf(asm_file, "format_in  db \"%%d\", 0\n");
        fprintf(asm_file, "format_out db \"%%d\", 10, 0\n\n");
    }
    ;

functie
    : tip ID LPAREN lista_decl_fct RPAREN corp
    ;

lista_decl_fct : /* empty */ | lista_decl ;
lista_decl : tip ID | tip ID COMMA lista_decl ;

corp
    : LBRACE {
        fprintf(asm_file, "section .bss\n");
    }
    declaratii_block {
        for(int i=0; i<var_count; i++) {
            fprintf(asm_file, "v_%s resq 1\n", vars[i]);
        }

        fprintf(asm_file,
            "\nsection .text\n"
            "global _main\n"
            "extern _printf, _scanf\n\n"
            "_main:\n"
            "    push rbp\n"
            "    mov rbp, rsp\n"
        );
    }
    instruct_compusa RBRACE {
        fprintf(asm_file,
            "    mov rax, 0\n"
            "    leave\n"
            "    ret\n"
        );
    }
    ;

declaratii_block : /* empty */ | declarare declaratii_block ;
declarare : tip lista_ids SEMI ;
lista_ids : ID { add_var($1); }
          | ID COMMA lista_ids { add_var($1); }
          ;

instruct_compusa : instruct | instruct instruct_compusa ;

instruct
    : atribuire | citire | scriere | instruct_if | instruct_loop ;

atribuire
    : ID ASSIGN expr_aritmetica SEMI {
        fprintf(asm_file,
            "    pop rax\n"
            "    mov [rel v_%s], rax\n", $1);
    }
    ;

citire
    : READ SHR ID SEMI {
        fprintf(asm_file,
            "    lea rdi, [rel format_in]\n"
            "    lea rsi, [rel v_%s]\n"
            "    xor eax, eax\n"
            "    call _scanf\n", $3);
    }
    ;

scriere
    : WRITE SHL ID SEMI {
        fprintf(asm_file,
            "    lea rdi, [rel format_out]\n"
            "    mov rsi, [rel v_%s]\n"
            "    xor eax, eax\n"
            "    call _printf\n", $3);
    }
    ;

expr_aritmetica
    : expr_aritmetica PLUS termen {
        fprintf(asm_file,
            "    pop rbx\n"
            "    pop rax\n"
            "    add rax, rbx\n"
            "    push rax\n");
    }
    | expr_aritmetica MINUS termen {
        fprintf(asm_file,
            "    pop rbx\n"
            "    pop rax\n"
            "    sub rax, rbx\n"
            "    push rax\n");
    }
    | termen
    ;

termen
    : termen MUL factor {
        fprintf(asm_file,
            "    pop rbx\n"
            "    pop rax\n"
            "    imul rax, rbx\n"
            "    push rax\n");
    }
    | factor
    ;

factor
    : LPAREN expr_aritmetica RPAREN
    | ID { fprintf(asm_file, "    push qword [rel v_%s]\n", $1); }
    | CONSTVAL { fprintf(asm_file, "    push %d\n", $1); }
    ;

instruct_if
    : IF LPAREN conditie RPAREN LBRACE {
        label_count++;
        fprintf(asm_file,
            "    pop rax\n"
            "    cmp rax, 0\n"
            "    je label_end_%d\n", label_count);
    }
    instruct_compusa RBRACE {
        fprintf(asm_file, "label_end_%d:\n", label_count);
    }
    ;

instruct_loop
    : LOOP {
        label_count++;
        fprintf(asm_file, "label_start_%d:\n", label_count);
    }
    LPAREN conditie RPAREN LBRACE {
        fprintf(asm_file,
            "    pop rax\n"
            "    cmp rax, 0\n"
            "    je label_end_%d\n", label_count);
    }
    instruct_compusa RBRACE {
        fprintf(asm_file,
            "    jmp label_start_%d\n"
            "label_end_%d:\n", label_count, label_count);
    }
    ;

conditie
    : expr_aritmetica EQ expr_aritmetica {
        fprintf(asm_file,
            "    pop rbx\n"
            "    pop rax\n"
            "    cmp rax, rbx\n"
            "    sete al\n"
            "    movzx rax, al\n"
            "    push rax\n");
    }
    | expr_aritmetica LT expr_aritmetica {
        fprintf(asm_file,
            "    pop rbx\n"
            "    pop rax\n"
            "    cmp rax, rbx\n"
            "    setl al\n"
            "    movzx rax, al\n"
            "    push rax\n");
    }
    ;

tip : INT | FLOAT | STRING ;

%%

void yyerror(const char *s) {
    fprintf(stderr, "Eroare la linia %d: %s\n", line_no, s);
}

int main(void) {
    asm_file = fopen("output.asm", "w");
    yyparse();
    fclose(asm_file);
    return 0;
}
