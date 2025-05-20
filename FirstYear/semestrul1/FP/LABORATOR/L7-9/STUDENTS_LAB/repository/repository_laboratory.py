from domain.domain_laboratory import *
from domain.domain_laboratory import Laboratory
class Laboratory_Repository:
    def __init__(self) -> None:
        self._problems = {}

    def get_all(self):
        return [self._problems[identifier] for identifier in self._problems.keys()]

    def get_problem(self, identifier):
        return self._problems[identifier]
    
    def __len__(self):
        return len(self._problems)
    
    def add_problem(self, problem : Laboratory):
        """
        Adauga o problema 
        :param problem: problem object
        """
        problem_id = problem.get_id()
        if problem_id in self._problems.keys():
            raise Exception(f"Problema {problem_id} a fost introdusa deja...")
        self._problems[problem_id] = problem
    
    def modify_problem(self, new_problem : Laboratory):
        """
        Modifica o problema cu alta data 
        :param new_problem: problem object
        """
        id = new_problem.get_id()
        if id not in self._problems.keys():
            raise Exception(f"Problema {id} nu exista...")
        self._problems[id] = new_problem

    def delete_problem(self, id):
        """
        Sterge o problema dupa ID-ul acesteia
        :param id: id problema
        """
        if id not in self._problems.keys():
            raise Exception(f"Problema {id} nu exista...")
        self._problems.pop(id)

    #RECURSIVE
    def search_problem(self, id, index = 0):
        """
        Cauta o problema in mod recursiv 
        :param id: id problema
        """
        keys = list(self._problems.keys())

        if index >= len(keys):
            raise Exception(f"Problema {id} nu exista...")
        
        if keys[index] == id:
            return self._problems[keys[index]]
        
        self.search_problem(id, index + 1)
    
    """
    def search_problem(self, id):
        if id not in self._problems.keys():
            raise Exception(f"Problema {id} nu exista...")
        return self._problems[id]
    
    """
        
class FileLabRepository(Laboratory_Repository):
    def __init__(self, file_name):
        super().__init__()
        self.__file_name = file_name

    def __read_problems_from_file(self) -> None:

        """
        Citirea datelor din fisier si adaugarea lor intr-un dictionar
        """
        try:
            f = open(self.__file_name, "r")
        except IOError:
            raise Exception(f"Fisierul {self.__file_name} nu exista...")
        
        self._problems.clear()
        lines = f.readlines()
        
        for line in lines:
            if line != "":
                tokens = line.strip().split(",")
                problem_id = tokens[0]
                description = tokens[1]
                deadline = tokens[2]
                problem = Laboratory(problem_id, description, deadline)
                self._problems[problem_id] = problem
        f.close()
    
    def __write_students_to_file(self) -> None:
        """
        Scrie datele din dictionar in fisier
        """
        try:
            f = open(self.__file_name, "w")
        except IOError:
            raise Exception(f"Fisierul {self.__file_name} nu exista...")
        
        for problem in self._problems.values():
            f.write(f"{problem.get_id()},{problem.get_description()},{problem.get_deadline()}" + "\n")
        f.close()

    def add_problem(self, problem: Laboratory):
        """
        Adauga o problema 
        """
        self.__read_problems_from_file()
        super().add_problem(problem)
        self.__write_students_to_file

    def delete_problem(self, id):
        """
        Sterge o problema dupa id
        """
        self.__read_problems_from_file()
        super().delete_problem(id)
        self.__write_students_to_file()

    def modify_problem(self, new_problem: Laboratory):
        """
        Modifica o problema cu alta data
        """
        self.__read_problems_from_file()
        super().modify_problem(new_problem)
        self.__write_students_to_file()

    def get_all(self):
        self.__read_problems_from_file()
        return super().get_all()
    
    def get_problem(self, identifier):
        self.__read_problems_from_file()
        return super().get_problem(identifier)
    
    def search_problem(self, id):
        self.__read_problems_from_file()
        return super().search_problem(id)