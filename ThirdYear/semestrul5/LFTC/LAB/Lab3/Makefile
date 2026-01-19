CC = gcc
CFLAGS = -Wall -g
LEX = flex

all: analizor

analizor: lex.yy.c tabel_simboluri.o atomi.o main.o
	$(CC) $(CFLAGS) -o analizor lex.yy.c tabel_simboluri.o atomi.o main.o 

lex.yy.c: analizor.l
	$(LEX) analizor.l

tabel_simboluri.o: tabel_simboluri.c tabel_simboluri.h
	$(CC) $(CFLAGS) -c tabel_simboluri.c

atomi.o: atomi.c atomi.h
	$(CC) $(CFLAGS) -c atomi.c

main.o: main.c
	$(CC) $(CFLAGS) -c main.c

clean:
	rm -f *.o lex.yy.c analizor FIP.txt TS_ID.txt TS_CONST.txt

run: analizor
	./analizor