import csv
import sys

def incarca_atomi(filepath):
    atomi = {}
    try:
        with open(filepath, 'r') as f:
            for line_idx, line in enumerate(f):
                line = line.strip()
                if not line: continue
                
                if "cod,atom" in line.lower():
                    continue
                
                parts = line.split(',', 1)

                if len(parts) >= 2:
                    cod_str = parts[0].strip()  
                    atom_text = parts[1].strip()
                    
                    try:
                        cod = int(cod_str)
                        atomi[cod] = atom_text
                    except ValueError:
                        print(f"   [SKIP Atom] Linia {line_idx}: Nu pot converti '{cod_str}' in numar.")
                        continue
                else:
                    print(f"   [SKIP Atom] Linia {line_idx}: Format incorect -> {line}")

        return atomi
    except FileNotFoundError:
        print(f"Eroare: Fisierul {filepath} nu a fost gasit.")
        sys.exit(1)

def incarca_fip(filepath):
    fip_codes = []
    try:
        with open(filepath, 'r') as f:
            for line_idx, line in enumerate(f):
                line = line.strip()
                if not line: continue
                
                if ',' in line:
                    parts = line.rsplit(',', 2)
                    if len(parts) >= 2:
                        cod_str = parts[-2].strip()
                    else:
                        print(f"   [SKIP FIP] Linia {line_idx} (format cu virgula invalid): {line}")
                        continue
                else:
                    parts = line.split()
                    if len(parts) >= 2:
                        cod_str = parts[1].strip()
                    else:
                        print(f"   [SKIP FIP] Linia {line_idx} (format spatiu invalid): {line}")
                        continue

                try:
                    cod = int(cod_str)
                    fip_codes.append(cod)
                except ValueError:
                    print(f"   [SKIP FIP] Linia {line_idx}: '{cod_str}' nu e numar.")
                    continue
                    
        return fip_codes
    except FileNotFoundError:
        print(f"Eroare: Fisierul {filepath} nu a fost gasit.")
        sys.exit(1)

def incarca_gramatica_din_fisier(filepath):
    try:
        with open(filepath, 'r') as f:
            return f.read()
    except FileNotFoundError:
        print(f"Eroare: Fisierul {filepath} nu a fost gasit.")
        sys.exit(1)

def incarca_secventa(filepath):
    try:
        with open(filepath, 'r') as f:
            content = f.read().strip()
            return content.split()
        
    except FileNotFoundError:
        print(f"Eroare: Fisierul {filepath} nu a fost gasit.")
        return []


def map_fip_to_tokens(fip_codes, atomi_dict):
    tokens = []
    
    translation_map = {
        '#': 'HASH',
        'include': 'INCLUDE',
        'iostream': 'IOSTREAM',
        'using': 'USING',
        'namespace': 'NAMESPACE',
        'std': 'STD',
        'int': 'INT',
        'float': 'FLOAT',
        'main': 'ID',       
        'write': 'WRITE',
        'read': 'READ',     
        'if': 'IF',
        'while': 'LOOP',
        
        ';': 'SEMI',
        ',': 'COMMA',
        '(': 'LPAREN',
        ')': 'RPAREN',
        '{': 'LBRACE',
        '}': 'RBRACE',
        '<': 'LT',
        '>': 'GT',
        '<<': 'SHL',
        '>>': 'SHR',
        '<=': 'LEQ',
        '>=': 'GEQ',
        '==': 'EQ',
        '!=': 'NEQ',
        '+': 'PLUS',
        '-': 'MINUS',
        '*': 'MUL',
        '%': 'MOD',
        '<-': 'ASSIGN'      
    }

    for code in fip_codes:
        if code not in atomi_dict:
            continue
            
        atom_name = atomi_dict[code]
        
        if code == 100: 
            tokens.append("ID")
        elif code == 101:
            tokens.append("CONSTVAL")
        elif atom_name in translation_map:
            tokens.append(translation_map[atom_name])
        else:
            tokens.append(atom_name)

    return tokens
