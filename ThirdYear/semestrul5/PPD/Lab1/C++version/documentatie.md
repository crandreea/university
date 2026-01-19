# Documentatie Laborator 1 (Aplicare kernel pe o matrice)

## Analiza

### 1. Secvential vs Paralel C++
Pentru matrici de dimensiuni mici, costul crearii threadurilor depaseste beneficiile paralelizarii. Pentru matrci mai mari insa, se merita utilizarea threadurilor (~8-16)

### 2. Secvential vs Paralel Java
Asemanator cu C++, doar ca aici diferenta este mult mai mare (de ordinul secundelor / minutelor)

### 3. Paralel vs Paralel

Verticala - putin mai lenta pentru 16 threaduri, cel mai performant la 4-8

Orizontala - mai rapida pentru valori mari 

Blocuri - asemanatoare cu cea orizonatale, mai eficienta totusi pentru valori mai mici

### 4. C++ vs JAVA

Secvential - Java putin mai rapid pentru matrici mari

Paralel - Java este mult mai lent (de ordinul secundelor / minutelor)

## Timpi de executie (in ms)

### C ++
#### Secvential 

| Input                    | Static     | Dinamic |
| :----------------------- | :--------: | ------: |
| N=M=10 si n=m=3          |   0.004083 | 0.001625|
| N=M=1000 si n=m=5        |   45.48  | 54.07 |
| N=10 M=10000 si n=m=5    |  2.67     | 3.53 |
| N=10000 M=10 si n=m=5    |  8.60     | 3.31 |
| N=10000 M=10000 si n=m=5 |  4700.68   | 5776.75 |



#### Paralel 
- pe verticala

| Input                    | Threaduri  | Static   | Dinamic |
| :----------------------- | :--------: |:--------:| ------: |
| N=M=10 si n=m=3          |  4         |     0    |     0   |
| N=M=1000 si n=m=5        |  2         |     8.40  |    0    |
| N=M=1000 si n=m=5        |  4         |      5.50 |    0    |
| N=M=1000 si n=m=5        |  8         |      6.40 |    0    |
| N=M=1000 si n=m=5        |  16        |     5.90  |    0    |
| N=10 M=10000 si n=m=5    |  2         |     0    |    0    |
| N=10 M=10000 si n=m=5    |  4         |     0    |    0    |
| N=10 M=10000 si n=m=5    |  8         |     0    |    0    |
| N=10 M=10000 si n=m=5    |  16        |     0    |     0   |
| N=10000 M=10 si n=m=5    |  2         |   20.10   |    0    |
| N=10000 M=10 si n=m=5    |  4         |    17.20  |    0    |
| N=10000 M=10 si n=m=5    |  8         |     18.80 |     0   |
| N=10000 M=10 si n=m=5    |  16        |    17.80  |     0   |
| N=10000 M=10000 si n=m=5 |  2         |   865.80  |  2066.30 |
| N=10000 M=10000 si n=m=5 |  4         |   642.70  |  1699.70 |
| N=10000 M=10000 si n=m=5 |  8         |   580.20  |  1512.20 |
| N=10000 M=10000 si n=m=5 |  16        |   645.40  |  1531.20 |


- pe orizontala


| Input                    | Threaduri  | Static       | Dinamic |
| :----------------------- | :--------: |:------------:| ------: |
| N=M=10 si n=m=3          |  4         |     5.10      |     0   |
| N=M=1000 si n=m=5        |  2         |     7.70      |    10.60 |
| N=M=1000 si n=m=5        |  4         |      5.10     |    7.10  |
| N=M=1000 si n=m=5        |  8         |      3.60     |    5.10  |
| N=M=1000 si n=m=5        |  16        |     3.80      |    5.00  |
| N=10 M=10000 si n=m=5    |  2         |     10.50     |    0    |
| N=10 M=10000 si n=m=5    |  4         |     8.10      |    0.10   |
| N=10 M=10000 si n=m=5    |  8         |     8.40      |    0    |
| N=10 M=10000 si n=m=5    |  16        |     10.00     |     0   |
| N=10000 M=10 si n=m=5    |  2         |   743.80      |    0.10   |
| N=10000 M=10 si n=m=5    |  4         |    457.20     |    0    |
| N=10000 M=10 si n=m=5    |  8         |      327.50   |     0   |
| N=10000 M=10 si n=m=5    |  16        |    317.30     |     0   |
| N=10000 M=10000 si n=m=5 |  2         |    756.10     |  1169.10 |
| N=10000 M=10000 si n=m=5 |  4         |     465.60    |  667.40  |
| N=10000 M=10000 si n=m=5 |  8         |     333.80    |  554.80  |
| N=10000 M=10000 si n=m=5 |  16        |    323.90     |  473.10  |


- pe blocuri

| Input                    | Threaduri  | Static       | Dinamic |
| :----------------------- | :--------: |:------------:| ------: |
| N=M=10 si n=m=3          |  4         |      0     |     0   |
| N=M=1000 si n=m=5        |  2         |      15.20     |  14.00   |
| N=M=1000 si n=m=5        |  4         |      4.90     |   5.90   |
| N=M=1000 si n=m=5        |  8         |       5.30    |  5.20    |
| N=M=1000 si n=m=5        |  16        |       3.70    |  3.30    |
| N=10 M=10000 si n=m=5    |  2         |       0.10   |   0     |
| N=10 M=10000 si n=m=5    |  4         |         0  |   0    |
| N=10 M=10000 si n=m=5    |  8         |          0 |    0    |
| N=10 M=10000 si n=m=5    |  16        |        0  |     0   |
| N=10000 M=10 si n=m=5    |  2         |    12.10     |   0    |
| N=10000 M=10 si n=m=5    |  4         |    11.20     |   0    |
| N=10000 M=10 si n=m=5    |  8         |     8.90    |    0    |
| N=10000 M=10 si n=m=5    |  16        |     9.30    |    0    |
| N=10000 M=10000 si n=m=5 |  2         |      1453.60   |  1533.10 |
| N=10000 M=10000 si n=m=5 |  4         |      454.70   |  449.70  | 
| N=10000 M=10000 si n=m=5 |  8         |    442.20     |  461.90  |
| N=10000 M=10000 si n=m=5 |  16        |     325.40    |  329.90  |


