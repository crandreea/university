from domain.tractor import *
from domain.tractor import Tractor

class RepoTractor:
    def __init__(self) -> None:
        self._tractoare = {}

    def get_all(self):
        return [self._tractoare[id] for id in self._tractoare.keys()]
    
    def get_tractor(self, tractor_id):
        if tractor_id not in self._tractoare.keys():
            raise Exception("Tractorul cu acest id nu exista...")
        return self._tractoare[tractor_id]
    
    def add_tractor(self, tractor : Tractor):
        tractor_id = tractor.get_id()
        if tractor_id in self._tractoare.keys():
            raise Exception("Tractorul cu acest id exista deja...")
        self._tractoare[tractor_id] = tractor

    def modify_tractor(self, new_tractor : Tractor):
        tractor_id = new_tractor.get_id()
        if tractor_id not in self._tractoare.keys():
            raise Exception("Tractorul cu acest id nu exista...")

        self._tractoare[tractor_id] = new_tractor

    def delete_tractor(self, tractor : Tractor):
        tractor_id = tractor.get_id()
        if tractor_id not in self._tractoare.keys():
            raise Exception("Tractorul cu acest id nu exista...")

        self._tractoare.pop(tractor_id)


class FileRepo(RepoTractor):
    def __init__(self, file_name) -> None:
        super().__init__()
        self._file_name = file_name

    def __read_from_file(self):
        try:
            f = open(self._file_name, "r")
        except IOError:
            print("Fisierul nu a putut fi deschis...")

        self._tractoare.clear()
        lines = f.readlines()

        for line in lines:
            if line!="":
                params = line.strip().split(",")
                id = int(params[0])
                denumire = params[1]
                pret = int(params[2])
                model = params[3]
                data = params[4]

                tractor = Tractor(id, denumire, pret, model, data)
                self._tractoare[id] = tractor

        f.close()

    def __write_to_file(self):
        try:
            f = open(self._file_name, "w")
        except IOError:
            print("Fisierul nu a putut fi deschis...")

        for tractor in self._tractoare.values():
            f.write(f"{tractor.get_id()},{tractor.get_denumire()},{tractor.get_pret()},{tractor.get_model()},{tractor.get_data()}" + "\n")

        f.close()

    def add_tractor(self, tractor: Tractor):
        self.__read_from_file()
        super().add_tractor(tractor)
        self.__write_to_file()

    def modify_tractor(self, new_tractor: Tractor):
        self.__read_from_file()
        super().modify_tractor(new_tractor)
        self.__write_to_file()

    def delete_tractor(self, tractor: Tractor):
        self.__read_from_file()
        super().delete_tractor(tractor)

    def get_tractor(self, tractor_id):
        self.__read_from_file()
        super().get_tractor(tractor_id)
    
