#include <iostream>
#include <fstream>
#include <random>
#include <ctime>
#include <string>
using namespace std;

int main() {
    const int nrStudenti = 500;
    const int nrProiecte = 10;

    mt19937 gen(time(nullptr));
    uniform_int_distribution<> distID(1, nrStudenti);
    uniform_int_distribution<> distNote(0, 10);
    uniform_int_distribution<> distNrNote(80, 200); 

    for (int p = 1; p <= nrProiecte; p++) {
        string filename = "proiect" + to_string(p) + ".txt";
        ofstream fout(filename);

        int nrLinii = distNrNote(gen);

        for (int i = 0; i < nrLinii; i++) {
            int id = distID(gen);
            int nota = distNote(gen);

            fout << "(" << id << ", " << nota << ")\n";
        }

        fout.close();
    }

    return 0;
}
