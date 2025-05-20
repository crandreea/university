#include <iostream>
using namespace std;
using std::string;
using std::move;
class Transformer{
private:
public:
    virtual void transform(vector<int>& nrs) = 0;
};

class Adder : public Transformer{
private:
    int cat;
public:
    Adder() = default;
    Adder(int cat1):cat{cat1}{}

    void transform(vector<int>& nrs) override{
        for(int & nr : nrs)
            nr += cat;
    }
};

class Nuller : public Adder{
public:
    Nuller(int cat1):Adder{cat1}{}

    void transform(vector<int>& nrs) override{
        Adder::transform(nrs);
        for(int & nr : nrs)
            if(nr > 10)
                nr = 0;
    }
};

class Swapper : public Transformer{
public:
    void transform(vector<int>& nrs) override{
        for(int i = 0; i< nrs.size()-1; i+=2){
            swap(nrs[i], nrs[i+1]);
        }
    }
};

class Composite : public Transformer{
private:
    unique_ptr<Transformer> t1;
    unique_ptr<Transformer> t2;

public:
    Composite(unique_ptr<Transformer> t1, unique_ptr<Transformer> t2) : t1{std::move(t1)}, t2{std::move(t2)}{}
    void transform(vector<int>& nrs) override{
        t1->transform(nrs);
        t2->transform(nrs);
    }
};

class Numbers{
private:
    unique_ptr<Transformer> t;
public:
    vector<int> nrs;
    Numbers(unique_ptr<Transformer> t1):t{std::move(t1)}{}

    void addd(int nr){
        nrs.push_back(nr);
    }

    void transform() {
        sort(nrs.begin(), nrs.end(), [](int a, int b){
            return a>b;
        });
        t->transform(nrs);
    }

};

Numbers f(){
    auto nl = make_unique<Nuller>(9);
    auto ad = make_unique<Adder>(3);
    auto sp = make_unique<Swapper>();

    auto c1 = make_unique<Composite>(std::move(ad), std::move(sp));
    //auto c2 = make_unique<Composite>(std::move(c1), std::move(nl));

    Numbers n(std::move(c1));
    return n;

}
int main() {
    auto n = f();
    n.addd(5);
    n.addd(3);
    n.addd(4);
    n.addd(7);
    n.addd(2);

    auto n1 = f();
    n1.addd(7);
    n1.addd(34);
    n1.addd(78);
    n1.addd(2);
    n1.addd(9);

    for(auto& nr: n.nrs)
        cout<<nr<<" ";
    cout<<endl;
    for(auto&nr : n1.nrs)
        cout<<nr<<" ";

    cout<<endl;

    n.transform();
    n1.transform();

    for(auto& nr: n.nrs)
        cout<<nr<<" ";
    cout<<endl;
    for(auto&nr : n1.nrs)
        cout<<nr<<" ";

    return 0;
}
