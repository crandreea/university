#include <iostream>
#include <fstream>
#include <vector>
#include <map>
#include <climits>
#include <algorithm>

using namespace std;

struct cf {
    int c;
    int f;
    int c_f;
    cf(int capacity, int flow, int cf_val) : c(capacity), f(flow), c_f(cf_val) {}
    cf() : c(0), f(0), c_f(0) {} // Constructor default pentru inițializare
};

struct nd {
    int h;
    int e;
    nd(int height, int excess) : h(height), e(excess) {}
    nd() : h(0), e(0) {} // Constructor default pentru inițializare
};

class Graph {
public:
    vector<nd> nodes;
    vector<map<int, cf> > lst;

    Graph(int n) {
        nodes.resize(n, nd(0, 0));
        lst.resize(n);
    }

    void add_edge(int u, int v, int c) {
        lst[u][v] = cf(c, 0, 1);
        lst[v][u] = cf(0, 0, 2);
    }

    int get_cf(int u, int v) {
        if (lst[u][v].c_f == 1) { // u,v in E
            return lst[u][v].c - lst[u][v].f;
        }
        if (lst[u][v].c_f == 2) { // v,u in E
            return lst[v][u].f;
        }
        return 0;
    }

    void init_preflux() {
        nodes[0].h = nodes.size();
        for (auto &ucf : lst[0]) {
            int u = ucf.first;
            ucf.second.f = ucf.second.c;
            nodes[u].e = ucf.second.c;
            nodes[0].e -= ucf.second.c;
        }
    }

    bool cond_pomp(int &u, int &v) {
        for (u = 1; u < nodes.size() - 1; u++) {
            if (nodes[u].e <= 0)
                continue;
            for (const auto &l : lst[u]) {
                v = l.first;
                if (get_cf(u, v) <= 0)
                    continue;
                if (nodes[u].h == nodes[v].h + 1) {
                    return true;
                }
            }
        }
        return false;
    }

    void pompare(int u, int v) {
        int df = min(nodes[u].e, get_cf(u, v));
        if (lst[u][v].c_f == 1)
            lst[u][v].f += df;
        else
            lst[v][u].f -= df;

        nodes[u].e -= df;
        nodes[v].e += df;
    }

    bool cond_inaltare(int &u) {
        int w = -1;
        for (u = 1; u < nodes.size() - 1; u++) {
            if (nodes[u].e <= 0)
                continue;

            bool ulowest = true;
            for (const auto &vcf : lst[u]) {
                int v = vcf.first;
                if (get_cf(u, v) > 0) {
                    if (nodes[u].h > nodes[v].h) {
                        ulowest = false;
                        break;
                    }
                }
            }
            if (!ulowest)
                continue;
            if (w == -1) {
                w = u;
            } else {
                if (nodes[u].h < nodes[w].h)
                    w = u;
            }
        }
        u = w;
        return u != -1;
    }

    void inaltare(int u) {
        int hmin = INT_MAX;
        for (const auto &vcf : lst[u]) {
            int v = vcf.first;
            if (get_cf(u, v) > 0)
                hmin = min(hmin, nodes[v].h);
        }
        nodes[u].h = hmin + 1;
    }

    void pomp_preflux() {
        int s = 0;
        int t = nodes.size() - 1;
        init_preflux();

        int u, v;
        while (true) {
            if (cond_pomp(u, v)) {
                pompare(u, v);
                continue;
            }
            if (cond_inaltare(u)) {
                inaltare(u);
                continue;
            }
            break;
        }

        cout << nodes.back().e << '\n';
    }
};

int main(int argc, char **argv) {
    if (argc < 3) {
        cerr << "Usage: " << argv[0] << " <input file> <output file>" << endl;
        return 1;
    }

    ifstream f(argv[1]);

    int n, m;
    f >> n >> m;

    Graph G(n);

    for (int u, v, c; f >> u >> v >> c;) {
        G.add_edge(u, v, c);
    }

    f.close();

    ofstream fout(argv[2]);
    streambuf *coutbuf = cout.rdbuf(); // Save old buffer
    cout.rdbuf(fout.rdbuf()); // Redirect cout to file

    G.pomp_preflux();

    cout.rdbuf(coutbuf); // Reset to standard output again

    return 0;
}
