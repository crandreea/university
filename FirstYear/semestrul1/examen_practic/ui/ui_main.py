from srv.spectacol_srv import *
class UI:
    def __init__(self, srv_spectacol : SpectacolSrv) -> None:
        self.__s_srv = srv_spectacol

    def ui_add(self):
        """
        Partea de interfata pentru functia de adaugare
        """
        try:
            print("Introduceti datele: ")
            titlu = input("<TITLU> ")
            artist = input("<ARTIST> ")
            gen = input("<GEN> ")
            durata = input("<DURATA> ")
            spectacol = Spectacol(titlu, artist, gen, durata)
            self.__s_srv.add(spectacol)
            print("Spectacolul a fost introdus cu succes! \n")

        except Exception as e:
            print(e)
            UI.ui_add(self)

    def ui_modify(self):
        """
        Partea de interfata pentru functia de modificare
        """
        try:
            print("Introduceti datele noi: ")
            titlu = input("<TITLU> ")
            artist = input("<ARTIST> ")
            gen = input("<NOUL GEN> ")
            durata = input("<NOUA DURATA> ")
            self.__s_srv.modify(titlu, artist, gen, durata)
            print("Spectacolul a fost modificat!\n")

        except Exception as e:
            print(e)
            UI.ui_modify(self)

    def run(self):

        try:
            print("""
                  1.Adaugare
                  2.Modificare
                  3.Random
                  4.Export
                  5.Afisare
                  """)
            while True:
                option = input("Introduceti optiunea: ")
                option = int(option)
                if option == 1:
                    self.ui_add()

                elif option == 2:
                    self.ui_modify()

                elif option == 3:
                    cnt = input("Introduceti numarul de spectacole generate aleator: ")
                    cnt = int(cnt)
                    result = self.__s_srv.random(cnt)             

                elif option == 4:
                    file_name = input("Introduceti numele fisierului: ")
                    file_path = "/Users/croitoruandreea/Desktop/examen_practic/" + file_name + ".txt"

                else:
                    print("Optiune invalida...")

        except Exception as e:
            print(e)
            UI.run(self)
