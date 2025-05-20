from domain.domain_student import *
from domain.domain_laboratory import *
from repository.repository_student import *
from repository.repository_laboratory import *
from controller.controller_student import *
import unittest

class Tests(unittest.TestCase):
    def setUp(self):
        self.student_collection = Student_Repository()
        self.student1 = Student(1, "John", 212)
        self.student2 = Student(2, "Jane", 213)
        self.student_collection.add_student(self.student1)
        self.student_collection.add_student(self.student2)
        self.student3 = Student(1, "Ben", 214) 
        self.problem_collection = Laboratory_Repository()
        self.lab1 = Laboratory(1, "Description 1", "2023/11/30")
        self.lab2 = Laboratory(2, "Description 2", "2023/12/15")
        self.problem_collection.add_problem(self.lab1)
        self.problem_collection.add_problem(self.lab2)
        self.lab3 = Laboratory(1, "Description 1", "2023/11/30") 

    def test_get_id(self):
        self.assertEqual(self.student1.get_id(), 1)
        self.assertEqual(self.student2.get_id(), 2)

    def test_get_name(self):
        self.assertEqual(self.student1.get_name(), "John")
        self.assertEqual(self.student2.get_name(), "Jane")

    def test_get_group(self):
        self.assertEqual(self.student1.get_group(), 212)
        self.assertEqual(self.student2.get_group(), 213)

    def test_set_name(self):
        self.student1.set_name("Sean")
        self.assertEqual(self.student1.get_name(), "Sean")

    def test_set_group(self):
        self.student2.set_group(215)
        self.assertEqual(self.student2.get_group(), 215)

    def test_eq(self):
        self.assertTrue(self.student1 == self.student3)  
        self.assertFalse(self.student1 == self.student2)  

    def test_get_id_lab(self):
        self.assertEqual(self.lab1.get_id(), 1)
        self.assertEqual(self.lab2.get_id(), 2)

    def test_get_description(self):
        self.assertEqual(self.lab1.get_description(), "Description 1")
        self.assertEqual(self.lab2.get_description(), "Description 2")

    def test_get_deadline(self):
        self.assertEqual(self.lab1.get_deadline(), "2023/11/30")
        self.assertEqual(self.lab2.get_deadline(), "2023/12/15")

    def test_set_description(self):
        self.lab1.set_description("Updated Description")
        self.assertEqual(self.lab1.get_description(), "Updated Description")

    def test_set_deadline(self):
        self.lab2.set_deadline("2023-12-20")
        self.assertEqual(self.lab2.get_deadline(), "2023-12-20")

    def test_eq_lab(self):
        self.assertTrue(self.lab1 == self.lab3)  
        self.assertFalse(self.lab1 == self.lab2)

    def test_get_all(self):
        all_students = self.student_collection.get_all() 
        self.assertIn(self.student1, all_students)  


    def test_add_student(self):
        all_students = self.student_collection.get_all()
        student3 = Student(3, "Alice Johnson", "Group C")
        self.student_collection.add_student(student3)
        self.assertIn(self.student3, all_students)

    def test_delete_student(self):
        all_students = self.student_collection.get_all()
        self.student_collection.delete_student(1)

    def test_add_problem(self):
        problem3 = Laboratory(3, "Description 3", "2023/12/20")
        self.problem_collection.add_problem(problem3)  

    def test_delete_problem(self):
        self.problem_collection.delete_problem(1)

    

if __name__ == '__main__':
    unittest.main()
