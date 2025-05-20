#pragma once
#include "repo.h"
#include "observer.h"

class TaskService : public Observable{
private:
    TaskRepo& Repo;
public:
    TaskService(TaskRepo& Repo):Repo{Repo}{}

    vector<Task> getAll(){
        return sortByStare();
    }
    void modify(int id, string descriere, vector<string> prog, string new_stare ){
        Task t{id, descriere, prog, new_stare};
        Repo.modify(id, t);
        notify();
    }
    void add(int id, string& descriere, string& prog, string& stare){
        if(stare != "open" "" && stare!= "closed" && stare!= "inprogress")
            throw invalid_argument("Task invalid");
        if(descriere.empty())
            throw invalid_argument("Task invalid");

        vector<string> programatori;
        int ok;
        string p;
        for(auto &c : prog) {
            if(c == ' ')
                ok = 0;
            else
                p += c;
            if(ok == 0) {
                programatori.push_back(p);
                ok = 1;
                p = "";
            }
        }
        programatori.push_back(p);

        if(programatori.empty() || programatori.size()>4)
            throw invalid_argument("Task invalid");

        Task t{id, descriere, programatori, stare};
        Repo.add(t);
        notify();
    }

    vector<Task> filterByName(string& name){
        vector<Task> rez = Repo.searchProgramator(name);
        return rez;
    }

    vector<Task> filterByStare(string& stare){
        vector<Task> rez;
        vector<Task> tasks = Repo.getAll();
        for(auto const& el: tasks){
            if(el.getStare() == stare)
                rez.push_back(el);
        }
        return rez;
    }

    vector<Task> sortByStare(){
        vector<Task> tasks = Repo.getAll();
        sort(tasks.begin(), tasks.end(), [](Task& t1, Task& t2){return t1.getStare()<t2.getStare();});
        return tasks;
    }
};