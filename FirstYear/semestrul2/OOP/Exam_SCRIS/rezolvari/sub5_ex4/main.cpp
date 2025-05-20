#include <iostream>
using namespace std;
using std::string;
template<typename T>
class Cos{
    T val;
    vector<T> vals;
public:
    Cos() = default;

    Cos operator+(T val_new){
        vals.push_back(val);
        val+=val_new;
        return *this;
    }

    void tipareste(ostream& os){
         os << val<< " ";
    }

    Cos<T>& undo(){
        if(!vals.empty())
        {
            val = vals.back();
            vals.pop_back();
        }
        return *this;
    }


};
void cumparaturi() {
    Cos<string> cos;//creaza un cos de cumparaturi
    cos = cos + "Mere"; //adauga Mere in cos
    cos.undo();//elimina Mere din cos
    cos + "Mere"; //adauga Mere in cos
    cos = cos + "Paine" + "Lapte";//adauga Paine si Lapte in cos
    cos.undo().undo();//elimina ultimele doua produse adaugate

    cos.tipareste(cout);//tipareste elementele din cos (Mere)
}
int main() {
    cumparaturi();
    return 0;
}
