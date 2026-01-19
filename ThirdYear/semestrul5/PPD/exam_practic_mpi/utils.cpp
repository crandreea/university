#include "utils.h"
#include <fstream>
#include <iostream>

using namespace std;

void read_numbers(const string& filename, int& N, vector<string>& numbers, int &n) {
    ifstream fin(filename);
    if (!fin) {
        cerr << "Eroare la deschiderea fisierului " << filename << "\n";
        exit(1);
    }

    fin >> N;
    numbers.resize(N);
    for (int i = 0; i < N; i++)
        fin >> numbers[i];

    fin >> n;
    fin.close();
}

void write_numbers(const string& filename, const vector<string>& numbers) {
    ofstream fout(filename);
    if (!fout) {
        cerr << "Eroare la scrierea fisierului " << filename << "\n";
        exit(1);
    }

    for (string x : numbers)
        fout << x << "\n";

    fout.close();
}
