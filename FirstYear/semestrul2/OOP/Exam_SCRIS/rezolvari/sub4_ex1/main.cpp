#include <iostream>
#include <cassert>
using namespace std;

/*
 * Verifica daca numarul este prim
 * param a: numar intreg
 * return: true - nr este prim
 *         false - nr nu este prim
 * arunca: exceptie daca param mai mic sau egal cu 1
 *
 */
bool f(int a) {
    if (a <= 1)
        throw "Illegal argument";
    int aux = 0;
    for (int i = 2; i < a; i++) {
        if (a % i == 0) {
            aux++;
        }
    }
    return aux == 0;
}

void test(){
    try{
        bool rez = f(2);
        assert(rez == true);
        assert(true);

    }catch (exception& e){
        assert(false);
    }
    try{
        bool rez = f(13);
        assert(rez == true);
        assert(true);

    }catch (exception& e){
        assert(false);
    }
    try{
        f(-5);
        assert(false);

    }catch (const char* e){
        assert(true);
    }



}
int main() {
    test();
    std::cout << "Hello, World!" << std::endl;
    return 0;
}
