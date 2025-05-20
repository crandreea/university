from repository.repository_student import *
from domain.domain_student import *
from user_interface.validation import *
import random
import string
class Student_Controller(Validator):
    def __init__(self, student_repo: Student_Repository, student_validator: Student_Validator) -> None:
        self._student_repo = student_repo
        self._student_validator = student_validator

    def get_students(self) -> list:
        """
        Returneaza lista cu toti studentii
        :return: list of students
        """
        return self._student_repo.get_all()
    
    
    def add(self, id, name, group):
        """
        Adauga o noua entitate in lista

        :param id: id-ul studentului - int
        :param name: numele studentului - str
        :param group: grupa studentului - int
        :return: nothing
        """
        student = Student(id, name, group)
        self._student_validator.validate_student(student)
        self._student_repo.add_student(student)

    def modify_name(self, id, new_name):
        """
        Modifica numele entitatii

        :param id: id-ul studentului - int
        :param new_name: numele nou al studentului - str
        :return: nothing
        """
        
        student = self._student_repo.search_student(id)

        new_student = Student(id, new_name, self._student_repo._students[id].get_group())
        self._student_validator.validate_student(new_student)
        self._student_repo.modify_student(new_student)


    def modify_group(self, id, new_group):
        """
        Modifica grupa entitatii

        :param id: id-ul studentului - int
        :param new_group: grupa noua a studentului - int
        :return: nothing
        """
    
        student = self._student_repo.search_student(id)
        new_student = Student(id, self._student_repo._students[id].get_name, new_group)
        self._student_validator.validate_student(new_student)
        self._student_repo.modify_student(new_student)

    
    def delete(self, id):
        """
        Sterge entitatea

        :param id: id-ul studentului - int
        :return: nothing
        """
        student = self._student_repo.search_student(id)
        if self.is_number(id) ==  False:
            raise Exception(f"ID-ul trebuie sa fie un numar natural...")
    
        self._student_repo.delete_student(id)

    def search(self, id):
        """
        Cauta studentul

        :param id: id-ul studentului - int
        :return: studentul cautat
        """
        if self.is_number(id) == False:
            raise Exception(f"ID-ul trebuie sa fie un numar natural...")
        
        return self._student_repo.search_student(id)
    
    #RECURSIVE
    def random_student(self, cnt):
        if cnt==0: return
            
        id_random = random.randint(1, 30)
        lungime_nume = random.randint(5,11)
        name_random = ''.join(random.choice(string.ascii_letters) for _ in range(lungime_nume))
        group_random = random.randint(210, 217)

        if not id_random in self._student_repo._students:
            self.add(id_random, name_random, group_random)
            cnt = cnt - 1

        self.random_student(cnt-1)



        

    