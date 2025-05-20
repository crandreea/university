
class Tractor:
    def __init__(self, id, denumire, pret, model, data) -> None:
        self.__id = id
        self.__denumire = denumire
        self.__pret = pret
        self.__model = model
        self.__data = data

    def get_id(self):
        return self.__id
    
    def get_denumire(self):
        return self.__denumire
    
    def get_pret(self):
        return self.__pret
    
    def get_model(self):
        return self.__model
    
    def get_data(self):
        return self.__data
    
    def set_denumire(self, new_denumire):
        self.__denumire = new_denumire

    def set_pret(self, new_pret):
        self.__pret = new_pret

    def set_model(self, new_model):
        self.__model= new_model

    def set_data(self, new_data):
        self.__data = new_data

    def __str__(self) -> str:
        return f"<ID> {self.__id} <NUME> {self.__denumire} <PRET> {self.__pret} <MODEL> {self.__model} <DATA> {self.__data}"
        
    def __repr__(self) -> str:
        return f"{self.__id},{self.__denumire},{self.__pret},{self.__model},{self.__data}"
    
    