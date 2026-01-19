#include "LinkedList.h"
#include <mutex>
#include <vector>

using namespace std;

LinkedList::LinkedList() {
    head = new Node(INT32_MIN, 0);
    tail = new Node(INT32_MAX, 0);
    head->next = tail;
}

LinkedList::~LinkedList() {
    Node* cur = head;
    while (cur) {
        Node* temp = cur;
        cur = cur->next;
        delete temp;
    }
}

void LinkedList::addOrUpdate(int id, int nota) {
    if (nota == -1) {
        addCheater(id);
        removeStudent(id);
        return;
    }

    head->node_mtx.lock(); //blochez head
    Node* pred = head;
    Node* cur = pred->next;
    cur->node_mtx.lock(); //blochez urmatorul
    
    while (cur->id < id) {
        pred->node_mtx.unlock(); //eliberez pred
        pred = cur;
        cur = cur->next;
        cur->node_mtx.lock(); //blochez urm
    }
    
    if (cur->id == id) {
        cur->nota += nota; //update
    } else {
        Node* newNode = new Node(id, nota);
        newNode->next = cur;
        pred->next = newNode; //inserare
    }

    cur->node_mtx.unlock();
    pred->node_mtx.unlock();
}

void LinkedList::removeStudent(int id) {
    head->node_mtx.lock();
    Node* pred = head;
    Node* cur = pred->next;
    cur->node_mtx.lock();
    
    while (cur->id < id) {
        pred->node_mtx.unlock();
        pred = cur;
        cur = cur->next;
        cur->node_mtx.lock();
    }
    
    if (cur->id == id && cur != tail) {
        // gasit nodul de stres
        pred->next = cur->next;
        delete cur;
    } 

    cur->node_mtx.unlock();
    pred->node_mtx.unlock();
}

void LinkedList::addCheater(int id) {
    lock_guard<mutex> lock(cheatersMutex);
    // verif daca id ul nu este deja in lista 
    if (find(cheaters.begin(), cheaters.end(), id) == cheaters.end()) {
        cheaters.push_back(id);
    }
}

vector<int> LinkedList::getCheaters() {
    lock_guard<mutex> lock(cheatersMutex);
    vector<int> result = cheaters;
    sort(result.begin(), result.end());
    return result;
}

Node* LinkedList::getHead() {
    return head;
}

Node* LinkedList::getTail() {
    return tail;
}