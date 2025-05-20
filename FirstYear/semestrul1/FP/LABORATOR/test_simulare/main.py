from domain import *
from repository import *
from controler import *
from ui import *
from tests import *

def main():

    robot_repo = Robot_Repo("/Users/croitoruandreea/Desktop/FP/LABORATOR/test_simulare/robots.txt")
    robot_control = Robot_Control(robot_repo)
    ui = UI(robot_control)

    ui.ui_main()

if __name__ == "__main__":
    main()
