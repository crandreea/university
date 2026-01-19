### Timpi de executie

| Metoda      | Nr. threaduri | Nr. readers | Time (ms) |
|-------------|---------------|-------------|-----------|
| Secvențial  | –             | –           | 2         |
| Paralel     | 4             | 1           |   2       |
| Paralel     | 8             | 1           |   4       |
| Paralel     | 16            | 1           |   14      |
| Paralel     | 4             | 2           |    1      |
| Paralel     | 8             | 2           |    1      |
| Paralel     | 16            | 2           |   8       |

### Metoda de implementare
Implementarea foloseste un model producer–consumer cu o coada sincronizata: thread-urile reader citesc fisierele de proiect si introduc perechile (ID, nota) intr-o coada protejata cu mutex, semnaland existenta elementelor printr-un counting semaphore. Thread-urile worker consuma continuu din coada si actualizeaza lista inlantuita globala, unde fiecare nod reprezinta un student — inserarea sau actualizarea notelor este realizata in sectiune critica, printr-un mutex, pentru a evita accesul concurent la lista. Dupa ce toti readerii termina citirea, se trimit in coada elemente speciale (-1, 0) pentru a opri workerii. La final, threadul principal scrie lista completa cu notele finale in fisierul rezultateParalel.txt si afiseaza timpul de executie.

### Concluzii
 - implemenatrea secventiala este vizibil mai eficienta decat celelalte implementari.
 - cresterea numarului de threaduri, scade performanta