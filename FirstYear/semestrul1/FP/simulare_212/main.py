from domain import *
from repository import *
from service import *
from user_interface import *

def main():
    imobil_repo = Imobil_Repo("/Users/croitoruandreea/Desktop/simulare_212/domino.txt")
    imobil_service = Imobil_Service(imobil_repo)
    ui = UI(imobil_service)

    ui.ui_main()

if __name__ == "__main__":
    main()
    