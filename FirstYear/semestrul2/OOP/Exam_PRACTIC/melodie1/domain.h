#pragma once
#include <iostream>
using namespace std;

class Melodie{
private:
    int id;
    string denumire;
    string artist;
    int rank;

public:
    Melodie() = default;
    Melodie(int id, string denumire, string artist, int rank):id{id}, denumire{denumire}, artist{artist}, rank{rank}{}

    int getId() const{
        return id;
    }

    string getDenumire() const{
        return denumire;
    }

    string getArtist() const{
        return artist;
    }

    int getRank() const{
        return rank;
    }

    void setRank(int rank_nou) {
        rank = rank_nou;
    }

    void setDenumire(string titlu_nou) {
        denumire = titlu_nou;
    }

};