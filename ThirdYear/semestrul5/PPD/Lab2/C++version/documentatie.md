# Documentatie Laborator 2 (Aplicare kernel pe o matrice fara matrice rezultat)

## Analiza

### 1. Secvential vs Paralel C++

*Implementarea secventiala este vizibil mai rapida pentru matricea de 10x10 si cea de 10000 x 10000, insa asemanatoare cu cea paralele pentru o matrice de 100x100.*<br>
*In implementarea paralela, cel mai optim timp de executie s-a optinut pentru 4-8 threaduri.*

### 2. Secvential vs Paralel Java

*Implementarea secventiala este vizibil mai rapida pentru toate cazuri.*<br>
*In implementarea paralela, cel mai optim timp de executie s-a optinut pentru 2-4 threaduri.*

### 4. C++ vs JAVA 

**Secvential** - *Pentru o matrice de 10000x10000, implementarea in Java este mai rapida. Pentru celelalte cazuri timpul este asemanator.*

**Paralel** - *Pentru o matrice de dimensiuni mici (10x10 sau 100x100), implementarea in C++ este mai rapida, insa pentru o matrice de dimensiuni mari (10000x10000), implementarea in Java este mai rapida*

## Timpi de executie (in ms)

### C ++
#### Secvential 

| Input                    |  Dinamic |
| :----------------------- |  ------: |
| N=M=10 si n=m=3          | 0.0052999|
| N=M=100 si n=m=3         | 0.349278 |
| N=M=10000 si n=m=3       | 3047.93  | 



#### Paralel 

| Input                     | Threaduri  |  Dinamic |
| :-----------------------  | :--------: | ------: |
| N=M=10 si n=m=3           |  4         |      0.0524206   |
| N=M=100 si n=m=3          |  2         |     0.268846  |   
| N=M=100 si n=m=3          |  4         |      0.221188 |   
| N=M=100 si n=m=3          |  8         |      0.364095 |    
| N=M=100 si n=m=3          |  16        |     0.438084  |      
| N=M=10000 si n=m=3        |  2         |    1795.75 |  
| N=M=10000 si n=m=3        |  4         |     998.534|  
| N=M=10000 si n=m=3        |  8         |   968.519  |  
| N=M=10000 si n=m=3        |  16        |   734.753  |  


### JAVA
#### Secvential 

| Input                    | Static     | 
| :----------------------- | :--------: |
| N=M=10 si n=m=3          |   0.058    | 
| N=M=100 si n=m=3         |    0.991   | 
| N=M=10000 si n=m=3       |  383.899  |

#### Paralel 

| Input                    | Threaduri  | Static   | 
| :----------------------- | :--------: |:--------:| 
| N=M=10 si n=m=3          |  4         |      1.064     |     
| N=M=100 si n=m=3         |  2         |    1.902   |    
| N=M=100 si n=m=3         |  4         |    2.538   |    
| N=M=100 si n=m=3         |  8         |    3.33   |    
| N=M=100 si n=m=3         |  16        |    3.03    |    
| N=M=10000 si n=m=3       |  2         |     211.328 |  
| N=M=10000 si n=m=3       |  4         |     141.825   |  
| N=M=10000 si n=m=3       |  8         |   164.449  |  
| N=M=10000 si n=m=3       |  16        |  186.945   |  

## Descriere rezolvare problema

Daca avem N linii si p threaduri, fiecare thread va primii N / p linii in cazul in care N % p == 0, in cazul in care exista un rest, atunci primele rest threaduri vor primii N / p + 1 linii. Threaduri in prim pas isi vor salva fiecare frontierele, mai exact linia anterioara si posteriora fata de cele primite. Fiecare thread v-a astepta, pana ce toate threaduri au facut aceasta copiere (lucru implementat cu ajutorul unei bariere). Dupa aceasta copiere, fiecare thread isi va calcula submatricea corespunzatoare. Calculul submatricii se va face cu ajutorul unor vectori auxiliari (prevLinie, currLinie, auxLinie). PrevLine face referire la linia anterioara liniei currente (prima linie in caz ca suntem pe prima linie, sau frontiera superioara in caz ca suntem pe alta linie). CurrLine este linia curenta pe care ne aflam, iar nextLinie este urmatoarea linie (ultima Linie in caz ca suntem cu currLinie e ultima linie sau frontiera posterioara altfel). Cu ajutorul acestor linii vom face calculul convolutiei pentru o anumita linie, salvand rezultatul in auxLinie

