from domain import *

class Robot_Repo:
    def __init__(self, file_name) -> None:
        self._robots = {}
        self._file = file_name

    def read_from_file(self):
        try:
            f = open(self._file, "r")
        except IOError:
            print("Fisierul nu a putut fi deschis...")

        self._robots.clear()
        lines = f.readlines()

        for line in lines:
            if line != "":
                tokens = line.strip().split(";")
                id = int(tokens[0])
                baterie = int(tokens[1])
                descriere = tokens[2]
                moves = tokens[3]
                robot = Robot(id, baterie, descriere, moves)
                self._robots[id] = robot

        f.close()

    def get_all(self):
        return [self._robots[identifier] for identifier in self._robots.keys()]
    
    def get_robot_moves(self, robot: Robot):
         moves = robot.get_moves()
         moves = moves.strip().split(",")
         return moves

    def get_robot(self, id):
        return self._robots[id]
    
    def add_robot(self, robot : Robot):
        robot_id = robot.get_id
        if robot_id in self._robots.keys():
            raise Exception("Robotul cu acest id a fost deja introdus...")
        
        self._robots[robot_id] = robot

    def search_robot(self, id):
        if id not in self._robots.keys():
            raise Exception(f"Robotul cu id-ul {id} nu exista...")
        return self._robots[id]