Documentation – P2. Concurs

Statement

Creați o aplicație pentru gestiunea concurenților de la un concurs de programare. Programul înregistrează scorul obținut de fiecare concurent la 10 probe diferite, fiecare probă este notat cu un scor de la 1 la 10. Fiecare participant este identificat printr-un număr de concurs, scorul este ținut într-o listă unde concurentul 3 are scorul pe poziția 3 în listă . Programul trebuie sa ofere următoarele funcționalități:

1. Adaugă un scor la un participant.
    1.	Adaugă scor pentru un nou participant (ultimul participant)
    2. Inserare scor pentru un participant
2. Modificare scor.
    1. 	Șterge scorul pentru un participant dat
    2.	Șterge scorul pentru un interval de participanți
    3.	Înlocuiește scorul de la un participant
3. Tipărește lista de participanți.
    1.	Tipărește participanții care au scor mai mic decât un scor dat
    2.	Tipărește participanții ordonat după scor
    3.	Tipărește participanții cu scor mai mare decât un scor dat, ordonat după scor
4. Operații pe un subset de participanți.
    1.	Calculează media scorurilor pentru un interval dat 
    2.	Calculează scorul minim pentru un interval de participanți dat
    3.	Tipărește participanții dintr-un interval dat care au scorul multiplu de 10
5. Filtrare.
    1.	Filtrare participanți care au scorul multiplu unui număr dat
    2.	Filtrare participanți care au scorul mai mic decât un scor dat
6. Undo	- reface ultima operație (lista de scoruri revine la numerele ce existau înainte de ultima operație care a modificat lista)



Functionality list


Functionality	Description
    F1	        Adauga un scor la un participant nou
    F2	        Inserare scor pentru un participant
    F3	        Sterge scorul pentru un participant dat
    F4	        Sterge scorul pentru un interval de participanti
    F5	        Inlocuieste scorul de la un participant
    F6	        Tipareste participantii care au scor mai mic decat un scor dat
    F7	        Tipareste participantii ordonat dupa scor
    F8	        Tipareste participantii cu scor mai mare decta un scor dat, ordonat dupa scor
    F9	        Calculeaza media scorurilor pentru un interval dat
    F10	        Calculeaza scorul minim pentru un iterval de participanti dat
    F11	        Tipareste participantii dintr-un interval dat care au scorul multiplu de 10
    F12	        Filtrare participant care au scorul multiplu unui numar dat
    F13	        Filtrare participant care au scorul mia mic decat un scor dat
    F14	        Undo


Iteration plan 

Iteration	Planned features
    I1	    F1, F12, F13
    I2	    F3, F5, F6, F7, F8, F11, F4, F9, F10
    I3	    F2, F14


Runtime scenarios

Utilizator          Program	            Descriere
                    ...	                Printeaza meniul si afiseaza un mesaj pentru introducerea optiunii de catre utilizator
    1		                            Afiseaza cerintele functionalitatii 1 si un mesaj pentru introducerea numarului cerintei
    1		
                    ...	                Afiseaza mesaj pentru introducerea numarului unui participant
    1		                            Adauga in lista participantul cu numarul 1
                    ...                 Afiseaza mesaj pentru introducerea scorurilor la cele 10 probe
10 10...10                              Adauga scorul 10 (media probelor) pentru participantul 1
                    ...	                Afiseaza un mesaj cum ca participantul a fost adaugat in lista
    3		                            Se intoarce la meniul principal
    2		                            Afiseaza meniul pentru functionalitatea 2
    3               ...                 Afiseaza un mesaj pentru introducerea numarului unui participant 
    1               ...                 Afiseaza mesaj pentru reintroducerea scorurilor probelor
9 9 ... 9                               Modifica scorul participantului 1 cu 9 
                    ...                 Afiseaza mesaj pentru introducerea optiunii
    1                                   Afiseaza mesaj pentru introducerea numarului de participant
    1                                   Sterge scorul primului participant 
                    ...                 Afiseaza mesaj pentru introducerea optiunii
    0      Participant: 1 - Scor: None  Afiseaza lista de participanti

Tasks 

Functionality : F1

T1      citirea datelor participantului
T2      adaugarea participantului in lista

Functionality: F2

Functionality : F3

T1      citirea numarului participantului
T2      cautarea participantului in lista si stergerea scorului 

Functionality : F4

T1      citirea numerelor participantilor ce delimiteaza subsetul
T2      cautarea subsetului de participanti si stergerea scorurilor

Functionality : F5

T1      citirea numarului participnatului si a noului scor
T2      cautarea participnatului cu numarul citit si modificarea scorului

Functionality : F6

T1      citirea scorului de filtrare
T2      parcurgerea liste si gasirea participantilor cu scorul mai mic decat scorul dat
T3      tiparirea participantilor filtrati

Functionality : F7

T1      sortarea listei de participanti descrescator
T2      tiparirea listei de participanti

Functionality : F8

T1      citirea scorului de filtrare
T2      filtrarea partcipantilor cu scorul mai mic decat scorul dat
T3      sortarea listei de participanti filtrati descrescator
T4      tiparirea listei de participanti

Functionality : F9

T1      citirea numerelor participantilor ce delimiteaza subsetul
T2      calcularea sumei participantilor
T3      contorizarea participantilor
T4      calcularea mediei (suma // contor)

Functionality : F10

T1      citirea numerelor participantilor ce delimiteaza subsetul
T2      parcurgerea subsetului si aflarea scorului minim
T3      parcurgerea liste si salvarea participantului/participantilor cu scorul egal cu scorul minim

Functionality : F11

T1      citirea numerelor participantilor ce delimiteaza subsetul
T2      parcurgerea subsetului si salvarea participantilor cu scorul multiplu de 10
T3      tiparirea participantilor salvati

Functionlaity : F12

T1      citirea scorului de filtrare
T2      parcurgerea liste si salvarea participantilor cu scorul mai mic decat scorul citit 
T3      tiparirea participantilor salvati

Functionality : F13

T1      citirea numarului de filtrare
T2      parcurgerea liste si salvarea participantilor cu scorul multiplu numarului citit 
T3      tiparirea participantilor salvati    

Functionality : F14 
 
