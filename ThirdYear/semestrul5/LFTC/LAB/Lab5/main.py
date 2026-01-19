from grammar import Grammar
from slr_parser import SLRParser
from utils import incarca_atomi, incarca_fip, incarca_gramatica_din_fisier, incarca_secventa, map_fip_to_tokens

if __name__ == "__main__":
    
    print("\n === PARTEA 1: Analiza unei secvente simple === \n")
    
    try:
        file_secventa = "secventa.txt"
        seq_tokens = incarca_secventa(file_secventa)
        grammar_p1_text = incarca_gramatica_din_fisier("grammarSimple.txt")
        
        if seq_tokens:
            g1 = Grammar(grammar_p1_text)
            p1 = SLRParser(g1)
            p1.build_table()
            if p1.has_conflicts:
                print("\n [EROARE] Gramatica NU este SLR(1). Analiza nu poate fi aplicata.")
                exit(1)
                
            rezultat1 = p1.parse(seq_tokens)
            if rezultat1:
                print(" [SUCCES] Secventa acceptata!")
                print(" Sirul productiilor:", rezultat1)
            else:
                print("[EROARE] Secventa respinsa.")
        else:
            print("Nu s-a putut citi secventa.")
            
    except Exception as e:
        print(f"Eroare Partea 1: {e}")

    print("\n=== PARTEA 2: Analiza Minilimbajului ===\n")
    
    FILE_GRAMATICA = "grammarMLP.txt"
    FILE_ATOM = "atom.csv"
    FILE_FIP = "fip.txt"
    
    try:
        raw_grammar = incarca_gramatica_din_fisier(FILE_GRAMATICA)
        g2 = Grammar(raw_grammar)
        parser2 = SLRParser(g2)
        parser2.build_table()
        if parser2.has_conflicts:
            print("\n [EROARE] Gramatica NU este SLR(1). Analiza nu poate fi aplicata.")
            exit(1)
            
        atomi_dict = incarca_atomi(FILE_ATOM)
        fip_codes = incarca_fip(FILE_FIP)
        
        input_tokens = map_fip_to_tokens(fip_codes, atomi_dict)
        print(f" Tokeni: {input_tokens}\n") 
        
        rezultat2 = parser2.parse(input_tokens)
        
        if rezultat2:
            print("\n [SUCCES] Programul este corect sintactic!")
    
            with open("output_analiza.txt", "w") as f:
                for line in rezultat2:
                    f.write(line + "\n")
        else:
            print("\n [ESEC] Programul contine erori sintactice.")
            
    except Exception as e:
        print(f"   Eroare critica Partea 2: {e}")