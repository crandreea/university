#pragma once
#include "domain.h"

class RepoRochie{
private:
    vector<Rochie> rochii;
    bool exist(Rochie& r){
        for(auto const& rochie :rochii)
            if(rochie.get_id() == r.get_id())
                return true;
        return false;
    }

public:
    RepoRochie() = default;

    virtual void add(Rochie& r) = 0;
    vector<Rochie> getAll();
};

class RepoFile : public RepoRochie{
private:
    string fileName;
    void write();
    void read();
public:
    RepoFile(string filename):RepoRochie(), fileName(filename){
        read();
    }
    void add(Rochie& r) override{
        RepoRochie::add(r);
        write();
    }

};