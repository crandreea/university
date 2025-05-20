#include <vector>
#include <iostream>
using namespace std;
class A {
public:
    virtual void f() = 0;
};
class B:public A{
public:
    void f() override {
        cout << "f din B";
    }
};
class C :public B {
public:
    void f() override {
        cout << "f din C";
    }
};
int main() {
    vector<A> v; //vectorul incearca sa stocheze elem din clasa A care este pur virtuala
    B b;
    v.push_back(b); //clasa B nu poate fi copiata intr un vector de baza A fara a pierde informatia - pb de slicing
    C c;
    v.push_back(c);
    for (auto e : v) { e.f(); }
    return 0;
}


