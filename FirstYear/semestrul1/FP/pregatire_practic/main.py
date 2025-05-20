from domain.tractor import *
from repository.repo_tractor import *
from service.service_tractor import *
from ui.ui_main import *
from ui.validation import *

def main():
   # repo = RepoTractor()
    validation = Validator()
    file = FileRepo("/Users/croitoruandreea/Desktop/FP/pregatire_practic/tractoare.txt")
    service = ServiceTractor(file, validation)
    console = UI(service)
    console.run()

if __name__ == "__main__":
    main()