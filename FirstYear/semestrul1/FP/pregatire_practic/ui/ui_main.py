from service.service_tractor import * 
class UI:
    def __init__(self, tractor_service : ServiceTractor) -> None:
        self._trcator_service = tractor_service

    def run(self):
        try:
            while True:
                option = input("Introduceti optiunea: ")
                option = int(option)
                if option == 1:
                    id = input("id: ")
                    nume = input("nume: ")
                    pret = input("pret: ")
                    model = input("model: ")
                    data = input("data: ")
                    self._trcator_service.add(id, nume, pret, model, data)

                elif option == 2:
                    cifra = input("introduceti cifra: ")
                    numar = self._trcator_service.delete(cifra)
                    print(f"Numarul de tractoare sterse este: {numar}")

                elif option == 3:
                    for e in self._trcator_service.get_tractoare():
                        print(e)
                
                else:
                    print("Optiune invalida!")

        except Exception as e:
            print(e)
            UI.run(self)