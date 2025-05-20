class Spectacol:
    def __init__(self, titlu, artist, gen, durata) -> None:
        self.__titlu = titlu
        self.__artist = artist
        self.__gen = gen
        self.__durata = durata

    def get_titlu(self):
        """
        Geter pentru titlu
        :return: titlul 
        """
        return self.__titlu
    
    def get_artist(self):
        """
        Geter pentru nume artist
        :return: nume artist
        """
        return self.__artist
    
    def get_gen(self):
        """
        Geter pentru gen
        :return: genul
        """
        return self.__gen
    
    def get_durata(self):
        """
        Geter pentru durata
        :return: durata
        """
        return self.__durata
    
    def set_durata(self, new_durata):
        self.__durata = new_durata

    def set_gen(self, new_gen):
        self.__gen = new_gen
    
    def __eq__(self, other) -> bool:
        """
        Verifica egalitatea dintre doua functionalitati
        :param other: o alta entitate
        :return: bool 
        """
        return (self.__titlu == other.get_titlu() and self.__artist == other.get_artist())
    
    def __str__(self) -> str:
        return [f"<TITLU> {self.get_titlu()} <ARTIST> {self.get_artist()} <GEN> {self.get_gen()} <DURATA> {self.get_durata()}"]
    