class AutomatFinit:
    def __init__(self):
        self.stari = []
        self.alfabet = []
        self.stare_initiala = None
        self.stari_finale = []
        self.tranzitii = {}  # cheie (stare, simbol) si valoare [destinatii]

    def verifica_determinist(self):
        for (sursa, simbol), destinatii in self.tranzitii.items():
            if len(destinatii) > 1:
                return False
        return True
    
    def citire_din_fisier(self, path_file):
        with open(path_file, 'r', encoding='utf-8') as f:
            linii = [linie.strip() for linie in f.readlines() if linie.strip()]

        if len(linii) < 5:
            raise ValueError("Lipseste un elemnt al AF")

        self.stari = linii[0].split()
        self.alfabet = linii[1].split()
        self.stare_initiala = linii[2].strip()
        self.stari_finale = linii[3].split()
        self.tranzitii = {}
        for linie in linii[4:]:
            elem = linie.split()
            if len(elem) != 3:
                raise ValueError(f"Linie invalida:'{linie}'")
            
            sursa, simbol, destinatie = elem
            if sursa not in self.stari or destinatie not in self.stari:
                raise ValueError(f"Tranzitie invalida: {sursa} → {destinatie}")
            
            if simbol not in self.alfabet:
                raise ValueError(f"Simbolul '{simbol}' nu este in alfabet")

            self.tranzitii.setdefault((sursa, simbol), set()).add(destinatie)

    def citire_din_consola(self):
        print("Lista starilor: ")
        self.stari = input().split()
        while not self.stari:
            print("Trebuie sa existe cel putin o stare. Repeta introducerea:")
            self.stari = input().split()

        print("Alfabetul: ")
        self.alfabet = input().split()
        while not self.alfabet:
            print("Alfabetul nu poate fi gol. Repeta introducerea:")
            self.alfabet = input().split()

        print("Starea initiala: ")
        self.stare_initiala = input().strip()
        while not self.stare_initiala in self.stari:
            print("Starea initiala trebuie sa se regaseasca in lista starilor. Repeta introducerea:")
            self.stare_initiala = input().strip()

        print("Lista starilor finale: ")
        self.stari_finale = input().split()
        while any(stare_finala not in self.stari for stare_finala in self.stari_finale):
            print("Toate starile finale trebuie sa se regaseasca in lista starilor. Repeta introducerea:")
            self.stari_finale = input().split()

        print("Tanzitii [sursa simbol destinatie]:\n")
        self.tranzitii = []
        while True:
            linie = input().strip()
            if not linie:
                break
            elem = linie.split()
            if len(elem) != 3:
                print("Tranzitie e sub forma sura simbol destinatie")
                continue

            sursa, simbol, destinatie = elem

            if sursa not in self.stari or destinatie not in self.stari:
                print(f"Tranzitie invalida: {sursa} → {destinatie}")
                continue

            if simbol not in self.alfabet:
                print(f"Simbolul {simbol} nu exista")
                continue

            self.tranzitii.setdefault((sursa, simbol), set()).add(destinatie)

        if not self.tranzitii:
            print("Nu a fost introdusa nicio tranzitie")

    def afisare_af(self):
        print("\n--- Automat finit ---")
        print("Stari:", self.stari)
        print("Alfabet:", self.alfabet)
        print("Stare initiala:", self.stare_initiala)
        print("Stari finale:", self.stari_finale)
        print("Tranzitii:")
        for (sursa, simbol), destinatii in self.tranzitii.items():
            for dst in destinatii:
                print(f"  {sursa} --{simbol}--> {dst}")

    def verifica_secventa(self, secventa):
        stare_curenta = self.stare_initiala
        for simbol in secventa:
            if simbol not in self.alfabet:
                print(f"Secventa nu este acceptata. Simbol invalid: '{simbol}'")
                return False
            
            if (stare_curenta, simbol) not in self.tranzitii:
                print(f"Secventa nu este acceptata. Lipsa tranzitie pentru ({stare_curenta}, '{simbol}').")
                return False
            
            stare_curenta = list(self.tranzitii[(stare_curenta, simbol)])[0]
            print(f"  {simbol} → {stare_curenta}")

        if stare_curenta in self.stari_finale:
            print(f"Secventa este acceptata")
            return True
        else:
            print(f"Secventa nu este acceptata (s-a ajuns in {stare_curenta})")
            return False

    def cel_mai_lung_prefix_acceptat(self, secventa):
        stare_curenta = self.stare_initiala
        prefix_acceptat = ""
        prefix_curent = ""

        for simbol in secventa:
            if simbol not in self.alfabet:
                break

            if (stare_curenta, simbol) not in self.tranzitii:
                break

            stare_curenta = list(self.tranzitii[(stare_curenta, simbol)])[0]
            prefix_curent += simbol

            if stare_curenta in self.stari_finale:
                prefix_acceptat = prefix_curent

        # if prefix_acceptat:
        #     print(f"Cel mai lung prefix acceptat: '{prefix_acceptat}'")
        # else:
        #     print("Niciun prefix al secventei nu este acceptat")

        return prefix_acceptat