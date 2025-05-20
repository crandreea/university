from domain.spectacol import *
class SpectacolRepo:
    def __init__(self) -> None:
        self._spectacole = []

    def get_all(self):
        """
        Returneaza toate spectacolele
        :return: list
        """
        return [x for x in self._spectacole]
    
    def add_spectacol(self, new_entity):
        """
        Adauga o noua entitate in lista
        :param new_entity: noua entitate 
        :return: nothing
        """
        for entity in self._spectacole:
            if entity == new_entity:
                raise Exception("Spectacolul a fost deja introdus...")

        self._spectacole.append(new_entity)

    def modify_spectacol(self, new_entity):
        """
        Modifica o entitate
        :param new_entity: noua entitate 
        :return: nothing
        """
        for entity in self._spectacole:
            if entity.get_titlu() == new_entity.get_titlu() and entity.get_artist() == new_entity.get_artist():
                entity.set_durata(new_entity.get_durata())
                entity.set_gen(new_entity.get_gen())
    

        
           
class FileRepo(SpectacolRepo):
    def __init__(self, file_name) -> None:
        super().__init__()
        self.__file_name = file_name

    def __read_from_file(self):
        """
        Functie de citire din fisier
        """
        try:
            f = open(self.__file_name, "r")
        except IOError:
            raise Exception("Fisierul nu putut fi deschis...")
        
        
        self._spectacole.clear()
        lines = f.readlines()

        for line in lines:
            if line != "":
                tokens = line.strip().split(",")
                titlu = tokens[0]
                artist = tokens[1]
                gen = tokens[2]
                durata = int(tokens[3])
                spectacol = Spectacol(titlu, artist, gen, durata)
                self._spectacole.append(spectacol)

        f.close()

    def __write_to_file(self):
        """
        Functie de scriere in fisier
        """
        try:
            f = open(self.__file_name, "w")
        except IOError:
            raise Exception("Fisierul nu putut fi deschis...")
        
        for spectacol in self._spectacole:
            f.write(f"{spectacol.get_titlu()},{spectacol.get_artist()},{spectacol.get_gen()},{spectacol.get_durata()}" + "\n")

        f.close()

    def get_all(self):
        self.__read_from_file()
        return super().get_all()
    
    def add_spectacol(self, new_entity):
        """
        Adaugarea entitatilor in fiser
        """
        self.__read_from_file()
        super().add_spectacol(new_entity)
        self.__write_to_file()

    def modify_spectacol(self, new_entity):
        """
        Modificarea entitatilor din fisier
        """
        self.__read_from_file()
        super().modify_spectacol(new_entity)
        self.__write_to_file()


class FileExport(SpectacolRepo):
    def __init__(self, file_name) -> None:
        super().__init__()
        self.__file_name = file_name

    def __read_from_file(self):
        try:
            f = open(self.__file_name, "r")
        except IOError:
            raise Exception("Fisierul nu putut fi deschis...")
        
        
        self._spectacole.clear()
        lines = f.readlines()

        for line in lines:
            if line != "":
                tokens = line.strip().split(",")
                titlu = tokens[0]
                artist = tokens[1]
                gen = tokens[2]
                durata = int(tokens[3])
                spectacol = Spectacol(titlu, artist, gen, durata)
                self._spectacole.append(spectacol)

        f.close()

    def __write_to_file(self):
        try:
            f = open(self.__file_name, "w+")
        except IOError:
            raise Exception("Fisierul nu putut fi deschis...")
        
        for spectacol in self._spectacole:
            f.write(f"{spectacol.get_titlu()},{spectacol.get_artist()},{spectacol.get_gen()},{spectacol.get_durata()}" + "\n")

        f.close()

    def get_all(self):
        self.__read_from_file()
        return super().get_all()
    
    def add_spectacol(self, new_entity):
        self.__read_from_file()
        super().add_spectacol(new_entity)
        self.__write_to_file()