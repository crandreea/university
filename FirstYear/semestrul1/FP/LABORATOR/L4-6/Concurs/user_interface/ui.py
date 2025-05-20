from user_interface.menu import *
from domain import get_contest_number, get_score
from functionality import *

def read_valid_number(message: str):
    """
    Returneaza o valoare intreaga citita de la consola
    :param message: string
    :return: valoarea intreaga a valorii citite
    """
    while True:
        try:
            user_input = input(message).strip()
            user_input = int(user_input)
            return user_input
        except ValueError:
            print("Valoare invalida. Introduceti din nou...")
             

def read_valid_contest_number(competitors, message: str):
    """
    Verifica daca participantul cu numarul introdus de la tastatura exista deja in lista
    :param competitors: lista de participanti
    :param message: string
    :return: valoarea intreaga a valorii citite
    """
    while True:
        number = read_valid_number(message)
        sw = False
        for competitor in competitors:
            if get_contest_number(competitor) == number:
                sw = True
        if sw == True and len(competitors) >0:
            print(f"Participantul cu numarul {number} a fost deja introdus...")
        else:
            return int(number)

def valid_contest_number(competitors, message: str):
    """
    Verifica daca participantul cu numarul introdus de la tastatura nu exista in lista
    :param competitors: lista de participanti
    :param message: string
    :return: valoarea intreaga a valorii citite
    """
    while True:
        number = read_valid_number(message)
        sw = False
        for competitor in competitors:
            if get_contest_number(competitor) == number:
                sw = True
        if sw == False:
            print(f"Partcipantul cu numarul {number} nu exista...")
        else:
            return int(number)

def read_valid_score(message: str):
    """
    Valideaza scorul participantilor
    :param message: string
    :return: valoare intreaga
    """
    score = input(message).strip()
    if not score.isdigit():
        print("Eroare: Valoarea introdusa trebuie sa fie un numar natural mai mic sau egal cu 10...")
        return read_valid_score(message)
    else:
        score = int(score)
        if score > 10 or score < 1:
            print("Eroare: Valoarea introduse trebuie sa fie cel putin egala cu 1 si mai mica sau egala cu 10...")
            return read_valid_score(message)
    return int(score)

def option_validation(message: str):
   """
   Valideaza optiunea introdusa
   :param message: string
   :return: nothing
   """
   while True:
        try:
            option = input(message).strip()
            if not option.isdigit():
                raise ValueError("Eroare: Valoarea introdusa trebuie sa fie un numar natural...")
            return int(option)
        except ValueError as error:
            print(error)


def read_competitor_data(competitors):
    """
    Citeste datele unui participant si le returneaza
    :param competitors: lista de participanti
    :return: datele participnatului (numarul de concurs si media scorurilor la cele 10 probleme)
    """
    nr = read_valid_contest_number(competitors, "Introduceti numarul participantului: ")
    cnt = 1
    sum = 0
    while cnt <=10:
        score = read_valid_score(f"Introduceti scorul pentru problema {cnt}: ") 
        sum += int(score)
        cnt += 1

    return nr, sum//10

def print_competitors(competitor):
    print(f"Participant: {get_contest_number(competitor)} - Scorul: {get_score(competitor)}")

