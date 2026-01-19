### Timpi de executie
#### Lab4
| Metoda      | Nr. threaduri | Nr. readers | Time (ms) |
|-------------|---------------|-------------|-----------|
| Secvențial  | –             | –           | 2         |
| Paralel     | 4             | 1           |   2       |
| Paralel     | 8             | 1           |   4       |
| Paralel     | 16            | 1           |   14      |
| Paralel     | 4             | 2           |    1      |
| Paralel     | 8             | 2           |    1      |
| Paralel     | 16            | 2           |   8       |

#### Lab5

1) Varianta citire fisier, note >= 0

| Metoda      | Nr. threaduri | Nr. readers | Time (ms) |
|-------------|---------------|-------------|-----------|
| Paralel     | 6             | 4           |   13       |
| Paralel     | 8             | 4           |   9       |
| Paralel     | 12            | 4           |   12      |


2) Varianta citire fiser, note >= -1

| Metoda      | Nr. threaduri | Nr. readers | Time (ms) |
|-------------|---------------|-------------|-----------|
| Paralel     | 6             | 4           |   12      |
| Paralel     | 8             | 4           |   7      |
| Paralel     | 12            | 4           |   11      |

3) Varianta citire din db

| Metoda      | Nr. threaduri | Nr. readers | Time (ms) |
|-------------|---------------|-------------|-----------|
| Paralel     | 6             | 4           |   17      |
| Paralel     | 8             | 4           |   12      |
| Paralel     | 12            | 4           |   9      |

### Metoda de implementare

1)  Varianta citire fisier, note >= 0

Implementarea foloseste un model producer-consumer cu o coada sincronizata: thread-urile reader citesc fisierele de proiect si introduc perechile (ID, nota) intr-o coada boundata protejata cu mutex si variabile de conditie. Thread-urile worker, gestionate printr-un ThreadPool, consuma continuu din coada si actualizeaza lista inlantuita globala folosind tehnica hand-over-hand locking — fiecare nod are propriul mutex, iar traversarea se face prin blocarea nodului curent si succesorului sau inainte de deblocare, asigurand astfel atomicitatea operatiilor de inserare sau actualizare a notelor. Dupa ce toti readerii termina citirea (monitorizat printr-un atomic counter), coada este marcata ca finalizata, iar workerii se opresc automat cand coada devine goala. In final, un al doilea ThreadPool paralelizeaza sortarea: fiecare worker insereaza cate un nod din lista initiala in SortedList (o lista thread-safe sortata descrescator dupa nota si ID), iar rezultatul final este scris in rezultateParalel.txt.

2) Varianta citire fiser, note >= -1

Implementarea extinde modelul producer-consumer prin tratarea speciala a notei -1: cand un worker extrage o pereche (ID, -1) din coada boundata, LinkedList detecteaza acest caz si executa doua operatii — adauga ID-ul intr-un vector protejat de mutex dedicat studentilor care au copiat, apoi elimina studentul din lista principala. Notele normale (≥ 0) sunt procesate identic cu varianta originala, prin actualizare sau inserare in lista. La final, dupa sortarea paralela si scrierea rezultatelor valide, programul extrage lista de copieri printr-o operatie thread-safe, o sorteaza si o afiseaza in fisierul studentiCopiat.txt.

3) Varianta citire din db

Implementarea substituie sursele de date cu conexiuni SQLite: fiecare thread reader primeste o lista de tabele (in loc de fisiere) si deschide propria conexiune independenta la baza de date studenti.db pentru a evita conflictele de acces concurent la resursa SQLite. Pentru fiecare tabel alocat, readerul executa un query SELECT, itereaza rezultatele si introduce perechile (ID, nota) in aceeasi coada boundata folosita in varianta cu fisiere. Workerii proceseaza identic datele. 

### Concluzii
 - implemenatrea secventiala este vizibil mai eficienta decat celelalte implementari.
 - cresterea numarului de threaduri, nu mai scade performanta.
 - tratarea notelor de -1 nu afecteaza performanta
 - varianta cu db se comporta diferit in cazul 1 (6 threaduri si 4 readers). este mai lenta, decat in celelate cazuri, probabil datorita overheadului cauzat de conexiunile la sqlite