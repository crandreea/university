#include <vector>
#include <string>
#include <algorithm>
#include <map>
#include <exception>
#include <iostream>
#include <cassert>
using namespace std;
using std::exception;

/* Ordoneaza descrescator o lista de elemnete dupa numarul de aparitii si returneaza lista ordonata
 * param l: lista de intregi
 * return: l' = l ordonata descrescator
 * arunca: exceptie daca lisat e vida
 */
vector<int> f(vector<int> l) {
    if (l.size() == 0)
        throw invalid_argument("Illegal argument");
    map<int, int> c;
    for (auto e : l) {
        c[e]++;
    }
    sort(l.begin(), l.end(), [&](int a, int b) {
        return c[a] > c[b]; });
    return l;
}

void test(){
    try{
        vector<int> rez = f({4, 3, 5, 1, 1, 5, 5, 4});
        vector<int> rezf = {5, 5, 5, 4, 1, 1, 4, 3};
        assert(rez == rezf);
        assert(true);
    }catch(exception& e){
        assert(false);
    }
    try{
        vector<int> rez = f({4, 3, 5, 1, 1, 5, 3, 4});
        vector<int> rezf = {4, 3, 5, 1, 1, 5, 3, 4};
        assert(rez == rezf);
        assert(true);
    }catch(exception& e){
        assert(false);
    }

    try{
        vector<int> rez = f({});
        assert(false);
    }catch(exception& e){
        assert(true);
    }
}
int main() {
    test();
    return 0;
}
