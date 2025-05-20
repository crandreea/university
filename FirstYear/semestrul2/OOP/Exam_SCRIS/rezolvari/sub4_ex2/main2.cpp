#include <iostream>
using namespace std;
class A {
public:
    A() {cout << "A" << endl;}
    ~A() {cout << "~A" << endl; }
    void print() {cout << "print" <<
                       endl;}
};
void f() {
    A a[2]; //A A
    a[0].print(); //print
} //~A ~A
int main() {
    f();
    return 0;
}

