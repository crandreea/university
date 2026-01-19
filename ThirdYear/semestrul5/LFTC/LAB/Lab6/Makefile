TRANSLATOR = translator
OUTPUT_ASM = output.asm
TARGET = program_final

all: $(TARGET)

$(TRANSLATOR): translator.l translator.y
	bison -d translator.y
	flex translator.l
	gcc -arch x86_64 translator.tab.c lex.yy.c -o $(TRANSLATOR)

$(TARGET): $(TRANSLATOR) test.txt
	./$(TRANSLATOR) < test.txt
	nasm -f macho64 output.asm -o output.o
	gcc -arch x86_64 output.o -o $(TARGET)

clean:
	rm -f lex.yy.c translator.tab.c translator.tab.h output.o $(TRANSLATOR) $(OUTPUT_ASM) $(TARGET)

run: $(TARGET)
	./$(TARGET)