class Examen:
    def __init__(self, data, ora, materia, tip) -> None:
        self.__data = data
        self.__ora = ora
        self.__materia = materia
        self.__tip = tip 

    def __str__(self) -> str:
        return [f"<DATA> {self.__data} <ORA> {self.__ora} <MATERIA> {self.__materia} <TIPUL> {self.__tip}"]
    
    def get_data(self):
        return self.__data
    
    def get_ora(self):
        return self.__ora
    
    def get_materia(self):
        return self.__materia
    
    def get_tip(self):
        return self.__tip
    
    