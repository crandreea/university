from repository.repository_grade import *
from repository.repository_student import *

class Grade_Manager:
    def __init__(self, grade_repo : Grade_Repository, student_repo : Student_Repository):
        self.__grade_repo = grade_repo
        self.__student_repo = student_repo

    def assign(self, student_id, problem_id, grade):
        """
        Assigns a grade to the given student for the specified problem.
        :param student_id: id student
        :param problem_id: id problema
        :param grade: nota student
        :return: grade object 
        """
        student = self.__student_repo.search_student(student_id)
        grade = Grade(student_id, problem_id, grade)

        self.__grade_repo.add(grade)

        return grade
    
    def list_grades_for_student(self, student_id):
        """
        Returns all grades of the given student.
        """
        student = self.__student_repo.search_student(student_id)

        return self.__grade_repo.get_grades_for_student(student_id)
    

    def average_grade_for_student(self):
        """
        Calculates and returns the average grade of all students in the system.

        """
        # complexitatea: O(n*m) + O(p)
        students = self.__student_repo.get_all()
        averages = {}

        for student in students: # O(n) - n nr studenti 
            grades = self.__grade_repo.get_all_grades_for_student(student.get_id()) # O(m) - m nr note pt un student
            if grades != []:
                sum_grades = sum(grades)
                total_grades = len(grades)
                if total_grades > 0:
                    averages[student.get_id()] = round(sum_grades / total_grades, 2)
        
        result = []

        for key, value in averages.items(): # O(p) - p nr studenti cu media notelor sub 5
            student = self.__student_repo.get_student(key)
            student_name = student.get_name()
            student_average = value
            if student_average < 5:
                dto = StudentGradeDTO(student_name, student_average)
                result.append(dto)
        if result == []:
            raise Exception("Nu exista student cu media notelor sub 5!")
        else: return result
    
    @staticmethod
    def insertion_sort(arr, key=lambda x: x, reverse = False):
        for i in range(1, len(arr)):
            key_value = arr[i]
            j = i - 1
            while j >= 0 and key(key_value) < key(arr[j]):
                arr[j + 1] = arr[j]
                j -= 1
            arr[j + 1] = key_value

        if reverse:
            arr.reverse()
        return arr

    @staticmethod
    def comb_sort(arr, key=lambda x: x, reverse = False):
        def get_next_gap(gap):
            gap = (gap * 10) // 13
            if gap < 1:
                return 1
            return gap

        n = len(arr)
        gap = n
        swapped = True

        while gap != 1 or swapped:
            gap = get_next_gap(gap)
            swapped = False

            for i in range(0, n - gap):
                if key(arr[i]) > key(arr[i + gap]):
                    arr[i], arr[i + gap] = arr[i + gap], arr[i]
                    swapped = True

        if reverse:
            arr.reverse()

        return arr
    
    def students_grade_for_problem(self, problem_id):
        """
        Returneaza lista ordonata de note pentru o problema data
        :param proble,_id: id problemei
        :retunr: lista ordonata
        """
        students = self.__student_repo.get_all()
        result = []

        for student in students:
            grade = self.__grade_repo.get_grade_for_student_by_problem(student.get_id(), problem_id)
            student_name = student.get_name()
            if grade > 0:
                dto = StudentGradeDTO(student_name, grade)
                result.append(dto)

        if result == []:
            raise Exception("Studentii nu a fost notati la aceasta problema!")
        else: 
            #studenti_ordonati = sorted(result, key=lambda x: (-x.get_grade(), x.get_name()))

            def sort_key(student_grade):
                return (-student_grade.get_grade(), student_grade.get_name())

            sorted_students = self.comb_sort(result, key=lambda x: sort_key(x), reverse = True)
            return sorted_students

            

