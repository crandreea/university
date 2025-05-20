#include <iostream>
#include <exception>
#include <cassert>
using namespace std;
using std::exception;

/*
 * Verifica daca un numar este prim (>=2)
 * param a: numar intreg
 * return: true - daca param este prim (>=2)
 *         false - daca param nu este prim
 * arunca : exceptie daca param mai mic sau egal cu 0
 */
bool f(int a) {
    if (a <= 0)
        throw invalid_argument("Illegal argument");
    int d = 2;
    while (d<a && a % d>0) d++;
    return d>=a;
}
//a = 12     a = 13      a = 1     a = 15
//d = 2      d = 13      d = 2     d = 3
//false      true        true      false
void test(){
    try{
        bool rez = f(3);
        assert(rez == true);
        assert(true);

    }catch (exception& e){
        assert(false);
    }

    try{
        bool rez = f(15);
        assert(rez == false);
        assert(true);

    }catch (exception& e){
        assert(false);
    }

    try{
        bool rez = f(-2);
        assert(false);

    }catch (exception& e){
        assert(true);
    }

}

int main() {
    test();
    std::cout << "Hello, World!" << std::endl;
    return 0;
}
