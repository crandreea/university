#include "SortedList.h"
#include <fstream>
#include <climits>

SortedList::SortedList() {
    head = new Node(INT32_MIN, INT32_MAX);
    tail = new Node(INT32_MAX, INT32_MIN);
    head->next = tail;
}

SortedList::~SortedList() {
    Node* cur = head;
    while (cur) {
        Node* temp = cur;
        cur = cur->next;
        delete temp;
    }
}

void SortedList::insertSorted(int id, int nota) {
    head->node_mtx.lock();
    Node* pred = head;
    Node* cur = pred->next;
    cur->node_mtx.lock();
    
    while (cur != tail && 
           (cur->nota > nota || 
           (cur->nota == nota && cur->id < id))) {
        pred->node_mtx.unlock();
        pred = cur;
        cur = cur->next;
        cur->node_mtx.lock();
    }
    
    Node* newNode = new Node(id, nota);
    newNode->next = cur;
    pred->next = newNode;
    
    cur->node_mtx.unlock();
    pred->node_mtx.unlock();
}

void SortedList::writeToFile(const std::string& filename) {
    std::ofstream fout(filename);
    Node* cur = head->next;
    
    while (cur->id != INT32_MAX) {
        fout << cur->id << " " << cur->nota << "\n";
        cur = cur->next;
    }
    
    fout.close();
}
