from typing import Any
from repository import *

class Robot_Control:
    def __init__(self, robot_repo : Robot_Repo) -> None:
        self._robot_repo = robot_repo

    def get_robots(self):
        self._robot_repo.read_from_file()
        return self._robot_repo.get_all()
    
    def add(self, id, baterie, descriere, moves):
    
        robot = Robot(id, baterie, descriere, moves)
        self._robot_repo.add_robot(robot)

    def filter_description(self, str):
        self._robot_repo.read_from_file()
        result = []
        for robot in self._robot_repo.get_all():
            if str in robot.get_descriere():
                result.append(robot)

        sorted_result = sorted(result, key=lambda x: x.get_baterie(), reverse=True)
        return sorted_result
    
    def final_move(self, x, y, robot:Robot):
        
        moves = self._robot_repo.get_robot_moves(robot)
        baterie = robot.get_baterie()
        baterie = int(baterie)
        x = int(x)
        y = int(y)
        for move in moves:
                if baterie > 0:
                    if move == "right": 
                        x += 1
                        baterie -= 10
                    elif move == "left":
                        x -= 1
                        baterie -= 10
                    elif move == "up":
                        y += 1 
                        baterie -= 10
                    elif move == "down":
                        y -= 1
                        baterie -= 10
                    elif move == "halt":
                        baterie += 5

        robot.set_baterie(baterie)
        return(RobotDTO(robot.get_id(),x,y,baterie))


