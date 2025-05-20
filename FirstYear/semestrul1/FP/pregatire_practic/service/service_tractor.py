from repository.repo_tractor import *
from ui.validation import *

class ServiceTractor:
    def __init__(self, tractor_repo : RepoTractor, tractor_valid : Validator):
        self._tractor_repo = tractor_repo
        self._tractor_valid = tractor_valid

    def get_tractoare(self):
        return self._tractor_repo.get_all()
    
    def add(self, id, denumire, pret, model, data):
        tractor = Tractor(id, denumire, pret, model, data)
        self._tractor_valid.tractor_validation(tractor)
        self._tractor_repo.add_tractor(tractor)

    def delete(self, cifra : str):
        tractoare = self.get_tractoare()
        cnt_deleted = 0
        for tractor in tractoare:
            pret = tractor.get_pret()

            if str(cifra) in str(pret):
                cnt_deleted += 1
                self._tractor_repo.delete_tractor(tractor)

        return cnt_deleted

    


