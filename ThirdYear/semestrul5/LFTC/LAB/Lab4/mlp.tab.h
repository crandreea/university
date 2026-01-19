/* A Bison parser, made by GNU Bison 2.3.  */

/* Skeleton interface for Bison's Yacc-like parsers in C

   Copyright (C) 1984, 1989, 1990, 2000, 2001, 2002, 2003, 2004, 2005, 2006
   Free Software Foundation, Inc.

   This program is free software; you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation; either version 2, or (at your option)
   any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program; if not, write to the Free Software
   Foundation, Inc., 51 Franklin Street, Fifth Floor,
   Boston, MA 02110-1301, USA.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.

   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */

/* Tokens.  */
#ifndef YYTOKENTYPE
# define YYTOKENTYPE
   /* Put the tokens into the symbol table, so that GDB and other debuggers
      know about them.  */
   enum yytokentype {
     HASH = 258,
     INCLUDE = 259,
     IOSTREAM = 260,
     USING = 261,
     NAMESPACE = 262,
     STD = 263,
     SEMI = 264,
     COMMA = 265,
     LBRACE = 266,
     RBRACE = 267,
     LPAREN = 268,
     RPAREN = 269,
     SHL = 270,
     SHR = 271,
     ASSIGN = 272,
     PLUS = 273,
     MINUS = 274,
     MUL = 275,
     MOD = 276,
     EQ = 277,
     NEQ = 278,
     LT = 279,
     GT = 280,
     LEQ = 281,
     GEQ = 282,
     IF = 283,
     LOOP = 284,
     READ = 285,
     WRITE = 286,
     INT = 287,
     FLOAT = 288,
     STRING = 289,
     ID = 290,
     CONSTVAL = 291
   };
#endif
/* Tokens.  */
#define HASH 258
#define INCLUDE 259
#define IOSTREAM 260
#define USING 261
#define NAMESPACE 262
#define STD 263
#define SEMI 264
#define COMMA 265
#define LBRACE 266
#define RBRACE 267
#define LPAREN 268
#define RPAREN 269
#define SHL 270
#define SHR 271
#define ASSIGN 272
#define PLUS 273
#define MINUS 274
#define MUL 275
#define MOD 276
#define EQ 277
#define NEQ 278
#define LT 279
#define GT 280
#define LEQ 281
#define GEQ 282
#define IF 283
#define LOOP 284
#define READ 285
#define WRITE 286
#define INT 287
#define FLOAT 288
#define STRING 289
#define ID 290
#define CONSTVAL 291




#if ! defined YYSTYPE && ! defined YYSTYPE_IS_DECLARED
typedef union YYSTYPE
#line 13 "mlp.y"
{
    int ival;
    char* sval;
}
/* Line 1529 of yacc.c.  */
#line 126 "mlp.tab.h"
	YYSTYPE;
# define yystype YYSTYPE /* obsolescent; will be withdrawn */
# define YYSTYPE_IS_DECLARED 1
# define YYSTYPE_IS_TRIVIAL 1
#endif

extern YYSTYPE yylval;

#if ! defined YYLTYPE && ! defined YYLTYPE_IS_DECLARED
typedef struct YYLTYPE
{
  int first_line;
  int first_column;
  int last_line;
  int last_column;
} YYLTYPE;
# define yyltype YYLTYPE /* obsolescent; will be withdrawn */
# define YYLTYPE_IS_DECLARED 1
# define YYLTYPE_IS_TRIVIAL 1
#endif

extern YYLTYPE yylloc;
