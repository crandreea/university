from service import *

class UI:
    def __init__(self, imobil_service : Imobil_Service) -> None:
        self._imobil_service = imobil_service

    def print_menu(self):
        print("""
            1. Media de pret in functie de tipul de oferta
            2. Tranzactii
              """)
        
    def ui_main(self):
        try:
            self.print_menu()
            while True:
                command = input("Introduceti comanda: ")
                command = int(command)

                if command == 0:
                    for element in self._imobil_service.get_imobils():
                        print(element)

                elif command == 1:
                    oferta = input("Introduceti tipul de oferta: ")
                    average = self._imobil_service.average_price(oferta)
                    print(f"Pentru {oferta} pretul mediu este: {average}\n")

                elif command == 2:
                    id = input("Introduceti id-ul proprietatii: ")
                    pret = input("Introduceti pretul negociat: ")
                    result = self._imobil_service.tranzactii(id, pret)
                    for tranzactie in result:
                        print(tranzactie)
                

                else: print("Optiune invalida!\n")
        except Exception as e:
            print(e)
            print("\n")
            self.ui_main()