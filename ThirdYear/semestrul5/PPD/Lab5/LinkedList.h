#ifndef LINKEDLIST_H
#define LINKEDLIST_H

#include "Node.h"
#include <climits>
#include <vector>
#include <mutex>

using namespace std;

class LinkedList {
private:
    Node* head;
    Node* tail;
    vector<int> cheaters;  // list cu stud care au copiat
    mutex cheatersMutex;   
    
    void removeStudent(int id);  // sterge un stud din lista
    void addCheater(int id);     // add un studnet in lista
    
public:
    LinkedList();
    ~LinkedList();
    void addOrUpdate(int id, int nota);
    vector<int> getCheaters();  // return lista cu stud care au copiat
    Node* getHead();
    Node* getTail();
};

#endif