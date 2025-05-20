#pragma once
#include "repo.h"

class ServiceMelodii{
private:
    RepoMelodie& Repo;
public:
    ServiceMelodii(RepoMelodie& Repo):Repo{Repo}{}

    void modifica(int id, string titlu, int rank){
        Repo.modifica(id, titlu, rank);
    }

    void add(int id, string denumire, string tip, int rank){
        Melodie m{id, denumire, tip, rank};
        Repo.add(m);
    }

    void sterge(int id){
        string artist;
        for(auto& m : getAll()) {
            if(id == m.getId())
                artist = m.getArtist();
        }
        int i = 0;
        for(auto& m : getAll()) {
            if(artist == m.getArtist())
                i++;
        }

        Melodie m1;
        for(auto const& elem:getAll()){
            if(elem.getId() == id)
                m1 = elem;
        }

        if(i > 1)
            Repo.sterge(m1);
        else
            throw exception();
    }

    vector<Melodie> getAll(){
        return sortByRank();
    }

    vector<int> rankSort(){
        int rank = 0;
        vector<int> rez;
        for(int i = 0; i<10; i++) {
            int cnt = 0;
            for (auto const &m: getAll()) {
                if (m.getRank() == rank)
                    cnt++;
            }
            rez.push_back(cnt);
            rank++;
        }
        return rez;
    }

    int nrMelodiiRank(int rank){
        int cnt  = 0;
        for(auto const& m: Repo.getAll()){
            if(m.getRank() == rank)
                cnt++;
        }
        return cnt;
    }

    vector<Melodie> sortByRank(){
        vector<Melodie> melodii = Repo.getAll();
        sort(melodii.begin(), melodii.end(), [](Melodie& m1, Melodie& m2){
            return m1.getRank() > m2.getRank();
        });
        return melodii;
    }


};