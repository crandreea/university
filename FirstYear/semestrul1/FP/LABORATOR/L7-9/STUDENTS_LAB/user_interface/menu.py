class MENU:
    def main_menu():
            print("""
            Gestiune laboratoare studenți
            
            1 - Optiuni pentru student
            2 - Optiuni pentru problema    
            3 - Notare laborator          
            4 - Statistici      
            5 - Exit
    
            """)

    def student_menu():
            print("""
                0 - afisare studenti
                1 - adauga student
                2 - sterge student
                3 - modifica nume student
                4 - modifica grupa student
                5 - cauta student
                6 - exit
                7 - intoarcere meniu principal
                8 - random
            """)
    
    def laboratory_menu():
           print("""
                0 - afisare probleme
                1 - adauga problemă
                2 - sterge problemă
                3 - modifica descriere problema
                4 - modifica deadline problema
                5 - cauta problema
                6 - exit
                7 - intoarcere meniu principal
                 """)
           
    def notes_menu():
           print("""
                 0 - afisare note student
                 1 - notare problema
                 2 - intoarcere meniu principal
                 3 - exit 
                 """)
           
    def raport_menu():
           print("""
                1 - afisare studenți și notele lor la o problema de laborator dat
                2 - afisare studenți cu media notelor de laborator mai mica decât 5
                3 - intoarcere meniu principal
                4 - exit
                 """)
            