#include <iostream>
#include <exception>
#include <cassert>
using namespace std;
using std::exception;

/*
 * Calculeaza si returneaza oglinditul numarului
 * param x: intreg
 * return: x' = oglinditul lui x daca x>0
 *         @exceptie "Invalid argument" daca x<=0
 */
int f(int x) {
    if (x <= 0)
        throw invalid_argument("Invalid argument!");
    int rez = 0;
    while (x)
    {
        rez = rez * 10 + x % 10;
        x /= 10;
    }
    return rez;
}

int test(){
    try{
        assert(f(123) == 321);
        assert(true);
    }
    catch (exception& e)
    {
        assert(false);
    }

    try{
        f(-123);
        assert(false);
    }
    catch (exception& e)
    {
        assert(true);
    }

    try{
        f(0);
        assert(false);
    }
    catch (exception& e)
    {
        assert(true);
    }


}
int main() {
    test();
    return 0;
}
