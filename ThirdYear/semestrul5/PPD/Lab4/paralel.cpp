#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <thread>
#include <vector>
#include <mutex>
#include <semaphore>
#include <atomic>

using namespace std;

struct Node {
    int id;
    int nota;
    Node* next;
};
// lista rez 
Node* lista = nullptr;
mutex lista_mtx;   // mutex ca doar un thrd sa poata modif lista 

counting_semaphore<1000000> sem_items(0);

// coada
Node* q_head = nullptr;
Node* q_tail = nullptr;
mutex q_mtx;

int readers_done = 0;
int p_r;

Node* findNode(Node* head, int id) {
    Node* cur = head;
    while (cur != nullptr) {
        if (cur->id == id){
            return cur;
        } 
        cur = cur->next;
    }
    return nullptr;
}

void addNode(Node*& head, int id, int nota) {
    Node* newNode = new Node;
    newNode->id = id;
    newNode->nota = nota;
    newNode->next = head;
    head = newNode;
}

void calculateGrade(int id, int nota) {
    lock_guard<mutex> lock(lista_mtx);

    Node* node = findNode(lista, id);
    if (node) {
        // daca exista adunam nota
        node->nota += nota;
    } else {
        // daca nu exista adaug nod nou
        addNode(lista, id, nota);
    }
}

void addToQueue(int id, int nota) {
    auto* node = new Node{id, nota, nullptr};

    lock_guard<mutex> lock(q_mtx);
    if (!q_tail) {
        q_head = q_tail = node;
    } else {
        q_tail->next = node;
        q_tail = node;
    }

    sem_items.release(); // +1 ca am add un elem
}


bool deleteFromQueue(int &id, int &nota) {
    sem_items.acquire(); // -1 daca contorul e > 0, daca nu asteptam 

    lock_guard<mutex> lock(q_mtx);
    if (!q_head) return false;

    Node* node = q_head;
    q_head = q_head->next;
    if (!q_head) q_tail = nullptr;

    id = node->id;
    nota = node->nota;
    delete node;
    return true;
}

void reader(const vector<string> &files) {
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

            addToQueue(id, nota); // adauga perechile citite in coada
        }
        fin.close();
    }

    readers_done++;
}

void worker() {
    while (true) {
        int id, nota;
        deleteFromQueue(id, nota);

        if (id == -1) 
            break;

        if (readers_done == p_r) {
            lock_guard<mutex> lock(q_mtx);
            if (!q_head) // adica coada e goala
                break;
        }

        calculateGrade(id, nota);
    }
}

void writeResults(Node* head) {
    ofstream fout("rezultateParalel.txt");
    Node* cur = head;

    while (cur != nullptr) {
        fout << cur->id << " " << cur->nota << "\n";
        cur = cur->next;
    }

    fout.close();
}

int main(int argc, char** argv){
    if (argc < 3) {
        cerr << "Invalid arguments";
        return 1;
    }

    int p = stoi(argv[1]);;
    p_r = stoi(argv[2]);;

    int p_w = p - p_r;

    vector<string> fisiere;
    for (int i = 1; i <= 10; i++)
        fisiere.push_back("proiect" + to_string(i) + ".txt");

    vector<vector<string>> assign(p_r); //fisierele din care cit un reader
    // le impart una una 
    for (int i = 0; i < 10; i++)
        assign[i % p_r].push_back(fisiere[i]);

    auto start = chrono::high_resolution_clock::now();

    vector<thread> readers;
    vector<thread> workers;

    for (int i = 0; i < p_r; i++)
        readers.emplace_back(reader, assign[i]);

    for (int i = 0; i < p_w; i++)
        workers.emplace_back(worker);

    for (auto &t : readers) t.join();

    // aici au terminat toti readerii
    for (int i = 0; i < p_w; i++) {
        addToQueue(-1, 0);
    }

    for (auto &t : workers) t.join();

    writeResults(lista);

    auto stop = chrono::high_resolution_clock::now();
    auto duration = chrono::duration_cast<chrono::milliseconds>(stop - start);

    cout << "Timpul de executie: " << duration.count() << " ms\n";
    return 0;
}