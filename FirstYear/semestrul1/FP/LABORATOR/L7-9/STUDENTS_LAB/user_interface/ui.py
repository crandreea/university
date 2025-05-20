from controller.controller_student import *
from controller.controller_laboratory import *
from controller.controller_grade import *
from user_interface.menu import *

class UI:
    def __init__(self, student_control : Student_Controller, lab_control : Laboratory_Controller, common_control : Grade_Manager) -> None:
        self._student_control = student_control
        self._lab_control = lab_control
        self._grade_control = common_control
    
    def valid_option(self, str :str):
        try:
            str = int(str)
            return str > -1
        except ValueError:
            return False
            

    def ui_student(self):
            try:
                MENU.student_menu()
                while True:
                    
                    command = input("Introduceti optiunea: ")
                    if not self.valid_option(command):
                        print("Optiune invalida!\n")
                        UI.ui_student(self)
                    else: command = int(command) 

                    if command == 0:
                        students = self._student_control.get_students()
                        if len(students) == 0:
                            print("Nu exista niciun student...\n")
                        for student in students: 
                            print(student)
                        print("\n")

                    elif command == 1:
                        print("Introduceti urmatoarele date despre student:")
                        id = input("ID: ")
                        name = input("NUME: ")
                        group = input("NUMAR_GRUPA: ")
                        self._student_control.add(id, name, group)
                        print("Studentul a fost adaugat cu succes!\n")
                        

                    elif command == 2:
                        print("Introduceti:")
                        student_id = input("ID: ")
                        self._student_control.delete(student_id)
                        print("Studentul a fost sters cu succes!\n")

                    elif command == 3:
                        print("Introduceti urmatoarele date despre student:")
                        id = input("ID: ")
                        name = input("NOUL NUME: ")
                        self._student_control.modify_name(id, name)
                        print("Studentul a fost modificat cu succes! \n")

                    elif command == 4:
                        print("Introduceti urmatoarele date despre student:")
                        id = input("ID: ")
                        group = input("NOUA GRUPA: ")
                        self._student_control.modify_group(id, group)
                        print("Studentul a fost modificat cu succes! \n")

                    elif command == 5:
                        print("Introduceti:")
                        id = input("ID: ")
                        print(f"Studentul cautat este: {self._student_control.search(id)}\n")

                    elif command == 6:
                        print("-EXIT-\n")
                        exit()

                    elif command == 7:
                        UI.run(self)

                    elif command == 8:
                        cnt = int(input("Introduceti numarul de entitati random: "))
                        self._student_control.random_student(cnt)
                        print("Studnetii au fost adaugati!\n")

                    else:
                        print("Optiune invalida!\n")

            except Exception as e:
                print(e)
                print("\n")
                UI.ui_student(self)

                    
    def ui_laboratory(self):
       try:
            MENU.laboratory_menu()
            while True:
                    
                    command = input("Introduceti optiunea: ")
                    if not self.valid_option(command):
                        print("Optiune invalida!\n")
                        UI.ui_laboratory(self)

                    else: command = int(command) 

                    if command == 0:
                        problems = self._lab_control.get_problems()
                        if len(problems) == 0:
                            print("Nu exista nicio problema...\n")
                        for problem in problems:
                            print(problem)
                        print("\n")

                    elif command == 1:
                        print("Introduceti urmatoarele date despre problema: ")
                        id = input("ID: ")
                        description = input("DESCRIPTION: ")
                        deadline = input("DEADLINE: ")

                        self._lab_control.add(id, description, deadline)
                        print("Problema a fost adaugata cu succes!\n")

                    elif command == 2:
                        print("Introduceti: ")
                        id = input("ID: ")
                        self._lab_control.delete(id)
                        print("Problema a fost stearsa cu succes!\n")

                    elif command == 3:
                        print("Introduceti urmatoarele date: ")
                        id = input("ID: ")
                        description = input("NOUA DESCRIERE: ")
                        self._lab_control.modify_description(id, description)
                        print("Problema a fost modificata cu succes!\n")

                    elif command == 4:
                        print("Introduceti urmatoarele date: ")
                        id = input("ID: ")
                        deadline = input("NOUL DEADLINE: ")
                        self._lab_control.modify_deadline(id, deadline)
                        print("Problema a fost modificata cu succes!\n")

                    elif command == 5:
                        print("Introduceti: ")
                        id = input("ID: ")
                        print(f"Problema cautata este: {self._lab_control.search(id)}\n")

                    elif command == 6:
                        print("-EXIT-\n")
                        exit()

                    elif command == 7:
                        UI.run(self)

                    else:
                        print("Optiune invalida!\n")

       except Exception as e:
           print(e)
           print("\n")
           UI.ui_laboratory(self)

    """"
    def ui_notes(self):
        try:
            MENU.notes_menu()
            while True:

                option = input("Introduceti optiunea: ")
                if not self.valid_option(option):
                    print("Optiune invalida!\n")
                else: option = int(option)

                if option == 0 :
                    student_id = input("ID-ul studentului: ")
                    student = self._student_control.search(student_id)
                    rez = self._grade_control.list_grades_for_student(student_id)
                    if len(rez):
                        for grade in rez:
                            print (grade)
                    else: print("Studentul nu a fost notat!")
                    print("\n")

                elif option == 1:
                    print("Introduceti urmatoarele date: ")
                    id_student = input("ID student: ")
                    id_problem = input("ID problema: ")
                    grade = input("NOTA: ")
                    self._grade_control.assign(id_student, id_problem, grade)
                    print("Notarea a fost realizata cu succes!\n")

                elif option == 2:
                    UI.run(self)

                elif option == 3:
                    print("-EXIT-\n")
                    exit()
                
                else: print("Optiune invalida!\n")

        except Exception as e:
            print(e)
            print("\n")
            UI.ui_notes(self)
    """
    #RECURSIV
    def ui_notes(self):
        try:
            MENU.notes_menu()
            self.handle_options_notes()

        except Exception as e:
            print(e)
            print("\n")
            self.ui_notes()

    def handle_options_notes(self):
        option = input("Introduceti optiunea: ")
        if not self.valid_option(option):
            print("Optiune invalida!\n")
            self.handle_options_notes()
        else:
            option = int(option)

            if option == 0:
                student_id = input("ID-ul studentului: ")
                student = self._student_control.search(student_id)
                rez = self._grade_control.list_grades_for_student(student_id)
                if len(rez):
                    for grade in rez:
                        print(grade)
                else:
                    print("Studentul nu a fost notat!")
                print("\n")
                self.handle_options_notes()

            elif option == 1:
                print("Introduceti urmatoarele date: ")
                id_student = input("ID student: ")
                id_problem = input("ID problema: ")
                grade = input("NOTA: ")
                self._grade_control.assign(id_student, id_problem, grade)
                print("Notarea a fost realizata cu succes!\n")
                self.handle_options_notes()

            elif option == 2:
                UI.run(self)
                self.handle_options_notes()

            elif option == 3:
                print("-EXIT-\n")
                exit()

            else:
                print("Optiune invalida!\n")
                self.handle_options_notes()

    def ui_raports(self):
        try:
            MENU.raport_menu()

            while True:
                option = input("Introduceti optiunea: ")
                if not self.valid_option(option):
                    print("Optiune invalida!\n")
                else: option = int(option)

                if option == 1:
                    problem_id = input("Introduceti ID PROBLEMA: ")
                    students_list = self._grade_control.students_grade_for_problem(problem_id)
                    if len(students_list):
                        for student in students_list:
                            print(student)
                    print("\n")

                elif option == 2:
                    students_list = self._grade_control.average_grade_for_student()
                    if len(students_list):
                        print("Studentii cu media notelor sub 5 sunt: \n")
                        for student in students_list:
                            print(student) 
                    else: print("Niciun student nu are media notelor sub 5!")
                    print('\n')

                elif option == 3:
                    UI.run(self)

                elif option == 4:
                    print("-EXIT-\n")
                    exit()

                else: print("Optiune invalida!\n")

        except Exception as e:
            print(e)
            UI.ui_raports(self)

    def run(self):

            MENU.main_menu()
            while True:

                option  = input("Introduceti optiunea: ")
                if not self.valid_option(option):
                    print("Optiune invalida!\n")
                else: option = int(option)

                if option == 1:
                    UI.ui_student(self)

                elif option == 2:
                    UI.ui_laboratory(self)
                
                elif option == 3:
                    UI.ui_notes(self)

                elif option == 4:
                    UI.ui_raports(self)

                elif option == 5:
                    print("-EXIT-\n")
                    exit()

                else:
                    print("Optiunea nu exista...")