### JAVA
#### Secvential 

| Input                    | Static     | 
| :----------------------- | :--------: |
| N=M=10 si n=m=3          |   0.026    | 
| N=M=1000 si n=m=5        |   38.938   | 
| N=10 M=10000 si n=m=5    |  7.09      | 
| N=10000 M=10 si n=m=5    |  7.37      | 
| N=10000 M=10000 si n=m=5 |  3457.036  |

#### Paralel 
- pe verticala

| Input                    | Threaduri  | Static   | 
| :----------------------- | :--------: |:--------:| 
| N=M=10 si n=m=3          |  4         |     464.3    |     
| N=M=1000 si n=m=5        |  2         |     26912.9  |    
| N=M=1000 si n=m=5        |  4         |   27074.3    |    
| N=M=1000 si n=m=5        |  8         |    45677.8   |    
| N=M=1000 si n=m=5        |  16        |    53601.5   |  
| N=10 M=10000 si n=m=5    |  2         |      12324.5   |    
| N=10 M=10000 si n=m=5    |  4         |     16875.3    |   
| N=10 M=10000 si n=m=5    |  8         |      20425.2   |   
| N=10 M=10000 si n=m=5    |  16        |    20818.6     |    
| N=10000 M=10 si n=m=5    |  2         |   12152.7   |    
| N=10000 M=10 si n=m=5    |  4         |   14300.2   |  
| N=10000 M=10 si n=m=5    |  8         |   17657.1   |   
| N=10000 M=10 si n=m=5    |  16        |    18271.3  |    
| N=10000 M=10000 si n=m=5 |  2         |    2797943.7  |  
| N=10000 M=10000 si n=m=5 |  4         |   2216259.6    |  
| N=10000 M=10000 si n=m=5 |  8         |    1783520.1  |  
| N=10000 M=10000 si n=m=5 |  16        |  1884332.9    |  

- pe orizontala


| Input                    | Threaduri  | Static       | 
| :----------------------- | :--------: |:------------:| 
| N=M=10 si n=m=3          |  4         |     461.3        |    
| N=M=1000 si n=m=5        |  2         |    26755.9       |    
| N=M=1000 si n=m=5        |  4         |     25877.1      |   
| N=M=1000 si n=m=5        |  8         |    44227.5       |    
| N=M=1000 si n=m=5        |  16        |    54559.6       |    
| N=10 M=10000 si n=m=5    |  2         |   13604       |    
| N=10 M=10000 si n=m=5    |  4         |      14120     |   
| N=10 M=10000 si n=m=5    |  8         |   16137.2        |   
| N=10 M=10000 si n=m=5    |  16        |     18413.7     |
| N=10000 M=10 si n=m=5    |  2         |    13018.4     |   
| N=10000 M=10 si n=m=5    |  4         |   15095.4      |    
| N=10000 M=10 si n=m=5    |  8         |  18057.7       |    
| N=10000 M=10 si n=m=5    |  16        |     19141.1    |     
| N=10000 M=10000 si n=m=5 |  2         |    1648976.1      |  
| N=10000 M=10000 si n=m=5 |  4         |     965595.7      |  
| N=10000 M=10000 si n=m=5 |  8         |     739017.8    |  
| N=10000 M=10000 si n=m=5 |  16        |    694373.1      |  

- pe blocuri

| Input                    | Threaduri  | Static       |
| :----------------------- | :--------: |:------------:| 
| N=M=10 si n=m=3          |  4         |     438.1      |     
| N=M=1000 si n=m=5        |  2         |      39914.2     |  
| N=M=1000 si n=m=5        |  4         |     25255.6      |   
| N=M=1000 si n=m=5        |  8         |    29003.9       |  
| N=M=1000 si n=m=5        |  16        |    53649.4       |  
| N=10 M=10000 si n=m=5    |  2         |    7821.7      |  
| N=10 M=10000 si n=m=5    |  4         |     15372.8      |   
| N=10 M=10000 si n=m=5    |  8         |     14286.4      |    
| N=10 M=10000 si n=m=5    |  16        |    16594.7      |    
| N=10000 M=10 si n=m=5    |  2         |     8322    |  
| N=10000 M=10 si n=m=5    |  4         |    15493.5     |   
| N=10000 M=10 si n=m=5    |  8         |   15122.6      |    
| N=10000 M=10 si n=m=5    |  16        |    19727.8     |   
| N=10000 M=10000 si n=m=5 |  2         |     3660568.2     |  
| N=10000 M=10000 si n=m=5 |  4         |      1204680.7     |  
| N=10000 M=10000 si n=m=5 |  8         |   1027506.8      |  
| N=10000 M=10000 si n=m=5 |  16        |    828522.3      |  


### Analiza cod

#### Impartirea threadurilor pe linii / coloane
Daca avem N linii si p threaduri, fiecare thread va primii N / p linii in cazul in care N % p == 0, in cazul in care exista un rest, atunci primele rest threaduri vor primii N / p + 1 linii. Aceeasi impartire se considera si pentru coloane. 
