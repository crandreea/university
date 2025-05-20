from repository import *

class Imobil_Service:
    def __init__(self, imobil_repo : Imobil_Repo) -> None:
        self._imobil_repo = imobil_repo

    def get_imobils(self):
        """
        Returneaza lista cu toate imobiliarele 
        :return: list 
        """
        return self._imobil_repo.get_all()
    

    def average_price(self, str : str):
        """
        Calculeaza pretul mediu pentru un tip de oferta
        :param str: tipul ofertei - string
        :return: pretul mediu - float
        """
        if str != "vanzare" and str != "inchiriere":
            raise Exception("Nu a fost introdusa oferta corecta...")
        suma = 0
        cnt = 0
        for imobil in self._imobil_repo.get_all():
            if imobil.get_oferta() == str:
                suma += int(imobil.get_pret())
                cnt += 1
        
        return float(suma//cnt)
    
    def tranzactii(self, id, price):
        """
        Calculeaza comisionul unei tranzactii pentru un imobil
        :param id: id-ul imobilului
        :parma price: pretul negociat
        :return: tranzactia - object tip Tranzactie
        """
        self._imobil_repo.read_from_file()
        imobil = self._imobil_repo.get_imobil(id)

        result = []
        comision = 0
        price = int(price)
        
        if imobil.get_oferta() == "vanzare": 
            comision = (2 * price) // 100
        else: 
            comision = price // 2
        
        tranzactie = Tranzactie(imobil.get_adresa(), float(comision))
        result.append(tranzactie)
        
        return result
        
