# STATEMENT

Scrieți o aplicație pentru gestiunea notelor și a problemelor de laborator pentru o disciplină.
Aplicația gestionează:
     studenți: <studentID>,<nume>,<grup>
     problemă laborator: <număr laborator_problemă>,<descriere>, <deadline>
Creați o aplicație care permite gestiunea listei de studenți și probleme de laborator.
     adaugă, șterge, modifică, lista de studenți, listă de probleme
     căutare student, căutare problemă
     Asignare laborator/Notare laborator
     Creare statistici:
         lista de studenți și notele lor la o problema de laborator dat, ordonat: alfabetic după nume,după notă.
         Toți studenții cu media notelor de laborator mai mica decât 5. (nume student și notă)


# FUNCTIONALITY LIST

Functionality	Description

    F1          Adaugare student
    F2          Adaugare problema
    F3          Stergere student
    F4          Stergere problema
    F5          Modificare nume student
    F6          Modificare grup student
    F7          Cautare student
    F8          Cautare proba
    F9          Notarea unui student la o problema
    F10         Modificare deadline problema
    F11         Afisare studenti cu media notelor mai mica decat 5
    F12         Afisare studenti si notele lor in functie de un laborator (ordonat dupa nume si dupa nota)
    F13         Modificare descriere problema

# ITERATION PLAN 

Iteration	Planned features

    I1      F1, F2, F3, F4, F5, F6
    I2      F7, F8, F9, F10, F13
    I3      F11, F12

# RUNTIME SCENARIOS

Functionality F1 Adaugare student

Utilizator                Program                    Descriere
    ...                0 - afiseaz..            afiseaza meniul
    ...               Introduceti opt..         afiseaza mesaj pentru introducerea optiunii
    1                 Introduceti urm..         afiseaza mesaj pentru introducerea datelor
    ...               ID:                       afiseaza mesaj pentru introducerea datelor
    1                 NAME:                     afiseaza mesaj pentru introducerea datelor
    Andreea           GROUP:                    afiseaza mesaj pentru introducerea datelor
    212               Introduceti opt..         afiseaza mesaj pentru introducerea optiunii
    1                 Introduceti urm..         afiseaza mesaj pentru introducerea datelor
    ...               ID:                       afiseaza mesaj pentru introducerea datelor
    1                 NAME:                     afiseaza mesaj pentru introducerea datelor
    Croitoru          GROUP:                    afiseaza mesaj pentru introducerea datelor
    214               Studentul cu ID-ul 1..    afiseaza mesaj pentru ca studentul exista deja in lista

Functionality F2 Adaugare problema

Utilizator                Program                    Descriere
    ...                0 - afiseaz..            afiseaza meniul
    ...               Introduceti opt..         afiseaza mesaj pentru introducerea optiunii
    1                 Introduceti urm..         afiseaza mesaj pentru introducerea datelor
    ...               ID:                       afiseaza mesaj pentru introducerea datelor
    1                 DESCRIPTION:              afiseaza mesaj pentru introducerea datelor
    add               DEADLINE:                 afiseaza mesaj pentru introducerea datelor
    2023/12/03        Introduceti opt..         afiseaza mesaj pentru introducerea optiunii
    1                 Introduceti urm..         afiseaza mesaj pentru introducerea datelor
    ...               ID:                       afiseaza mesaj pentru introducerea datelor
    1                 DESCRIPTION:              afiseaza mesaj pentru introducerea datelor
    delete            DEADLINE:                 afiseaza mesaj pentru introducerea datelor
    2024/01/02        Problema 1 exist..        afiseaza mesaj pentru ca problema exista deja in lista

Functionality F3 Stergere student

Utilizator                Program                    Descriere
    ...               0 - afiseaz..             afiseaza meniul
    ...               Introduceti opt..         afiseaza mesaj pentru introducerea optiunii
    2                 Introduceti urm..         afiseaza mesaj pentru introducerea datelor
    ...               ID:                       afiseaza mesaj pentru introducerea datelor
    1                 Studentul a fost..        afiseaza mesaj cum ca studentul a fost sters

Functionality F4 Stergere problema

Utilizator                Program                    Descriere
    ...               0 - afiseaz..             afiseaza meniul
    ...               Introduceti opt..         afiseaza mesaj pentru introducerea optiunii
    2                 Introduceti urm..         afiseaza mesaj pentru introducerea datelor
    ...               ID:                       afiseaza mesaj pentru introducerea datelor
    1                 Problema a fost..         afiseaza mesaj cum ca problema a fost stearsa

