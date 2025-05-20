#include <iostream>
using namespace std;
using std::string;
template <typename T>

class Geanta{
private:
    vector<T> elems;
public:
    Geanta()=default;
    Geanta(T elemprim){
        elems.push_back(elemprim);
    }

    Geanta operator+ (T elem){
        elems.push_back(elem);
        return *this;
    }

    Geanta<T>& operator= (const T alteelems){
        elems = alteelems;
        return *this;
    }

    auto begin(){
        return elems.begin();
    }

    auto end(){
        return elems.end();
    }

};

void calatorie() {
    Geanta<string> ganta{ "Ion" };//creaza geanta pentru Ion
    ganta = ganta + string{ "haine" }; //adauga obiect in ganta
    ganta + string{ "pahar" };
    for (auto o : ganta) {//itereaza obiectele din geanta
        cout << o << "\n";
    }
}

int main() {
    std::cout << "Hello, World!" << std::endl;
    return 0;
}
