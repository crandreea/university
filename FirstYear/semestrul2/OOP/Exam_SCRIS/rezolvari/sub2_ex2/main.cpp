#include <iostream>
#include <vector>
struct A {
    A() {std::cout << "A";}
    virtual void print() {
        std::cout << "A";
    }
};
struct B : public A{
    B() { std::cout << "B"; }
    void print() override {
        std::cout << "B";
    }
};
int main() {
    std::vector<A> v;
    v.push_back(A{}); //A
    v.push_back(B{}); //AB
    for (auto& el : v) el.print(); //AA
    return 0;
}

//AABAA