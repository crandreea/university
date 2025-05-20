class Robot:
    def __init__(self, id, baterie, descriere, moves) -> None:
        self.__id = id
        self.__baterie = baterie
        self.__descriere = descriere
        self.__moves = moves

    def get_id(self):
        return self.__id
    
    def get_baterie(self):
        return self.__baterie
    
    def get_descriere(self):
        return self.__descriere
    
    def get_moves(self):
        return self.__moves
    
    def set_baterie(self, new_baterie):
        self.__baterie = new_baterie

    def __str__(self) -> str:
        return(f"{self.__id} : {self.__baterie} , {self.__descriere} - {self.__moves}")
    
class RobotDTO:
    def __init__(self, id, x, y, baterie):
        self._id = id
        self._x = x
        self._y = y
        self._baterie = baterie

    def __str__(self):
        return(f"{self._id} : < {self._x},{self._y}> , {self._baterie}")
