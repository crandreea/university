#include "Queue.h"

BQueue::BQueue(int cap) : capacity(cap), done(false) {}

void BQueue::push(int id, int nota) {
    std::unique_lock<std::mutex> lock(mtx);
    // astept pana e spatiu sua done
    not_full.wait(lock, [this]() { 
        return q.size() < capacity; 
    });

    q.push({id, nota});
    not_empty.notify_one(); // notif un consum
}

bool BQueue::pop(int& id, int& nota) {
    std::unique_lock<std::mutex> lock(mtx);
    // astept date sua done
    not_empty.wait(lock, [this]() { 
        return !q.empty() || done; 
    });
    
    // e gata
    if (q.empty() && done) {
        return false;
    }
    
    auto p = q.front();
    q.pop();
    id = p.first;
    nota = p.second;
    
    not_full.notify_one(); // notif un produc
    return true;
}

void BQueue::setDone() {
    std::unique_lock<std::mutex> lock(mtx);
    done = true;
    not_empty.notify_all(); // trezeste toti cosumatorii
}