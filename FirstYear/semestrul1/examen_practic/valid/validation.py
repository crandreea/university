from domain.spectacol import *
class Validator:
    def __init__(self) -> None:
        pass

    def intreg(self, number : int):
        """
        Verifica daca un numar este intreg
        :param number: numarul
        :return: bool 
        """
        try:
            number = int(number)
            if number >= 0:
                return True
        except ValueError:
            return False
        
    def spectacol_valid(self, spectacol : Spectacol):
        """
        Valideaza un spectacol
        :param spectacol: entitatea spectacol
        """
        erori = ""
        if spectacol.get_artist() == "":
            erori += "Numele artistului trebuie introdus\n"

        if spectacol.get_titlu() == "":
            erori += "Titlul spectacolului trebuie introdus\n"

        if self.intreg(spectacol.get_durata()) == False:
            erori += "Durata trebuie sa fie un numar intreg pozitiv...\n"

        if spectacol.get_gen() != "Comedie" and spectacol.get_gen() != "Concert" and spectacol.get_gen() != "Balet" and spectacol.get_gen() != "Altele":
            erori += "Genul nu se incadreaza in urmatoarele categorii: Comedie/Concert/Balet/Altele ...\n"

        if len(erori) > 0:
            erori = erori[:-1]
            raise Exception(erori)


        