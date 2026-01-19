#ifndef BOUNDEDQUEUE_H
#define BOUNDEDQUEUE_H

#include <queue>
#include <mutex>
#include <condition_variable>
#include <utility>

class BQueue {
private:
    std::queue<std::pair<int, int>> q;
    std::mutex mtx;
    std::condition_variable not_full; // pt prod
    std::condition_variable not_empty; // pt consum
    int capacity;
    bool done;
    
public:
    BQueue(int cap);
    void push(int id, int nota);
    bool pop(int& id, int& nota);
    void setDone();
};

#endif