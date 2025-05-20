from domain.domain_student import *
from domain.domain_laboratory import *
import datetime
class Validator:
    def __init__(self) -> None:
        pass

    def is_number(self, str : str):
        try:
            str = int(str)
            return str > 0
        except ValueError:
            return False
        
    def is_name(self, str : str):
        return str.isalpha()
    
    def is_date(self, str : str):
        try:
            date = datetime.datetime.strptime(str, '%Y/%m/%d')
            return date > datetime.datetime.today()
        except ValueError:
            return False
    
    def is_grade(self, str : str):
        try:
            grade = float(str)
            return grade >= 0 and grade <= 10
        except ValueError:
            return False
        
    
    

class Student_Validator(Validator):
    def validate_student(self, student : Student) -> None:
        errors = ""
        if self.is_number(student.get_id()) == False:
            errors += "ID-ul trebuie sa fie un numar natural...\n"
            #raise Exception("ID-ul trebuie sa fie un numar natural...\n")
            
        if self.is_name(student.get_name()) == False:
            errors += "NUMELE trebuie sa fie format strict din litere...\n"
            #raise Exception("NUMELE trebuie sa fie format strict din litere...")
            
        if self.is_number(student.get_group()) == False:
            errors += "GRUPA trebuie sa fie un numar natural..."
        
        if len(errors) > 0:
            errors = errors[:-1]
            raise Exception(errors)
            
            
        

class Laboratory_Validator(Validator):
    def validate_laboratory(self, problem : Laboratory):
        errors = ""
        if self.is_number(problem.get_id()) == False:
            errors += "NUMAR_PROBLEMA trebuie sa fie un numar natural...\n"
        if self.is_date(problem.get_deadline()) == False:
            errors += "DEADLINE invalid (format: aa/ll/zz )...\n"
            
        if len(errors) > 0:
            errors = errors[:-1]
            raise Exception(errors)
    
