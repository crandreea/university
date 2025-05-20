#include <iostream>
#include <string>
#include <algorithm>

using std::sort;
using std::string;
using std::vector;
using namespace std;

class Meniu{
private:
    float pret;
public:
    Meniu() = default;
    Meniu(float pret) : pret{pret}{}
    virtual string descriere() = 0;
    virtual float getPret() const{
        return pret;
    }
    virtual ~Meniu() = default;
};

class CuRacoritoare : public Meniu{
private:
    unique_ptr<Meniu> m;
public:
    CuRacoritoare( unique_ptr<Meniu> m) :  m{move(m)} {

    }

    float getPret() const override{
        return m->getPret() + 4;
    }

    string descriere() override{
        return m->descriere() + "cu racoritoare";
    }

};

class CuCafea : public Meniu{
private:
    unique_ptr<Meniu> m;
public:
    CuCafea(unique_ptr<Meniu> m) : m{move(m)} {

    }

    float getPret() const override{
        return m->getPret() + 5;
    }

    string descriere() override{
        return m->descriere() + "cu cafea";
    }

};

class MicDejun : public Meniu{
private:
    string denumire;
public:

    MicDejun(float pret, string denumire):Meniu{pret}, denumire{denumire}{
    };

    string descriere() override{
        return denumire;
    };

};

vector<unique_ptr<Meniu>> f(){
    vector<unique_ptr<Meniu>> rez;

    rez.push_back(make_unique<CuCafea>(make_unique<CuRacoritoare>(make_unique<MicDejun>(15, "Omleta"))));
    rez.push_back(make_unique<CuCafea>(make_unique<MicDejun>(10, "Ochiuri")));
    rez.push_back(make_unique<MicDejun>(15, "Omleta"));
    return rez;
}

int main() {
    vector<unique_ptr<Meniu>> order = f();
    sort(order.begin(), order.end(), [](unique_ptr<Meniu>& m1, unique_ptr<Meniu>& m2){
        return m1->getPret() < m2->getPret();
    });
    for(auto& elem : order){
        cout<<elem->descriere()<<endl;
    }
    return 0;
}
