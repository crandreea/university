#include <iostream>
using namespace std;
class Patricipant{
public:
    virtual void tipareste() = 0;
    virtual bool eVoluntar(){
        return true;
    }
    virtual ~Patricipant() = default;
};


class Personal : public Patricipant{
private:
    string nume;
public:
    Personal(string nume):nume{nume}{}

    void tipareste() override{
        cout<<nume<< " ";
    }
};

class Adminstrator : public Personal{
public:
    Adminstrator(string nume): Personal(nume){}

    void tipareste() override{
        Personal::tipareste();
        cout<<"Administrator"<<" ";
    }

};

class Director : public Personal{
public:
    Director(string nume): Personal(nume){}
    void tipareste() override{
        Personal::tipareste();
        cout<<"Director"<<" ";
    }
};

class Angajat : public Patricipant{
private:
    unique_ptr<Patricipant> P;
public:
    Angajat(unique_ptr<Patricipant> P):P{move(P)}{}
    void tipareste() override{
        P->tipareste();
        cout<<"Angajat"<<" ";
    }

    bool eVoluntar() override{
        return false;
    }
};


class ONG{
private:
    vector<unique_ptr<Patricipant>> participants;
public:
    ONG(vector<unique_ptr<Patricipant>> participants) : participants(move(participants)){}

    void add(unique_ptr<Patricipant> p){
        participants.push_back(std::move(p));
    }

    vector<unique_ptr<Patricipant>> getAll(bool voluntari) {
        vector<unique_ptr<Patricipant>> rez;
        for (auto& e : participants)
            if (e!= nullptr && e->eVoluntar() == voluntari)
                rez.push_back(std::move(e));

        return rez;
    }
};
ONG f(){
    auto a = make_unique<Adminstrator>("Dorel");
    auto d = make_unique<Director>("Elvis");

    auto a2 = make_unique<Angajat>(move(a)); // angajat administrator
    auto d2 = make_unique<Angajat>(move(d)); //angajat director

    ONG o({});
    o.add(move(a));
    o.add(move(a2));
    o.add(move(d));
    o.add(move(d2));

    return o;
}

int main() {
    ONG o = f();
    auto voluntari = o.getAll(true);
    for (auto& v: voluntari)
    {
        v->tipareste();
        cout<<endl;
    }
    auto angajati = o.getAll(false);
    for (auto& a: angajati)
    {
        a->tipareste();
        cout<<endl;
    }

    return 0;
}
