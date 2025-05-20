from controler import *

class UI:
    def __init__(self, robot_control : Robot_Control) -> None:
        self._control = robot_control

    def ui_main(self):
        try:
            print("""
                  0. afis total
                  1. filtrare descriere dupa un sir de caractere ordonati desc dupa baterie
                  2. moves
                  3. add
                  """)
            while True:
                command = int(input("Introduceti optiune: "))
                if command == 0:
                    robots = self._control.get_robots()
                    if robots == []:
                        print("Nu a fost introdus niciun robot...")
                    else:
                        for robot in robots:
                            print(robot)

                elif command == 1:
                    str = input("Introduceti sirul de caractere: ")
                    result = self._control.filter_description(str)
                    if result == []:
                        print("Nu exista roboti care sa corespunda...")
                    else:
                        for robot in result:
                            print(robot)

                elif command == 2:
                    str = input("Introduceti coordonatele (format: x,y): ")
                    coord = str.strip().split(",")
                    x = coord[0]
                    y = coord[1]
                    result = []
                    for robot in self._control.get_robots():
                        robot_result = self._control.final_move(x, y, robot)
                        result.append(robot_result)

                    for robot in result:
                        print(robot)
                        

                elif command == 3:
                    id = int(input("ID: "))
                    baterie = int(input("BATERIE: "))
                    descriere = input("DESCRIERE: ")
                    moves = input("MUTARI: ")
                    self._control.add(id, baterie, descriere, moves)

                else: print("Optiune invalida!\n")

        except Exception as e:
            print(e)
            print("\n")

