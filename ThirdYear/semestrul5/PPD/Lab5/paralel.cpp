#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <thread>
#include <vector>
#include <mutex>
#include <semaphore>
#include <atomic>
#include "LinkedList.h"
#include "Queue.h"
#include "SortedList.h"
#include "ThreadPool.h"
#include <sqlite3.h>

using namespace std;

const int MAX_QUEUE_SIZE = 100;

LinkedList lista;
atomic<int> readers_done(0);
int p_r;

struct Student {
    int id;
    int nota;
    
    bool operator<(const Student& other) const {
        if (nota != other.nota)
            return nota > other.nota; 
        return id < other.id; 
    }
};

void reader(const vector<string> &files, BQueue& queue) {
    for (auto &filename : files) {
        ifstream fin(filename);
        if (!fin.is_open()) continue;

        string line;
        while (getline(fin, line)) {
            for (char &c : line)
                if (c=='(' || c==')' || c==',') c=' ';

            int id, nota;
            stringstream ss(line);
            ss >> id >> nota;

            queue.push(id, nota); // adauga perechile citite in coada
        }
        fin.close();
    }

    readers_done++;
}

void dbReader(const vector<string>& tableNames, BQueue& queue) {
    sqlite3* db;
    int rc = sqlite3_open("studenti.db", &db);
    
    if (rc != SQLITE_OK) {
        cerr << "Eroare la deschiderea bazei de date: " << sqlite3_errmsg(db) << endl;
        readers_done++;
        return;
    }
    
    for (const auto& tableName : tableNames) {
        string query = "SELECT id, nota FROM " + tableName;
        sqlite3_stmt* stmt;
        
        rc = sqlite3_prepare_v2(db, query.c_str(), -1, &stmt, nullptr);
        if (rc != SQLITE_OK) {
            cerr << "Eroare la query" << tableName << ": " 
                 << sqlite3_errmsg(db) << endl;
            continue;
        }
        
        while (sqlite3_step(stmt) == SQLITE_ROW) {
            int id = sqlite3_column_int(stmt, 0);
            int nota = sqlite3_column_int(stmt, 1);
            queue.push(id, nota);
        }
        
        sqlite3_finalize(stmt);
    }
    
    sqlite3_close(db);
    readers_done++;
}

void worker(BQueue& queue) {
    while (true) {
        int id, nota;
        if (!queue.pop(id, nota)) {
            break; // coada goala
        }
        
        lista.addOrUpdate(id, nota);
    }
}

void sortingWorker(Node* node, SortedList& sortedList) {
    if (node->id != INT32_MIN && node->id != INT32_MAX) {
        sortedList.insertSorted(node->id, node->nota);
    }
}

void sortSequentialFile() {
    ifstream fin("rezultateSecvential.txt");
    if (!fin.is_open()) {
        cerr << "Eroare: nu se poate deschide rezultateSecvential.txt\n";
        return;
    }
    
    vector<Student> studenti;
    int id, nota;
    while (fin >> id >> nota) {
        studenti.push_back({id, nota});
    }
    fin.close();
    
    sort(studenti.begin(), studenti.end());
    
    ofstream fout("rezultateSecvential.txt");
    for (auto& s : studenti) {
        fout << s.id << " " << s.nota << "\n";
    }
    fout.close();
}

bool compareFiles(const string& file1, const string& file2) {
    ifstream f1(file1);
    ifstream f2(file2);
    
    if (!f1.is_open() || !f2.is_open()) {
        cerr << "Eroare la deschiderea fisierelor pentru comparare!\n";
        return false;
    }
    
    int id1, nota1, id2, nota2;
    int lineNum = 0;
    bool identical = true;
    
    while (true) {
        bool has1 = static_cast<bool>(f1 >> id1 >> nota1);
        bool has2 = static_cast<bool>(f2 >> id2 >> nota2);
        lineNum++;
        
        if (!has1 && !has2) {
            break; // ambele fisiere s au terminat
        }
        
        if (!has1 || !has2) {
            cout << "DIFERENTA: Fisierele au numar diferit de linii!\n";
            identical = false;
            break;
        }
        
        if (id1 != id2 || nota1 != nota2) {
            cout << "DIFERENTA la linia " << lineNum << ":\n";
            cout << "  " << file1 << ": ID=" << id1 << " Nota=" << nota1 << "\n";
            cout << "  " << file2 << ": ID=" << id2 << " Nota=" << nota2 << "\n";
            identical = false;
        }
    }
    
    f1.close();
    f2.close();
    
    return identical;
}


int main(int argc, char** argv) {
    if (argc < 3) {
        cerr << "Invalid arguments\n";
        return 1;
    }
    
    int p = stoi(argv[1]);
    p_r = stoi(argv[2]);
    int p_w = p - p_r;
    
    vector<string> fisiere;
    vector<string> tabele;
    for (int i = 1; i <= 10; i++)
        //tabele.push_back("proiect"+ to_string(i));
        fisiere.push_back("proiect" + to_string(i) + ".txt");
    
    sortSequentialFile();

    auto start = chrono::high_resolution_clock::now();
    
    BQueue queue(MAX_QUEUE_SIZE);
    ThreadPool readerPool(p_r);
    
    readerPool.enqueue([fisiere, &queue](){reader(fisiere, queue);});
    //readerPool.enqueue([tabele, &queue](){dbReader(tabele, queue);});

    ThreadPool workerPool(p_w);
    for (int i = 0; i < p_w; i++) {
        workerPool.enqueue([&queue]() { worker(queue); });
    }
    
    readerPool.shutdown(); //astept s atermine toti readers
    
    queue.setDone(); // semnalez catre workeri ca readers au terminat
    workerPool.shutdown(); //astept sa termine workers
    
    SortedList sortedList;
    ThreadPool sortPool(p_w);
    
    //fiecare nod din lista => un task de sortare
    Node* cur = lista.getHead()->next;
    while (cur != lista.getTail()) {
        Node* node = cur;
        sortPool.enqueue([node, &sortedList]() { sortingWorker(node, sortedList); });
        cur = cur->next;
    }
    
    sortPool.shutdown(); //astept sa se temrina sortarea
    
    sortedList.writeToFile("rezultateParalel.txt");
    
    vector<int> cheaters = lista.getCheaters();
    if (!cheaters.empty()) {
        ofstream foutCheaters("studentiCopiat.txt");
        for (int id : cheaters) {
            foutCheaters << id << "\n";
        }
        foutCheaters.close();
    } else {
        cout << "\nNu au fost detectati studenti care au copiat\n";
    }

    auto stop = chrono::high_resolution_clock::now();
    auto duration = chrono::duration_cast<chrono::milliseconds>(stop - start);
    
    compareFiles("rezultateSecvential.txt", "rezultateParalel.txt");
    cout << "Timpul de executie: " << duration.count() << " ms\n";
    return 0;
}