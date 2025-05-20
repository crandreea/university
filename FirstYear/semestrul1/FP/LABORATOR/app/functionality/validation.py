"""
def option_validation(minimum, maximum, option):
   
   Valideaza optiunea introdusa
   :param minimum: valoarea minima a optiunii
   :param maximum: valoarea maxima a optiunii
   :return: nothing
   
   while True:
        try:
            if not option.isdigit():
                raise ValueError("Eroare: Valoarea introdusa trebuie sa fie un numar natural")
            option = int(option)
            if option < minimum or option > maximum:
                raise ValueError("Eroare: Valoare introdusa nu este valida")
            break
        except ValueError as error:
            print(error)

def number_validation(number):
    
    Valideaza numarul introdus
    :return: nothing
    
    while True:
        try:
            if not number.isdigit():
                raise ValueError("Eroare: Valoarea introdusa trebuie sa fie un numar natural")
            number = int(number)
            break
        except ValueError as error:
            print(error)

def contest_validation(competitons_list, contest_number):
    while True:
        try:
            contest_number = int(contest_number)
            if 0> contest_number or contest_number > len(competitons_list):
                raise ValueError(f"Eroare: Participantul cu numarul {contest_number} nu exista")
            for competitor in competitons_list:
                if competitor[0] == contest_number:
                    raise ValueError(f"Eroare: Participantul cu numarul {contest_number} exista deja")
            break
        except ValueError as error:
            print(error)

"""""
