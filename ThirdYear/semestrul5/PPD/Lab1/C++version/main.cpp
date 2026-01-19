#include <iostream>
#include <fstream>
#include <thread>
using namespace std;

#define N_max 10000
#define M_max 10000
#define K_max 6

// N - nr linii F
// M - nr coloane F
// n = m - nr linii si coloane C
// p - nr de threaduri
int N, M, n, m, p;

// F - matricea input
// C - matricea de convolutie
// V - matrice rezultata 
//int F[N_max][M_max], C[K_max][K_max], V[N_max][M_max];

int** F;
int** C;
int** V;

void eliberare_matrici() {
    for (int i = 0; i < N; ++i){
        delete[] F[i];
        delete[] V[i];
    } 

    for (int i = 0; i < n; ++i) delete[] C[i];

    delete[] F;
    delete[] V;
    delete[] C;
}

void citire_matrici(string file_path){
    ifstream fin(file_path);

    fin>>N>>M;

    F = new int*[N];
    V = new int*[N];

    for (int i = 0; i < N; ++i){
        F[i] = new int[M];
        V[i] = new int[M];
    }

    for (int i = 0; i < N; ++i) {
        for (int j = 0; j < M; ++j) {
            fin >> F[i][j];
        }
    }

    fin>>n>>m;

    C = new int*[n];

    for (int i = 0; i < n; ++i)
        C[i] = new int[m];

    for (int i = 0; i < n; ++i) {
        for (int j = 0; j < m; ++j) {
            fin >> C[i][j];
        }
    }

    fin.close();
}

void afisare(string file_path){
    ofstream fout(file_path);

    for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++) {
            fout << V[i][j] << " ";
        }
        fout << endl;
    }

    fout.close();
}

void convolutie_linii_coloane(int start, int end, bool horizontal) {
    int offset = n / 2;
    if (horizontal) { 
        for (int i = max(start, offset); i < min(end, N - offset); i++) {
            for (int j = offset; j < M - offset; j++) {
                int s = 0;
                for (int u = 0; u < n; u++)
                    for (int v = 0; v < m; v++)
                        s += F[i - offset + u][j - offset + v] * C[u][v];
                V[i][j] = s;
            }
        }
    } else { 
        for (int j = max(start, offset); j < min(end, M - offset); j++) {
            for (int i = offset; i < N - offset; i++) {
                int s = 0;
                for (int u = 0; u < n; u++)
                    for (int v = 0; v < m; v++)
                        s += F[i - offset + u][j - offset + v] * C[u][v];
                V[i][j] = s;
            }
        }
    }
}

void convolutie_secventiala(){
    int offset = n / 2;
    for (int i = offset; i < N - offset; i++) {
        for (int j = offset; j < M - offset; j++) {
            int s = 0;
            for (int u = 0; u < n; u++)
                for (int v = 0; v < m; v++)
                    s += F[i - offset + u][j - offset + v] * C[u][v];
            V[i][j] = s;
        }
    }
}

void convolutie_paralela_linii_sau_coloane(bool horizontal){
    vector<thread> threads(p);
    int start = 0, end = 0, rest, baza;
    
    if(horizontal == true){
        baza = N / p;
        rest = N % p;
    }
    else{
        baza = M / p;
        rest = M % p;
    }

    auto t_start = std::chrono::high_resolution_clock::now();

    for(int i = 0; i < p; i ++){
        end = start + baza + (rest > 0 ? 1 : 0);

        if (rest > 0)
        {
            rest--;
        }

        threads[i] = thread(convolutie_linii_coloane, start, end, horizontal);
        start = end;
    }

    for (int i = 0; i < p; i++)
    {
        threads[i].join();
    }

    auto t_end = std::chrono::high_resolution_clock::now();
    double elapsed_time_ms = std::chrono::duration<double, std::milli>(t_end-t_start).count();

    std::cout<<(int)elapsed_time_ms * 100;
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
    citire_matrici("date2.txt");
    auto t_start = std::chrono::high_resolution_clock::now();

    convolutie_secventiala();
    
    auto t_end = std::chrono::high_resolution_clock::now();
    double elapsed_time_ms = std::chrono::duration<double, std::milli>(t_end-t_start).count();
    std::cout<<"Timp program secvential: "<< elapsed_time_ms;

    //eliberare_matrici();
    afisare("output.txt");
}

void main_paralel(int nrThrd){
    p = nrThrd;
    citire_matrici("date1.txt");
    convolutie_paralela_linii_sau_coloane(true);
    afisare("output_paralel.txt");
    verif_corectitudine("output.txt", "output_paralel.txt");
}

int main(int argc, char* argv[]){
    int threads = std::stoi(argv[1]);
    //main_secvential();
    //main_paralel(threads);
    eliberare_matrici();
    return 0;
}