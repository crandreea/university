#include "repo.h"
#include <fstream>
#include <sstream>
vector<Rochie> RepoRochie::getAll() {
    return rochii;
}

void RepoRochie::add(Rochie &r) {
    //if(!exist(r))
        rochii.push_back(r);
}


void RepoFile::read() {
    std::ifstream in(fileName);
    if(!in.is_open())
        throw Exception("Eroare deschidere");
    std::string line;
    while (std::getline(in, line)) {
        std::istringstream lineStream(line);
        std::string cell;

        int id;
        std::string name;
        std::string marime;
        double pret;
        bool disp;

        // Citește ID-ul
        std::getline(lineStream, cell, ',');
        id = std::stoi(cell);

        // Citește name
        std::getline(lineStream, cell, ',');
        name = cell;

        // Citește marime
        std::getline(lineStream, cell, ',');
        marime = cell;

        // Citește pret
        std::getline(lineStream, cell, ',');
        pret = std::stod(cell);

        // Citește disp
        std::getline(lineStream, cell, ',');
        disp = (cell == "1");

        // Adaugă rochia în repo
        Rochie r{id, name, marime, pret, disp};
        RepoRochie::add(r);
    }
    in.close();
}

void RepoFile::write() {
    std::ofstream out(fileName);
    if(!out.is_open())
        throw Exception("Eroare deschidere");
    for(auto const &rochie: getAll()){
        out<<rochie.get_id()<<","<<rochie.get_name()<<","<<rochie.get_marime()<<","<<rochie.get_pret()<<","<<rochie.get_disponibil();
        out<<endl;
    }
    out.close();
}