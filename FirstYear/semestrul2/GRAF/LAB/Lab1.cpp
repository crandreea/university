#include <iostream>
#include <fstream>
#include <vector>
#include <queue>
using namespace std;


void citire(const char* fisier, vector< vector<int> > &graf)
{
    ifstream fin(fisier);
    int noduri, x, y;

    fin >> noduri;

    graf.resize(noduri, vector<int>(noduri, 0));

    while (fin >> x >> y)
    {
        graf[x][y] = graf[y][x] = 1;
    }

    fin.close();
}

//Lista de adiacenta = pt fiecare vf, lista cu vecinii sai
//Matricea de adiacenta = 1 daca nodul i are muchie spre alt nod j
//Matricea de incidenta - randuri noduri, coloane muchii

void afis_matrice_a(const vector<vector<int> >& graf)
{
    cout << "Matricea de adiacenta:" << endl;
    for (auto& linie : graf)
    {
        for (int element : linie)
        {
            cout << element << " ";
        }
        cout << endl;
    }
}


void afis_lista_a(const vector<vector<int> > &graf)
{
    cout << "Lista de adiacenta:" << endl;
    for (int i = 0; i < graf.size(); ++i)
    {
        cout << i << ": ";
        for (int j = 0; j < graf[i].size(); ++j)
        {
            if (graf[i][j] == 1)
            {
                cout << j << " ";
            }
        }
        cout << endl;
    }
}

vector<vector<int> > matricea_a_lista_a(const vector<vector<int> > graf)
{
    vector<vector<int> > lista_a(graf.size(), vector<int>());
    for (int i = 0; i < graf.size(); ++i)
    {
        for (int j = 0; j < graf[i].size(); ++j)
        {
            if (graf[i][j] == 1)
            {
                lista_a[i].push_back(j); //pune la capatul listei
            }
        }
    }
    return lista_a;
}

vector<vector<int> > matrice_a_matrice_i(const vector<vector<int> >& graf)
{
    vector<vector<int> > matrice_i(graf.size(), vector<int>(graf.size(), 0));

    for (int i = 0; i < graf.size(); ++i)
    {

        int indice_muchie = 1;
        for (int j = i + 1; j < graf[i].size(); ++j)
        {
            if (graf[i][j] == 1)
            {
                matrice_i[i][indice_muchie] = 1;
                matrice_i[j][indice_muchie] = 1;
                ++indice_muchie;
            }
        }
    }
    return matrice_i;
}


void citire_matrice_adiacenta(const char* fisier, vector<vector<int> >& graf) {
    ifstream fin(fisier);
    int noduri;
    fin >> noduri;

    graf.resize(noduri, vector<int>(noduri, 0));

    for (int i = 0; i < noduri; ++i) {
        for (int j = 0; j < noduri; ++j) {
            fin >> graf[i][j];
        }
    }

    fin.close();
}


vector<int> noduri_izolate(const vector<vector<int> >& graf) {
    vector<int> izolate;
    for (int i = 0; i < graf.size(); ++i) {
        bool izolat = true;
        for (int j = 0; j < graf[i].size(); ++j) {
            if (graf[i][j] == 1) {
                izolat = false;
                break;
            }
        }
        if (izolat) {
            izolate.push_back(i);
        }
    }
    return izolate;
}

//regulat = fiecare nod are același numar de vecini
bool este_regular(const vector<vector<int> >& graf) {
    int grad = 0;
    for (int i = 0; i < graf.size(); ++i) {
        int sumaGrad = 0;
        for (int j = 0; j < graf[i].size(); ++j) {
            sumaGrad += graf[i][j];
        }
        if (i == 0) {
            grad = sumaGrad;
        } else if (grad != sumaGrad) {
            return false;
        }
    }
    return true;
}


vector<vector<int> > matrice_distante(const vector<vector<int> >& graf) {
    int n = graf.size();
    vector<vector<int> > distante(n, vector<int>(n, n + 1));


    for (int i = 0; i < n; ++i) {
        for (int j = 0; j < n; ++j) {
            if (graf[i][j] == 1) {
                distante[i][j] = 1;
            }
        }
    }

    for (int k = 0; k < n; ++k) {
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (distante[i][k] + distante[k][j] < distante[i][j]) {
                    distante[i][j] = distante[i][k] + distante[k][j];
                }
            }
        }
    }

    return distante;
}

