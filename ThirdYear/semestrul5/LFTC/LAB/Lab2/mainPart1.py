from automat_finit import AutomatFinit 
# Descriere automat finit (in EBNF)
#
# af = alfabet_stari, alfebet_de_intrare, tranzitii, stare_initiala, lista_stari_finale 
# alfabel_stari = stare{" ", stare} 
# alfabet_de_intrare = digit{" ", digit} | string{" ",string}
# tranzitii = tranzitie {"\n", tranzitie}
# stare_initiale = stare
# lista_stari_finale = stare{" ", stare}
# string = [a-z0A-z]
# digit = [0-9]
# const = digit | string
# tranzitie = stare const stare
# stare = string{string} | string{digit}

def main():
    af = AutomatFinit()
    print("Alege modul de citirea al inputului: 0 - fisier, 1 - consola")
    mod = input().strip()

    if mod == '0':
        af.citire_din_fisier("inClass.txt")
    elif mod == '1':
        af.citire_din_consola()
    else:
        print("Input gresit (0 sau 1)")
    
    af.afisare_af()

    determinist = af.verifica_determinist()
    if determinist == False:
        print("AF este nedeterminist")
    else:
        print("Introdu o secventa (fara spatii):")
        secventa = input().strip()
        
        af.verifica_secventa(secventa)
        af.cel_mai_lung_prefix_acceptat(secventa)
    
if __name__ == "__main__":
    main()