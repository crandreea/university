#pragma once
#include "domain.h"
#include <fstream>
#include <sstream>

class RepoMelodie{
private:
    string filename;
    vector<Melodie> melodii;
    bool exist(Melodie &m){
        for(auto const&el: melodii)
            if(el.getId() == m.getId())
                return true;
        return false;
    }

public:
    RepoMelodie()= default;
    RepoMelodie(string filename):filename(filename){
        loadFromFile();
    }

    vector<Melodie> getAll(){
        return melodii;
    }

    void add(Melodie& m){
        if(exist(m))
            throw invalid_argument("");
        melodii.push_back(m);
        writeToFile();
    }


    void modifica(int id, std::string titlu, int rank) {
        for(auto &melodie : melodii) {
            if(melodie.getId() == id) {
                melodie.setDenumire(titlu);
                melodie.setRank(rank);
            }

            writeToFile();
        }
    }
    void sterge(Melodie m){
        melodii.erase(remove_if(melodii.begin(), melodii.end(), [&m](Melodie& oth){
            return (m.getId() ==oth.getId());
        }), melodii.end());
    }

    void loadFromFile(){
        ifstream fin(filename);
        if(!fin.is_open())
            throw invalid_argument("");

        string line;
        while(getline(fin, line)){
            int id, rank;
            string denumire, tip;
            string curent;
            stringstream linestream(line);
            int n = 0;
            while(getline(linestream, curent, ',')){
                if(n==0)
                    id = stoi(curent);
                if(n==1)
                    denumire = curent;
                if(n==2)
                    tip = curent;
                if(n==3)
                    rank = stoi(curent);

                n++;
            }
            Melodie m{id, denumire, tip, rank};
            add(m);
        }

        fin.close();
    }

    void writeToFile(){
        ofstream fout(filename);
        if(!fout.is_open())
            throw invalid_argument("");

        for(auto const& m :melodii)
            fout<<m.getId()<<","<<m.getDenumire()<<","<<m.getArtist()<<","<<m.getRank()<<endl;

        fout.close();
    }


};