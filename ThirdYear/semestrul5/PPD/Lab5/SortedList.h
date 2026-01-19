#ifndef SORTEDLIST_H
#define SORTEDLIST_H

#include "Node.h"
#include <string>

class SortedList {
private:
    Node* head;
    Node* tail;
    
public:
    SortedList();
    ~SortedList();
    void insertSorted(int id, int nota);
    void writeToFile(const std::string& filename);
};

#endif