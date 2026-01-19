#include <iostream>
#include <fstream>
#include <string>
#include <sstream>
using namespace std;

struct Node {
    int id;
    int nota;
    Node* next;
};

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

void calculateGrade(Node*& head, int id, int nota) {
    Node* node = findNode(head, id);
    if (node) {
        // daca exista adunam nota
        node->nota += nota;
    } else {
        // daca nu exista adaug nod nou
        addNode(head, id, nota);
    }
}

void readFile(Node*& head, const string& filename) {
    ifstream fin(filename);
    if (!fin.is_open()) {
        cerr << "Error opening file: " << filename << endl;
        return;
    }

    string line;
    while (getline(fin, line)) {
        int id, nota;

        for (char &c : line) {
            if (c == '(' || c == ')' || c == ',')
                c = ' ';
        }

        stringstream ss(line);
        ss >> id >> nota;

        calculateGrade(head, id, nota);
    }

    fin.close();
}

void writeResults(Node* head) {
    ofstream fout("rezultateSecvential.txt");
    Node* cur = head;

    while (cur != nullptr) {
        fout << cur->id << " " << cur->nota << "\n";
        cur = cur->next;
    }

    fout.close();
}

int main() {
    Node* lista = nullptr;

    auto start = chrono::high_resolution_clock::now();

    for (int i = 1; i <= 10; i++) {
        string filename = "proiect" + to_string(i) + ".txt";
        readFile(lista, filename);
    }

    writeResults(lista);

    auto stop = chrono::high_resolution_clock::now();
    auto duration = chrono::duration_cast<chrono::milliseconds>(stop - start);

    cout << "Timpul de executie: " << duration.count() << " ms\n";
    return 0;
}