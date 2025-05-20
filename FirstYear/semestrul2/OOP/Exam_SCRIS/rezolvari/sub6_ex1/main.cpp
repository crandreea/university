#include <iostream>
#include <vector>
#include <exception>
#include <stdexcept>
#include <cassert>
using namespace std;
using std::vector;

/*
 * Slaveaza si returneaza o lista cu divizorii unui numar
 * param a: numar intreg
 * return: lista de numere intregi
 * arunca: exceptie daca numarul este negativ
 */
vector<int> f(int a) {
    if (a < 0)
        throw invalid_argument("Illegal argument");
    vector<int> rez;
    for (int i = 1; i <= a; i++) {
         if (a % i == 0) {
            rez.push_back(i);
        }
    }
    return rez;
}

void test(){
    try{
        vector<int> rez = f(3);
        assert(rez.size() == 2);
        assert(true);

    }catch (exception& e){
        assert(false);
    }

    try{
        vector<int> rez = f(15);
        assert(rez.size() == 4);
        assert(true);

    }catch (exception& e){
        assert(false);
    }

    try{
        vector<int> rez = f(0);
        assert(rez.empty());
        assert(true);

    }catch (exception& e){
        assert(false);
    }

    try{
        vector<int> rez = f(-5);
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
