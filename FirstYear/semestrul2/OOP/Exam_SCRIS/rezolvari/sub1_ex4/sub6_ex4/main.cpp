#include <iostream>
using namespace std;
using std::string;
template <typename T>
class Catalog{
    vector<T> val;
    string materie;
public:
    Catalog() = default;
    Catalog(string materie):materie(materie){}

    Catalog<T>& operator+(T val_new){
        val.push_back(val_new);
        return *this;
    }

    auto begin(){
        return val.begin();
    }

    auto end(){
        return val.end();
    }

};
void catalog() {
    Catalog<int> cat{ "OOP" };//creaza catalog cu note intregi
    cat + 10; //adauga o nota in catalog
    cat = cat + 8 + 6;
    int sum = 0;
    for (auto n : cat) { sum += n; } //itereaza notele din catalog
    std::cout << "Suma note:" << sum << "\n";
}

int main() {
    std::cout << "Hello, World!" << std::endl;
    return 0;
}
