#include <iostream>
#include <cassert>


/*
 * Returneaza perechea cu primele doua numere cele mia mari dintr-o lista
 * param l: lista de numere intregi
 * return: pereche(x, y) unde x si y > orice elem din lista diferit de ele
 * arunca: exceptie daca nu exista cel putin doua elem in lista
 */
std::pair<int, int> f(std::vector<int> l) {
    if (l.size()<2) throw std::exception{};
    std::pair<int, int> rez{-1,-1};
    for (auto el:l) {
        if (el < rez.second) continue;
        if (rez.first < el) {
            rez.second = rez.first;
            rez.first = el;
        }else{
            rez.second=el;
        }
    }
    return rez;
}
// 3 4 2 7
// 7 4
void test(){
    try{
        std::pair<int, int> rez = f({3, 4, 2, 7});
        assert(rez.first == 7);
        assert(rez.second == 4);
        assert(true);
    }
    catch (std::exception& e)
    {
        assert(false);
    }

    try{
        std::pair<int, int> rez = f({3, -2, 0, 3});
        assert(rez.first == 3);
        assert(rez.second == 3);
        assert(true);
    }
    catch (std::exception& e)
    {
        assert(false);
    }

    try{
        std::pair<int, int> rez = f({3});
        assert(false);
    }
    catch (std::exception& e)
    {
        assert(true);
    }

}
int main() {
    test();
    std::cout << "Hello, World!" << std::endl;
    return 0;
}
