from domain.domain_student import *

class Student_Repository():
    def __init__(self) -> None:
        self._students = {}
        

    def get_all(self):
        return [self._students[identifier] for identifier in self._students.keys()]
    
    def get_student(self, identifier):
        return self._students[identifier]

    def __len__(self):
        return len(self._students)
    
    def add_student(self, student : Student):
        """
        Add a new student to the repository
        """
        student_id = student.get_id()
        if student_id in self._students.keys():
                raise Exception(f"Studentul cu ID-ul {student_id} a fost introdus deja...")
        
        self._students[student_id] = student 

    def modify_student(self, new_student  : Student):
        """
        Modify an existing student in the repository
        """
        student_id = new_student.get_id()
        if student_id not in self._students.keys():
            raise Exception(f"Studentul cu ID-ul {student_id} nu exista...")
        
        self._students[student_id] = new_student
        
    def delete_student(self, student_id) -> None:
        """
        Delete an existing student in the repository
        """
        if student_id not in self._students.keys():
            raise Exception(f"Studentul cu ID-ul {student_id} nu exista...")
        self._students.pop(student_id)
   
    
    def search_student(self, student_id):
        """
        Cauta un student dupa id
        :param studnet_id: id student
        """
        if student_id not in self._students.keys():
            raise Exception(f"Studentul cu ID-ul {student_id} nu exista...")

        return self._students[student_id]
        
class FileStudentRepository(Student_Repository):
    def __init__(self, file_name) -> None:
        super().__init__()
        self.__file_name = file_name
   
    def __read_students_from_file(self) -> None:

        try:
            f = open(self.__file_name, "r")
        except IOError:
            raise Exception(f"Fisierul {self.__file_name} nu exista...")
        
        self._students.clear()
        lines = f.readlines()
        
        for line in lines:
            if line != "":
                tokens = line.strip().split(",")
                student_id = tokens[0]
                name = tokens[1]
                group = tokens[2]
                student = Student(student_id, name, group)
                self._students[student_id] = student
        f.close()
    
    def __write_students_to_file(self) -> None:
        
        try:
            f = open(self.__file_name, "w")
        except IOError:
            raise Exception(f"Fisierul {self.__file_name} nu exista...")
        
        for student in self._students.values():
            f.write(f"{student.get_id()},{student.get_name()},{student.get_group()}" + "\n")
        f.close()
    
    def add_student(self, student: Student):
        self.__read_students_from_file()
        super().add_student(student)
        self.__write_students_to_file()

    def get_all(self):
        self.__read_students_from_file()
        return super().get_all()
        
    def modify_student(self, new_student: Student):
        self.__read_students_from_file()
        super().modify_student(new_student)
        self.__write_students_to_file()
    
    
    def delete_student(self, student_id) -> None:
        self.__read_students_from_file()
        super().delete_student(student_id)
        self.__write_students_to_file()

    def search_student(self, student_id):
        self.__read_students_from_file()
        return super().search_student(student_id)
    
    def get_student(self, identifier):
        self.__read_students_from_file()
        return super().get_student(identifier)
    
    def clean_up(self):
        self._students = []
        self.__write_students_to_file()

    