Functionality F5 Modificare nume student

Utilizator                Program                    Descriere
    ...               0 - afiseaz..             afiseaza meniul
    ...               Introduceti opt..         afiseaza mesaj pentru introducerea optiunii
    3                 Introduceti urm..         afiseaza mesaj pentru introducerea datelor
    ...               ID:                       afiseaza mesaj pentru introducerea datelor
    1                 NOUL NUME:                afiseaza mesaj pentru introducerea datelor
    Eric              ...                       numele studnetului a fost modificat

Functionality F6 Modificare grup student

Utilizator                Program                    Descriere
    ...               0 - afiseaz..             afiseaza meniul
    ...               Introduceti opt..         afiseaza mesaj pentru introducerea optiunii
    4                 Introduceti urm..         afiseaza mesaj pentru introducerea datelor
    ...               ID:                       afiseaza mesaj pentru introducerea datelor
    1                 NOUA GRUPA:               afiseaza mesaj pentru introducerea datelor
    217               ...                       grupa studnetului a fost modificat

Functionality F7 Cautare student

Utilizator                Program                    Descriere
    ...               0 - afiseaz..             afiseaza meniul
    ...               Introduceti opt..         afiseaza mesaj pentru introducerea optiunii
    5                 ID:                       afiseaza mesaj pentru introducerea id ului
    1                 Studentul cautat es..     afiseaza studnetul cu id-ul 1

Functionality F8 Cautare problema

Utilizator                Program                    Descriere
    ...               0 - afiseaz..             afiseaza meniul
    ...               Introduceti opt..         afiseaza mesaj pentru introducerea opt
    5                 ID:                       afiseaza mesaj pentru introducerea id ului
    1                 Problema cautata es..     afiseaza problema cu id-ul 1

Functionality F9 Asignare laborator

Utilizator                Program                    Descriere
...               0 - afiseaz..             afiseaza meniul
...               Introduceti opt..         afiseaza mesaj pentru introducerea optiunii
1                 Introduceti datele..      afiseaza mesaj pt introducere id-ului studnetului si id-ul problemei
...               ID student:               afiseaza mesaj pentru introducerea id ului studnetului
1                 ID problema:
...               Asignarea a fost real...  afiseaza mesaj cum ca asignarea s-a realizta cu succes
...               Introduceti opt..         afiseaza mesaj pentru introducerea unei optiuni
0                 ID student:
1                 Problemele asignate st.. afiseaza problemele asignate studnetului cu id-ul 1

Functionality F10  Notare laborator

Utilizator                Program                    Descriere
...               0 - afiseaz..             afiseaza meniul
...               Introduceti opt..         afiseaza mesaj pentru introducerea optiunii
1                 Introduceti datele..      afiseaza mesaj pt introducere id-ului studnetului si id-ul problemei
...               ID student:               afiseaza mesaj pentru introducerea id ului studnetului
1                 ID problema:
2                 NOTA:
10                Notarea a fost real...    afiseaza mesaj cum ca asignarea s-a realizta cu succes
...               Introduceti opt..         afiseaza mesaj pentru introducerea unei optiuni
0                 ID student:
1                 Notele studentului...     afiseaza notele studentului cu id-ul 1

Functionality F11 Modificare deadline problema
Utilizator                Program                    Descriere
...               0 - afiseaz..             afiseaza meniul
...               Introduceti opt..         afiseaza mesaj pentru introducerea optiunii
...               ID problem:               afiseaza mesaj pentru introducerea id ului problemelor
...               Noul deadline..           afiseaza mesaj pentru introducerea deadlinului
...               Deadline modificate cu .. afiseaza mesaj cum ca deadline-ul s-a modificat cu succes

Functionality F12 Afisare studenti cu media notelor mai mica decat 5
Utilizator                Program                    Descriere
...               0 - afiseaz..             afiseaza meniul
...               Introduceti optiunea
2                 Studentul: .. Media:..    afiseaza studnetii cu media mai mica decat 5 si media lor

Functionality F13 Afisare studenti si notele lor in functie de un laborator (ordonat dupa nume si dupa nota)
Utilizator                Program                    Descriere
...               0 - afiseaz..             afiseaza meniul
...               Introduceti optiunea
1                 Laboratorul:              afiseaza mesaj pentru introducere id ului problmei 
2                 Eric: 5                   afiseaza studnetii si nota lor la problem adat a


