from domain import *
class Imobil_Repo:
    def __init__(self, file_name) -> None:
        self._elements = {}
        self.__file_name = file_name

    def get_all(self):
        """
        Returneaza toate elemenetele
        :return: list
        """ 
        self.read_from_file()
        return [self._elements[identifier] for identifier in self._elements.keys()]
    
    def get_imobil(self, id):
        """
        Returneaza un imobil dupa id
        :param id: id-ul imobilului
        :return: object 
        """
        return self._elements[id]
    

    def read_from_file(self):
        """
        Citeste elementele dintr-un fisier si le salveaza in dictionar
        :retunr: nothing
        """
        try:
            f = open(self.__file_name, "r")
        except IOError:
            raise Exception(f"Fisierul nu a putut fi deschis...")
        
        self._elements.clear()
        lines = f.readlines()

        for line in lines:
            if line != "":
                tokens = line.strip().split(",")
                id = tokens[0]
                adresa = tokens[1]
                pret = tokens[2]
                oferta = tokens[3]
                element = Imobil(id, adresa, pret, oferta)
                self._elements[id] = element

        f.close()
