from collections import deque

def citire(nume_fisier):
    neterminali = set()
    terminali = set()
    start = None
    reguli = {}
    
    try:
        with open(nume_fisier, 'r', encoding='utf-8') as f:
            linii = [l.strip() for l in f.readlines() if l.strip()]
            
            if len(linii) < 3:
                print("Eroare")
                return None, None, None, None
        
            neterminali = set(linii[0].split())
            terminali = set(linii[1].split())
            start = linii[2].strip()
        
            for i in range (3, len(linii)):
                linie = linii[i]
                
                if '->' in linie:
                    token = linie.split('->')
                    stanga = token[0].strip()
                    dreapta = token[1].strip()
                    
                    productii = [p.strip() for p in dreapta.split('|')]
                    
                    if stanga not in reguli:
                        reguli[stanga] = []
                        
                    reguli[stanga].extend(productii)
        
        return reguli, neterminali, terminali, start
        
    except FileNotFoundError:
        print(f"Eroare fisier")
        return None, None, None, None
        
    
def neterminal_productie(productie, neterminali):
    neterminali_gasiti = []
    for neterminal in neterminali:
        if neterminal in productie:
            neterminali_gasiti.append(neterminal)
    return neterminali_gasiti

def bfs(start, target, reguli, neterminali):
    if start == target:
        return True
    
    coada = deque([start])
    vizitate = set([start])
    
    while coada:
        neterminal_curent = coada.popleft()
        
        if neterminal_curent in neterminali:
            for productie in reguli[neterminal_curent]:
                neterminali_gasiti = neterminal_productie(productie, neterminali)
                
                for nt in neterminali_gasiti:
                    if nt == target:
                        return True
                    
                    if nt not in vizitate:
                        vizitate.add(nt)
                        coada.append(nt)
                        
    return False
            
def reguli_recursive(reguli, neterminali):
    recursive = []
    
    for neterminal in neterminali:
        for productie in reguli[neterminal]:
            
            if neterminal in productie:
                regula = f"{neterminal} -> {productie}"
                recursive.append(regula)
               
    for neterminal in neterminali:
        if neterminal in reguli:
            for productie in reguli[neterminal]:
                neterminali_dreapta = neterminal_productie(productie, neterminali)
                
                for nt in neterminali_dreapta:
                    if nt != neterminal and bfs(nt, neterminal, reguli, neterminali):
                        regula = f"{neterminal} -> {productie}"
                        if regula not in recursive:
                            recursive.append(regula)
                        
    return recursive


def afis(reguli, neterminali, terminali, start, recursive):
    print(f"Simboluri neterminale: {', '.join(neterminali)}")
    print(f"Simboluri terminale: {', '.join(terminali)}")    
    print(f"Simbol start: {start}")  
    print("Reguli de productie recursive\n")
    for regula in recursive:
        print(f"{regula}")   
        
def main():
    nume_fisier = "input.txt"
    reguli, neterminali, terminali, start = citire(nume_fisier)
    recursive = reguli_recursive(reguli, neterminali)
    afis(reguli, neterminali, terminali, start, recursive)
    
if __name__ == "__main__":
    main()