def run_add(competitors):
    undo_list = []
    while True:
        add_menu()
        command = option_validation("Alegeti optiunea: ")

        if command == 0:
            for competitor in competitors:
                print_competitors(competitor)

        elif command == 1:
            register_for_undo(undo_list, competitors)
            contest_number, score = read_competitor_data(competitors)
            add_new(competitors, contest_number, score)
            print("Participantul a fost adaugat cu succes!")

        elif command == 2:
            register_for_undo(undo_list, competitors)
            contest_number = valid_contest_number(competitors, "Introduceti numarul participantului: ")
            cnt = 1
            sum = 0
            while cnt <= 10:
                score = read_valid_score(f"Introduceti noul scor pentru problema {cnt}: ")
                sum += score
                cnt+= 1
            add_score(competitors, contest_number, sum//10)

        elif command == 3:
            main_menu()
            break

        elif command == 4:
            competitors = pop_undo_list(undo_list)
            print("Undo a fost realizat!") 

        else:
            print("Valoare invalida...")

def run_modify(competitors):
    undo_list = []
    while True:
        modify_menu()
        command = option_validation("Alegeti optiunea: ")

        if command == 0:
            for competitor in competitors:
                print_competitors(competitor)

        elif command == 1:
            register_for_undo(undo_list, competitors)
            contest_number = valid_contest_number(competitors, "Introduceti numarul participantului: ")
            delete_score(competitors, contest_number)
            print("Scorul a fost sters cu succes!")

        elif command == 2:
            register_for_undo(undo_list, competitors)
            contest_number_start = valid_contest_number(competitors, "Introduceti numarul primului participant: ")
            contest_number_finish = valid_contest_number(competitors, "Introduceti numarul ultimului participant: ")
            delete_score_subset(competitors, contest_number_start, contest_number_finish)
            print("Scorurile au fost sterse cu succes!")

        elif command == 3:
            register_for_undo(undo_list, competitors)
            contest_number = valid_contest_number(competitors, "Introduceti numarul participantului: ")
            cnt = 1
            sum = 0
            while cnt <= 10:
                score = read_valid_score(f"Introduceti noul scor pentru problema {cnt}: ")
                sum += score
                cnt+= 1
            replacement(competitors, contest_number, sum//10)
            print("Scorul a fost modificat cu succes!")

        elif command == 4:
            main_menu()
            break

        elif command == 5:
            competitors = pop_undo_list(undo_list)
            print("Undo a fost realizat!") 

        else:
            print("Valoare invalida...")

def run_print(competitors):
    while True:
        print_menu()
        command = option_validation("Alegeti optiunea: ")

        if command == 0:
            for competitor in competitors:
                print_competitors(competitor)

        elif command == 1:
            score = read_valid_score("Introduceti scorul de filtrare: ")
            result = []
            result = print_score_less(competitors, score)
            if result == []:
                print("Nu exista participanti care sa corespunda cerintei...")
            else:
                print("Participantii care corespund cerintei sunt: ")
                for competitor in result:
                    print_competitors(competitor)

        elif command == 2:
            result = []
            result = print_score_decrease(competitors)
            for competitor in result:
                print_competitors(competitor)

        elif command == 3:
            score = read_valid_score("Introduceti scorul de filtrare: ")
            result = []
            result = print_score_more_decrease(competitors, score)
            if result == []:
                print("Nu exista participanti care sa corespunda cerintei...")
            else:
                print("Participantii care corespund cerintei sunt: ")
                for competitor in result:
                    print_competitors(competitor)

        elif command == 4:
            main_menu()
            break
        else:
            print("Valoare invalida...")

def run_subset(competitors):
      while True:
        subset_menu()
        command = option_validation("Alegeti optiunea: ")

        if command == 0:
            for competitor in competitors:
                print_competitors(competitor)

        elif command == 1:
            contest_number_start = read_valid_contest_number(competitors, "Introduceti numarul primului participant: ")
            contest_number_finish = read_valid_contest_number(competitors, "Introduceti numarul ultimului participant: ")
            result = subset_range(competitors, contest_number_start, contest_number_finish)
            print(f"Media scorurilor este: {result}")

        elif command == 2:
            contest_number_start = read_valid_contest_number(competitors, "Introduceti numarul primului participant: ")
            contest_number_finish = read_valid_contest_number(competitors, "Introduceti numarul ultimului participant: ")
            result = subset_minim(competitors, contest_number_start, contest_number_finish)
            print(f"Participantul cu scorul minim {get_score(result)} este {get_contest_number(result)}")

        elif command == 3:
            result = []
            contest_number_start = read_valid_contest_number(competitors, "Introduceti numarul primului participant: ")
            contest_number_finish = read_valid_contest_number(competitors, "Introduceti numarul ultimului participant: ")
            result = subset_multiple(competitors, contest_number_start, contest_number_finish)
            print("Participantii care corespund cerintei sunt: ")
            for competitor in result:
                    print_competitors(competitor)

        elif command == 4:
            main_menu()
            break
        else:
            print("Valoare invalida...")

def run_filter(competitors):
    while True:
        add_menu()
        command = option_validation("Alegeti optiunea: ")
        if command == 0:
            for competitor in competitors:
                print_competitors(competitor)

        elif command == 1:
            score = read_valid_score("Introduceti scorul de filtrare: ")
            result = []
            result = filter_by_score_less(competitors, score)
            print("Lista de participanti a fost filtrata!")
            for competitor in result:
                print_competitors(competitor)

        elif command == 2:
            number = read_valid_number("Introduceti numarul: ")
            result = []
            result = filter_by_score_multiple(competitors, number)
            print("Lista de participanti a fost filtrata!")
            for competitor in result:
                print_competitors(competitor)

        elif command == 3:
            main_menu()
            break
        else:
            print("Valoare invalida...")

def run():
    competitors = []
    undo_list = []
    lenght = len(competitors)
    main_menu()
    while True:
        try:
            option = option_validation("Alegeti optiunea: ")
            if option == 0:
                if lenght == 0: 
                    print("Nu exista niciun participant...")
                else:
                    for competitor in competitors:
                        print_competitors(competitor)
            elif option == 1:
                run_add(competitors)
            elif option == 2:
                run_modify(competitors)
            elif option == 3:
                run_print(competitors)
            elif option == 4:
                run_subset(competitors)
            elif option ==5:
                run_filter(competitors)
            elif option == 6:
                print("Exit...")
                exit()
            else: print("Valoare invalida...")

        except ValueError as e:
            print(e)