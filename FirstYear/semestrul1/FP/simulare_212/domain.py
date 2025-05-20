
class Imobil:
    def __init__(self, id : int, adresa: str, pret : float, oferta : str):
        self._id = id
        self._adresa = adresa
        self._pret = pret
        self._oferta = oferta

    def get_id(self):
        """
        Returneaza id-ul imobilului
        """
        return self._id
    
    def get_adresa(self):
        """
        Returneaza adresa imobilului
        """
        return self._adresa
    
    def get_oferta(self):
        """
        Returneaza oferta imobilului
        """
        return self._oferta
    
    def get_pret(self):
        """
        Returneaza pretul imobilului
        """
        return self._pret
    
    def __str__(self) -> str:
        return (f"{self.get_id()} : {self.get_oferta()} - {self.get_adresa()} , {self.get_pret()}")
    
class Tranzactie:
    def __init__(self, adresa, comision) -> None:
        self._adresa = adresa
        self._comision = comision

    def __str__(self) -> str:
        return (f"{self._adresa} - {self._comision}")