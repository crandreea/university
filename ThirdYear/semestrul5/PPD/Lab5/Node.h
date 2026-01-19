#ifndef NODE_H
#define NODE_H

#include <mutex>

struct Node {
    int id;
    int nota;
    Node* next;
    std::mutex node_mtx;
    
    Node(int i = -1, int n = 0) : id(i), nota(n), next(nullptr) {}
};

#endif