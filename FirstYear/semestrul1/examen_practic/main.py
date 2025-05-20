from domain.spectacol import *
from repo.spectacol_repo import *
from srv.spectacol_srv import *
from ui.ui_main import *
from tests.test import *

def main():
    #test = Tests()
    valid = Validator()
    file_repo = FileRepo("/Users/croitoruandreea/Desktop/examen_practic/spectacole.txt")
    srv = SpectacolSrv(file_repo, valid)

    console = UI(srv)
    console.run()

if __name__ == "__main__":
    main()

