class Student:
    def __init__(self, student_id, name, group) -> None:
        self.__id = student_id
        self.__name = name
        self.__group = group

    def __str__(self):
        return f"{self.__id} / {self.__group} : {self.__name}"
   
    def __eq__(self, other):
        if not isinstance(other, Student):
            return False
        return self.get_id() == other.get_id()
    
    def get_id(self):
        return self.__id  
    
    def get_name(self):
        return self.__name
    
    def get_group(self):
        return self.__group

    def set_group(self, new_group):
        self.__group = new_group
    
    def set_name(self, new_name):
        self.__name = new_name
