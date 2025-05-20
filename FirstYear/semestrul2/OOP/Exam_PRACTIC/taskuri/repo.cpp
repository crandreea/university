#include "repo.h"
#include <fstream>
#include <exception>
#include <sstream>

void TaskRepo::add(Task& t) {
    if(exist(t))
        throw invalid_argument("Task invalid");
    tasks.push_back(t);
    writeToFile();
}

vector<Task> TaskRepo::searchProgramator(std::string name) {
    vector<Task> rez;
    for(auto const& e: tasks)
    {
        vector<string>programatori = e.getNames();
        for(auto const& n : programatori)
            if(n == name)
                rez.push_back(e);
    }

    return rez;
}

void TaskRepo::readFromFile() {
    ifstream fin(filename);
    string line;
    while(getline(fin, line)) {
        int id;
        string descriere, stare;
        vector<string> programatori;
        string curent;
        stringstream linestream(line);
        int nr = 0;
        while(getline(linestream, curent, ',')) {
            if(nr == 0) {
                id = stoi(curent);
            }
            if(nr == 1) {
                descriere = curent;
            }
            if(nr == 2) {
                stringstream linestream2(curent);
                string programator;
                while(getline(linestream2, programator, ' ')) {
                    programatori.push_back(programator);
                }
            }
            if(nr == 3) {
                stare = curent;
            }
            nr++;
        }
        Task task(id, descriere, programatori, stare);
        add(task);
    }
    fin.close();
}

void TaskRepo::writeToFile() {
    ofstream fout(filename);
    for(auto &el : tasks) {
        string programatori;
        for(auto &p : el.getNames()) {
            programatori += p;
            programatori += " ";
        }
        fout<<el.getId()<<","<<el.getDescriere()<<","<<programatori<<","<<el.getStare()<<endl;
    }
    fout.close();
}
