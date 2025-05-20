from domain.domain_grade import *
from domain.domain_grade import Grade
from user_interface.validation import *
class Grade_Repository(Validator):
    def __init__(self):
        self._grades = []

    def find(self, student, problem):
        """
        Returns a grade object for a studnet and a problem given
        """
        for grade in self._grades:
            if grade.get_student() == student and grade.get_problem() == problem:
                return grade
        return None
  
    def get_all(self):
        return [self._grades[identifier] for identifier in self._grades]
    
    def add(self, grade: Grade):
        """
        Add a grade object 
        :param grade: grade object
        """
        if self.find(grade.get_student(), grade.get_problem())!=None:
            raise Exception("Problema a fost deja notata!")
        
        if self.is_grade(grade.get_grade()) == False:
            raise Exception("Notare se realizeaza de la 1 la 10!")

        self._grades.append(grade)

    def size(self):
        return len(self._grades)
    
    def get_grades_for_student(self, student_id):
        """
        Get all grades of a specific student
        :param stdunet_id: id student
        """

        rez = []
        for grade in self._grades:
            if grade.get_student() == student_id:
                rez.append(grade)
        if rez == []:
            raise Exception("Studentul nu a fost notat!")
        else: return rez
    
    def get_grade_for_student_by_problem(self, student, problem_id):
        """
        Get the grade of a specific student by a problem
        :param stdunet: id studnet
        :param problem_id: id problema
        :return: list
        """
        st_grade = 0
        for grade in self._grades:
            if grade.get_student() == student and grade.get_problem() == problem_id:
                st_grade = int(grade.get_grade())
        return st_grade
    
    def get_all_grades_for_student(self, student_id):
        """
        Get all grades of a specific student
        :param student_id: id student
        :return: list 
        """
        grades = []
        for grade in self._grades:
            if grade.get_student() == student_id:
                grades.append(int(grade.get_grade()))
        return grades

class FileGradeRepository(Grade_Repository):
    
    def __init__(self, file_name):
        super().__init__()
        self.__file_name = file_name
    
    def __read_grades_from_file(self) -> None:
        """
        Reads data from file to internal storage
        """

        try:
            f = open(self.__file_name, "r")
        except IOError:
            raise Exception(f"Fisierul {self.__file_name} nu exista...")
        
        self._grades.clear()
        lines = f.readlines()
        
        for line in lines:
            if line != "":
                tokens = line.strip().split(",")
                student_id = tokens[0]
                problem_id = tokens[1]
                grade = tokens[2]
                grade = Grade(student_id, problem_id, grade)
                self._grades.append(grade)
        f.close()
    
    def __write_grades_to_file(self) -> None:
        """
        Writes data to file
        """
        
        try:
            f = open(self.__file_name, "w")
        except IOError:
            raise Exception(f"Fisierul {self.__file_name} nu exista...")
        
        for grade in self._grades:
            f.write(f"{grade.get_student()},{grade.get_problem()},{grade.get_grade()}" + "\n")
        f.close()

    def find(self, student, problem):
        """
        Find a specific grade by its student and problem id
        """
        self.__read_grades_from_file()
        return super().find(student, problem)
    
    def add(self, grade: Grade):
        """
        Adds a new grade to the list of grades
        """
        self.__read_grades_from_file()
        super().add(grade)
        self.__write_grades_to_file()

    def get_all(self):
        """
        Returns al grade objects
        """
        self.__read_grades_from_file()
        return super().get_all()
    
    def get_all_grades_for_student(self, student_id):
        """
        Get all grades for a given student
        """
        self.__read_grades_from_file()
        return super().get_all_grades_for_student(student_id)
    
    def get_grade_for_student_by_problem(self, student, problem_id):
        """
        Get grade for a given student and problem
        """
        self.__read_grades_from_file()
        return super().get_grade_for_student_by_problem(student, problem_id)
    
    def get_grades_for_student(self, student_id):
        """
        Get all grades for a given student
        """
        self.__read_grades_from_file()
        return super().get_grades_for_student(student_id)
    
    