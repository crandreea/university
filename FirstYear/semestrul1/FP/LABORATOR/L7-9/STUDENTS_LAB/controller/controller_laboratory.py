from repository.repository_laboratory import *
from user_interface.validation import *
class Laboratory_Controller(Validator):
    def __init__(self, lab_repo : Laboratory_Repository, lab_validator: Laboratory_Validator) -> None:
        self._lab_repo = lab_repo
        self._lab_validator = lab_validator

    def get_problems(self) -> list:
        return self._lab_repo.get_all()
    
    def add(self, id, description, deadline):
        """
        Adauga o noua entitate in lista

        :param id: id-ul problemei
        :param description: descrierea problemei 
        :param deadline: deadline-ul problemei in format yy/mm/dd
        :return: nothing
        """
        problem = Laboratory(id, description, deadline)
        self._lab_validator.validate_laboratory(problem) 
        self._lab_repo.add_problem(problem)

    def delete(self, id):
        """
        Sterge o entitate din lista

        :param id: id-ul problemei
        :return: nothing
        """
        problem = self._lab_repo.search_problem(id)
        
        if self.is_number(id) == False:
            raise Exception("ID-ul trebuie sa fie un numar natural...")
            
        self._lab_repo.delete_problem(id)

            
    def modify_description(self, id, new_description):
       
        """
        Modifica descrierea unei entitati

        :param id: id-ul problemei
        :param new_description: noua descriere a problemei 
        :return: nothing
        """
        problem = self._lab_repo.search_problem(id)

        new_problem = Laboratory(id, new_description, self._lab_repo._problems[id].get_deadline())
        self._lab_validator.validate_laboratory(new_problem)

        self._lab_repo.modify_problem(new_problem)

    
    def modify_deadline(self, id, new_deadline):
        """
        Modifica deadline-ul unei entitati

        :param id: id-ul problemei
        :param new_deadline: noul deadline al problemei 
        :return: nothing
        """
        problem = self._lab_repo.search_problem(id)
        new_problem = Laboratory(id, self._lab_repo._problems[id].get_description() , new_deadline)
        self._lab_validator.validate_laboratory(new_problem)


        self._lab_repo.modify_problem(new_problem)

    def search(self, id):
        """
        Cauta o entitate

        :param id: id-ul problemei
        :return: entitatea problema
        """
        if self.is_number(id) == False:
            raise Exception("ID-ul trebuie sa fie un numar natural...")
        
        return self._lab_repo.search_problem(id)
    

    