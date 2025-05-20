from service import *
from repository import *

def test_imobil():
    imobil = Imobil(1,"cluj",400,"vanzare")
    assert imobil.get_id() == 1
    assert imobil.get_adresa() == "cluj"
    assert imobil.get_pret() == 400
    assert imobil.get_oferta() == "vanzare"

def test_read_from_file():
    repo = Imobil_Repo("/Users/croitoruandreea/Desktop/simulare_212/test.txt")
    repo._elements.clear()
    repo.read_from_file()
    assert len(repo._elements) == 4

def test_average_price():
    repo = Imobil_Repo("/Users/croitoruandreea/Desktop/simulare_212/test.txt")
    repo._elements.clear()
    repo.read_from_file()
    service = Imobil_Service(repo)
    assert service.average_price("vanzare") == 300
    assert service.average_price("inchiriere") == 500

def test_tranzactii():
    repo = Imobil_Repo("/Users/croitoruandreea/Desktop/simulare_212/test.txt")
    repo._elements.clear()
    repo.read_from_file()
    service = Imobil_Service(repo)
    assert len(service.tranzactii("1",400)) == 1

if __name__ == "__main__":
    test_imobil()
    test_read_from_file()
    test_average_price()
    test_tranzactii()