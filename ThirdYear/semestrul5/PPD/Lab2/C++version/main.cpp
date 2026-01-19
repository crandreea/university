#include <iostream>
#include <fstream>
#include <thread>
#include "barrier.cpp"
using namespace std;

#define N_max 10001
#define M_max 10001
#define K_max 4

// N - nr linii F
// M - nr coloane F
// n = m - nr linii si coloane C
// p - nr de threaduri
int N, M, n, m, p;

// F - matricea input
// C - matricea de convolutie
int** F;
int** C;

void eliberare_matrici() {
    for (int i = 0; i < N; ++i){
        delete[] F[i];
    } 

    for (int i = 0; i < n; ++i){
        delete[] C[i];
    } 

    delete[] F;
    delete[] C;
}

void citire_matrici(string file_path){
    ifstream fin(file_path);

    fin>>N>>M;
    F = new int*[N];

    for (int i = 0; i < N; ++i){
        F[i] = new int[M];
    }

    for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++) {
            fin >> F[i][j];
        }
    }

    fin>>n>>m;
    C = new int*[n];

    for (int i = 0; i < n; ++i)
        C[i] = new int[m];

    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            fin >> C[i][j];
        }
    }

    fin.close();
}

void afisare(string file_path){
    ofstream fout(file_path);

    for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++) {
            fout << F[i][j] << " ";
        }
        fout << endl;
    }

    fout.close();
}

int convolutie_pe_linie(int *valori_linie, int coloana, int linie_kernel){
    return valori_linie[max(coloana-1, 0)] * C[linie_kernel][0] + valori_linie[coloana] * C[linie_kernel][1] + valori_linie[min(M-1, coloana+1)] * C[linie_kernel][2];
}

void convolutie_secventiala(){
    int* prevLinie = new int[M];
    int* currLinie = new int[M];
    int* auxLinie = new int[M];

    for (int j = 0; j < M; ++j) {
        prevLinie[j] = F[0][j];
        currLinie[j] = F[0][j];
    }

    auto t_start = std::chrono::high_resolution_clock::now();
    for(int i = 0; i < N; i++){
        int* nextRow = F[min(N - 1, i + 1)];
        for(int j = 0; j < M; j++){
            int output = 0;
            output = convolutie_pe_linie(prevLinie, j, 0) + convolutie_pe_linie(currLinie, j, 1) + convolutie_pe_linie(nextRow, j, 2);

            auxLinie[j] = output;
        }
        
        for (int j = 0; j < M; ++j) {
            F[i][j] = auxLinie[j];
        }

        for (int j = 0; j < M; ++j) {
            prevLinie[j] = currLinie[j];
        }

        for (int j = 0; j < M; ++j) {
            currLinie[j] = nextRow[j];
        }
    }

    auto t_end = std::chrono::high_resolution_clock::now();
    double elapsed_time_ms = std::chrono::duration<double, std::milli>(t_end-t_start).count();

    std::cout<<elapsed_time_ms;

    delete[] prevLinie;
    delete[] currLinie;
    delete[] auxLinie;
}

void convolutie_linii(my_barrier& barrier, int start, int end){
    int* frontieraUp = new int[M];
    int* frontieraDown = new int[M];

    if (start > 0) {
        for (int j = 0; j < M; ++j)
            frontieraUp[j] = F[start - 1][j];
    } else {
        for (int j = 0; j < M; ++j)
            frontieraUp[j] = F[start][j]; 
    }

    if (end < N) {
        for (int j = 0; j < M; ++j)
            frontieraDown[j] = F[end][j];
    } else {
        for (int j = 0; j < M; ++j)
            frontieraDown[j] = F[N - 1][j]; 
    }

    barrier.wait();

    int* prevLinie = new int[M];
    int* currLinie = new int[M];
    int* auxLinie  = new int[M];

    for (int j = 0; j < M; ++j) {
        prevLinie[j] = frontieraUp[j];
        currLinie[j] = F[start][j];
    }

    for(int i = start; i < end; i++){
        int* nextRow;
        if (i+1 < end){
            nextRow = F[i + 1];
        }
        else{
            nextRow = frontieraDown;
        }
        
        for(int j = 0; j < M; j++){
            int output = 0;
            output = convolutie_pe_linie(prevLinie, j, 0) + convolutie_pe_linie(currLinie, j, 1) + convolutie_pe_linie(nextRow, j, 2);

            auxLinie[j] = output;
        }
        
        for (int j = 0; j < M; ++j) {
            F[i][j] = auxLinie[j];
        }

        for (int j = 0; j < M; ++j) {
            prevLinie[j] = currLinie[j];
        }

        for (int j = 0; j < M; ++j) {
            currLinie[j] = nextRow[j];
        }
    }
    
    delete[] frontieraUp;
    delete[] frontieraDown;
    delete[] prevLinie;
    delete[] currLinie;
    delete[] auxLinie;
}

void convolutie_paralela_linii(){
    vector<thread> threads(p);
    my_barrier barrier(p);

    int start = 0, end = 0;
    int baza = N / p;
    int rest = N % p;

    auto t_start = std::chrono::high_resolution_clock::now();

    for(int i = 0; i < p; i ++){
        end = start + baza + (rest > 0 ? 1 : 0);

        if (rest > 0)
        {
            rest--;
        }

        threads.emplace_back(convolutie_linii, ref(barrier), start, end);
        start = end;
    }

    for (auto &th : threads)
        if (th.joinable())
            th.join();
        

    auto t_end = std::chrono::high_resolution_clock::now();
    double elapsed_time_ms = std::chrono::duration<double, std::milli>(t_end-t_start).count();

    std::cout<<elapsed_time_ms;
}

void verif_corectitudine(string file1, string file2){
    ifstream fin_1(file1);
    ifstream fin_2(file2);

    int x, y;
    while (fin_1 >> x && fin_2 >> y) {
        if (x != y) {
            throw exception();
        }
    }
}

void main_secvential(){
    citire_matrici("date1.txt");

    convolutie_secventiala();

    //eliberare_matrici();
    afisare("output.txt");
}

void main_paralel(int nrThrd){
    p = nrThrd;
    citire_matrici("date1.txt");
    convolutie_paralela_linii();
    afisare("output_paralel.txt");
    //verif_corectitudine("output.txt", "output_paralel.txt");
}

int main(int argc, char* argv[]){
    //main_secvential();
    int threads = std::stoi(argv[1]);
    main_paralel(threads);
    eliberare_matrici();
    return 0;
}