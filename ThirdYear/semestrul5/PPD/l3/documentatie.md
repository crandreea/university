## Timpi de executie
| implementation       | digits_1 | digits_2 | processes | execution_time_ms |
|-----------------------|----------|-----------|------------|-------------------|
| standard              | 16       | 16        | 5          | 0.59              |
| standard              | 10000    | 10000     | 5          | 0.819             |
| standard              | 10000    | 10000     | 9          | 2.673             |
| standard              | 10000    | 10000     | 17         | 5.838             |
| standard              | 100      | 100000    | 5          | 3.685             |
| standard              | 100      | 100000    | 9          | 9.605             |
| standard              | 100      | 100000    | 17         | 19.889            |
| scatter               | 16       | 16        | 4          | 0.31              |
| scatter               | 1000     | 1000      | 4          | 0.221             |
| scatter               | 1000     | 1000      | 8          | 8.034             |
| scatter               | 1000     | 1000      | 16         | 1.337             |
| scatter               | 100      | 100000    | 4          | 3.441             |
| scatter               | 100      | 100000    | 8          | 1.216             |
| scatter               | 100      | 100000    | 16         | 5.743             |
| async                 | 16       | 16        | 5          | 0.456             |
| async                 | 10000    | 10000     | 5          | 1.185             |
| async                 | 10000    | 10000     | 9          | 2.412             |
| async                 | 10000    | 10000     | 17         | 5.937             |
| async                 | 100      | 100000    | 5          | 3.848             |
| async                 | 100      | 100000    | 9          | 6.217             |
| async                 | 100      | 100000    | 17         | 9.31              |
| sequential            | 16       | 16        | 1          | 0.0               |
| sequential            | 10000    | 10000     | 1          | 0.032             |
| sequential            | 100      | 100000    | 1          | 0.255             |
| sequential            | 1000     | 1000      | 1          | 0.002             |
| sequential            | 4        | 4         | 1          | 0.0               |
| standard_optimized    | 16       | 16        | 5          | 0.438             |
| standard_optimized    | 10000    | 10000     | 5          | 1.067             |
| standard_optimized    | 10000    | 10000     | 9          | 2.15              |
| standard_optimized    | 10000    | 10000     | 17         | 3.858             |
| standard_optimized    | 100      | 100000    | 5          | 6.344             |
| standard_optimized    | 100      | 100000    | 9          | 7.61              |
| standard_optimized    | 100      | 100000    | 17         | 23.383            |


## Explicatie implementari
### varianta 1
*procesul 0* : 
 - seteaza o variabila id_proces_curent=1 
 - repeta urmatoarele actiuni pana cand se citesc toate cifrele numerelor:
    - citeste cate N/(p-1) cifre din cele 2 fisiere // Atentie -procesul 0 nu citeste toate cifrele la inceput 
    - le trimite procesului “id_proces_curent” 
    - incrementeaza “id_proces_curent” 
 - primeste cifrele sumei de la fiecare proces cu id>0 
 - dupa ce primeste un segment de cifre de la un process il scrie in fisier

*procesele cu id>0* :
 - fac suma cifrelor primite si calculeaza “report” (carry) corespunzator; 

*procesele cu id>0 si id<p* :
 - trimit “reportul” la procesul urmator care il foloseste pentru actualizarea rezultatului

*procesele cu id>1*:
 - trimit cifrelor sumei pe care le-a calculat catre procesul 0

### varianta 1.1
- asemanator **varianta 1**, diferenta este ca workeriii nu asteapta dupa carry. calculeaza o suma "partiala", mai apoi primesc carry ul, iar daca acesta este diferit de 0, recalculeaza suma 

### varianta 2
*procesul 0* 
- citeste cele 2 numere si le stocheaza in 2 tablouri: daca un numar are mai putine cifre se completeaza cu cifre nesemnificative 
- cifrele celor 2 numere se distribuire proceselor folosind MPI_Scatter  
- procesele fac suma cifrelor primite si calculeaza “report” (carry) pe care il trimit procesului urmator (cu exceptia ultimului proces care nu trimite carry) 
- rezultatul final se obtine in procesul 0 (se foloseste MPI_Gather) 

### varianta 3
- asemanator **varianta 1**, diferenta este ca folosesc MPI_ISend si MPI_IRecv (nu stim ordinea in care se trimit / primesc datele) si Waitall dupa fiecare Recv pentru a fi sigura ca primesc toate datele necesare 

## Concluzii 
- varianta secventiala este cea mia eficienta in toate cazurile de test 
- dintre variantele cu MPI, pentru testele cu numere foarte mari (100000), varianta scatter cu 16 procese a fost cea mia eficienta 
- variata asincrona este de aprox 2 ori mai lenta decat celelalte implementari cu MPI
- varianta optimizata este mai optima dpdv al timpului de executie dar nu pt mai multe procese (17)