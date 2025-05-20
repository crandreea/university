import datetime
from domain.tractor import *

class Validator:
    def __init__(self) -> None:
        pass

    def is_number(self, str : str):
        try:
            str = int(str)
            return str > 0
        except ValueError:
            return False
        
    def is_name(self, str : str):
        return str.isalpha()
    
    def is_date(self, str : str):
        try:
            datetime.datetime.strptime(str, '%Y/%m/%d')
            return True
        except ValueError:
            return False
        
    def tractor_validation(self, tractor : Tractor):
        id = tractor.get_id()
        pret = tractor.get_pret()
        data = tractor.get_data()
        nume = tractor.get_denumire()

        erori = ""
        if not self.is_number(id):
            erori += "Id-ul trebuie sa fie un numar natural...\n"

        if not self.is_name(nume):
            erori += "Numele introdus este incorect...\n"

        if not self.is_date(data):
            erori += "Data introdusa este incorecta (format yy/mm/dd)...\n"

        if not self.is_number(pret):
            erori += "Pretul trebuie sa fie un numar intreg...\n"

        if len(erori) >0:
            erori = erori[:-1]
            raise Exception(erori)

        
    