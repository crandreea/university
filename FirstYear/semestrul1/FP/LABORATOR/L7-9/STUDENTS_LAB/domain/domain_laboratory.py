class Laboratory:
    def __init__(self, problem_id, description, deadline) -> None:
        self.__id = problem_id
        self.__description = description
        self.__deadline = deadline

    def __str__(self) -> str:
        return f"Problem {self.__id} (until {self.__deadline}) - {self.__description}"
    
    def __eq__(self, other):
        if not isinstance(other, Laboratory):
            return False
        return self.get_id() == other.get_id()
    
    def get_id(self):
        return self.__id
    
    def get_description(self):
        return self.__description
    
    def get_deadline(self):
        return self.__deadline
    
    def set_description(self, message):
        self.__description = message

    def set_deadline(self, new_date):
        self.__deadline = new_date
        
    
    
