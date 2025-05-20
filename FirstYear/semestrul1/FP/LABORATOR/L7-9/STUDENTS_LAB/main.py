from user_interface.ui import *
from domain.domain_laboratory import *
from domain.domain_student import *
from controller.controller_laboratory import *
from controller.controller_student import *
from controller.controller_grade import *
from repository.repository_student import *
from repository.repository_laboratory import *
from testing import *


def main():
    #student_repo = Student_Repository()
    student_repo = FileStudentRepository("/Users/croitoruandreea/Desktop/FP/LABORATOR/L7-9/STUDENTS_LAB/repository/students.txt")
    student_validator = Student_Validator()
    student_control = Student_Controller(student_repo, student_validator)
    
    #lab_repo = Laboratory_Repository()
    lab_repo = FileLabRepository("/Users/croitoruandreea/Desktop/FP/LABORATOR/L7-9/STUDENTS_LAB/repository/problems.txt")
    lab_validator = Laboratory_Validator()
    lab_control = Laboratory_Controller(lab_repo, lab_validator)
    validator = Validator()
    #grade_repo = Grade_Repository()
    grade_repo = FileGradeRepository("/Users/croitoruandreea/Desktop/FP/LABORATOR/L7-9/STUDENTS_LAB/repository/grades.txt")

    
    
    common_control = Grade_Manager(grade_repo, student_repo)
    
    console = UI(student_control, lab_control, common_control)
    console.run()

if __name__ == "__main__":
    main()
