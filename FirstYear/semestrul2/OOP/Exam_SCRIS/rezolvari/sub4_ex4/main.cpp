#include <iostream>
using namespace std;

template <typename T>
class Expresie{
private:
    T val;
    vector<T> vals;
public:

    Expresie() = default;
    Expresie(T val):val{val}{
        vals.push_back(0);
    }

    T valoare() const{
        return val;
    }

    Expresie operator+(T new_val){
        vals.push_back(val);
        val+=new_val;
        return *this;
    }

    Expresie operator-(T new_val){
        vals.push_back(val);
        val-=new_val;
        return *this;
    }

    Expresie<T>& undo(){
        if(!vals.empty())
        {
            val = vals.back();
            vals.pop_back();
        }
        else cout<<" ";
        return *this;
    }

};
void operatii() {
    Expresie<int> exp{ 3 };//construim o expresie,pornim cu operandul 3
//se extinde expresia in dreapta cu operator (+ sau -) si operand
    exp = exp + 7 + 3;
    exp = exp - 8;
//tipareste valoarea expresiei (in acest caz:5 rezultat din 3+7+3-8)
    cout << exp.valoare()<<"\n";
    exp.undo(); //reface ultima operatie efectuata
//tipareste valoarea expresiei (in acest caz:13 rezultat din 3+7+3)
    cout << exp.valoare() << "\n";
    exp.undo().undo();
    cout << exp.valoare() << "\n"; //tipareste valoarea expresiei (in acest caz:3)
}

int main() {
    operatii();
    return 0;
}
