
import textwrap

def main_menu():
    print(textwrap.dedent("""
    Concurs - gestionare concurenti
    
    1. Adauga un scor la un participant
    2. Modifica scorul 
    3. Tipareste lista de participanti
    4. Realizeaza operatii pe un subset de participnati
    5. Filtreaza particpantii
    6. Undo 
                          
    """))

def add_menu():
   print(textwrap.dedent("""
    Adauga un scor la un participant
                         
    1. Adauga scor pentru un participant nou
    2. Insereaza scor pentru un participant 
                          
    """))

def modify_menu():
    print(textwrap.dedent("""
    Modificare scor

    1. Sterge scorul pentru un participant dat
    2. Sterge scorul pentru pentru un interval de participanti
    3. Inlocuieste scorul de la un participant 
                          
    """))

def print_menu():
    print(textwrap.dedent("""
    Tipareste lista de participanti
                         
    1. Tipareste lista de participanti care au scorul mai mic decat un scor dat
    2. Tipareste participantii ordonat dupa scor
    3. Tipareste participantii cu scor mai mare decat un scor dat, ordonat dupa scor
                          
    """))

def subset_menu():
    print(textwrap.dedent("""
    Operatii pe un subset de participanti
                          
    1. Calculeaza media scorurilor pentru un interval dat
    2. Calculeaza scorul minim pentru un interval de participanti dat
    3. Tipareste participantii dintr-un interval dat care au scorul multiplu de 10
                          
    """))

def filter_menu():
    print(textwrap.dedent("""
    Filtrare
                         
    1. Filtrare participanti care au scorul multiplu unui numar dat
    2. Filtrare participanti care au scorul mai mic decat un scor dat 
                          
    """))


