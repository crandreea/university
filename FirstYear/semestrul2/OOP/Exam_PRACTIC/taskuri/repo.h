#pragma once
#include "domain.h"

class TaskRepo{
private:
    string filename;
    vector<Task> tasks;
    bool exist(Task& t){
        for(auto const& e : tasks){
            if(e.getId() == t.getId())
                return true;
        }
        return false;
    }
public:
    TaskRepo() = default;
    TaskRepo(string filename):filename{filename}{
        readFromFile();
    }

    void writeToFile();
    void readFromFile();

    void modify(int id, Task& t){
        tasks[id] = t;
    }
    void add(Task& t);
    vector<Task> getAll(){
        return tasks;
    }
    vector<Task> searchProgramator(string name);




};