//daca intre oricare doua noduri exista cel putin un lanț
bool este_conex(const vector<vector<int> >& graf) {
    int n = graf.size();
    vector<bool> vizitat(n, false);
    queue<int> coada;

    //porneste de la un nod arbitrar
    coada.push(0);
    vizitat[0] = true;

    //parcurge graful in latime
    while (!coada.empty()) {
        int nodCurent = coada.front();
        coada.pop();
        for (int i = 0; i < n; ++i) {
            if (graf[nodCurent][i] == 1 && !vizitat[i]) {
                coada.push(i);
                vizitat[i] = true;
            }
        }
    }

    //verifica daca toate nodurile au fost vizitate
    for (bool v : vizitat) {
        if (!v) {
            return false;
        }
    }
    return true;
}

void ex2() {
    const char* numeFisier = "graf1.txt";
    vector<vector<int> > graf;

    citire_matrice_adiacenta(numeFisier, graf);


    vector<int> izolate = noduri_izolate(graf);
    cout << "Nodurile izolate: ";
    if (izolate.empty()) {
        cout << "Nu exista." << endl;
    } else {
        for (int nod : izolate) {
            cout << nod << " ";
        }
        cout << endl;
    }


    if (este_regular(graf)) {
        cout << "Graful este regular." << endl;
    } else {
        cout << "Graful nu este regular." << endl;
    }


    vector<vector<int> > distante = matrice_distante(graf);
    cout << "Matricea distantelor:" << endl;
    for (const auto& linie : distante) {
        for (int distanta : linie) {
            if (distanta == graf.size() + 1) {
                cout << "- ";
            } else {
                cout << distanta << " ";
            }
        }
        cout << endl;
    }

    // Determinarea daca graful este conex
    if (este_conex(graf)) {
        cout << "Graful este conex." << endl;
    } else {
        cout << "Graful nu este conex." << endl;
    }


}

void ex1()
{
    const char* numeFisier = "graf.txt";

    vector<vector<int> > graf;
    citire(numeFisier, graf);

    afis_matrice_a(graf); //afiseaza matricea de adiacenta


    vector<vector<int> > lista_a = matricea_a_lista_a(graf);
    afis_lista_a(lista_a);//afiseaza lista de adiacenta

    vector<vector<int> > matrice_i = matrice_a_matrice_i(graf);
    cout << "Matricea de incidenta:" << endl;
    for (auto& linie : matrice_i)
    {
        for (int element : linie)
        {
            cout << element << " ";
        }
        cout << endl;
    }
}
int main()
{
    //ex1();
    ex2();
    return 0;
}



/**
 * BELLMAN_FORD
 *
 * - minimum cost from source to all nodes
 * - weight[node] <> negative / positive
 */

// Imports and configuration
#include <iostream>
#include <vector>

/**
 * Perform Bellman Ford's algorithm for minimum cost on weighted graph.
 *
 * @param graph(vector<vector<pair<int, int>>>) - weighted graph
 * @param source(int) - source node from where to start algorithm
 *
 * @return vector<int> - list of minimum distances to every node from source
 */
std::vector<int> BELLMAN_FORD(std::vector<std::vector<std::pair<int, int>>> &graph, int source) {
    int nodes = (int)graph.size();

    // Initialize distances from source to all nodes
    std::vector<int> dists(nodes, INT_MAX);
    dists[source] = 0;

    // Relax edges repeatedly
    for (int idx = 0; idx < nodes; idx++) {
        for (auto& [node, dist]: graph[idx]) {
            if (dists[idx] != INT_MAX && dists[idx] + dist < dists[node]) {
                dists[node] = dists[idx] + dist;
            }
        }
    }

    // Check for negative cycles
    for (int idx = 0; idx < nodes; idx++) {
        for (auto& [node, dist]: graph[idx]) {
            if (dists[idx] != INT_MAX && dists[idx] + dist < dists[node]) {
                return {};
            }
        }
    }

    return dists;
}