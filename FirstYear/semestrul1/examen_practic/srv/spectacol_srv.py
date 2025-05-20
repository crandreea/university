from domain.spectacol import *
from repo.spectacol_repo import *
from valid.validation import *
import random
import string

class SpectacolSrv():
    def __init__(self, s_repo : SpectacolRepo, s_valid : Validator) -> None:
        self.__s_repo = s_repo
        self.__s_valid = s_valid

    def get_spectacole(self):
        self.__s_repo.get_all()

    def add(self, new_entity):
        """
        Adaugare noua entitate
        :param new_entity: noua entitate
        :return: nothig
        """
        self.__s_valid.spectacol_valid(new_entity)
        self.__s_repo.add_spectacol(new_entity)

    def modify(self, titlu, artist, gen, durata):
        """
        Modifica o entitate
        :param titlu: titlul existent
        :param artist: nume artist existent
        :param gen: noul gen
        :param durata: noua durata
        """
        new_entity = Spectacol(titlu, artist, gen, durata)
        self.__s_valid.spectacol_valid(new_entity)
        self.__s_repo.modify_spectacol(new_entity)

    def random(self, cnt):
        """
        Genreaza random un numar de spectacole
        :param cnt: numarul de spectacole random generate
        :retunr: lista cu spectcaolele noi generate
        """
        new_spectacole = []
        while cnt>0:
            genuri = ["Concert","Comedie","Balet","Altele"]
            lungime = random.randint(9, 12)
            durata = random.randint(1,12637382)
            titlu = ''.join(random.choice(string.ascii_letters) for _ in range(lungime))
            artist = ''.join(random.choice(string.ascii_letters) for _ in range(lungime))
            gen = ''.join(random.choice(genuri))
            spectacol = Spectacol(titlu, artist, gen, durata)
            self.__s_repo.add_spectacol(spectacol)
            new_spectacole.append(spectacol)
            cnt = cnt - 1

    

class SpectacolExport():
    def __init__(self, s_repo : SpectacolRepo, s_valid : Validator) -> None:
        self.__s_repo = s_repo
        self.__s_valid = s_valid

    def get_spectacole(self):
        self.__s_repo.get_all()

    def add(self, new_entity):
        self.__s_valid.spectacol_valid(new_entity)
        self.__s_repo.add_spectacol(new_entity)


    