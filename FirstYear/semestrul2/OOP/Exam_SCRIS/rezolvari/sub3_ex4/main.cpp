#include <iostream>
using namespace std;
using std::string;

class Examen{
private:
    string descriere;
    string ora;
public:
    Examen(string descriere, string ora):descriere{descriere}, ora{ora}{}

    string getDescriere(){
        return descriere + " ora " + ora;
    }

};

template <typename T>
class ToDo{
private:
    vector<T> elems;
public:
    ToDo() = default;

    ToDo<T>& operator<<(const T exam){
        elems.push_back(exam);
        return *this;
    }

    void printToDoList(ostream& os ){
        for(auto& e : elems)
            os << e.getDescriere()<<";";
    }

};
void todolist() {
    ToDo<Examen> todo;
    Examen oop{ "oop scris","8:00" };
    todo << oop << Examen{"oop practic", "11:00"}; //Adauga 2 examene la todo
    std::cout << oop.getDescriere(); //tipareste la consola: oop scris ora 8:00
//itereaza elementele adaugate si tipareste la consola lista de activitati
//in acest caz tipareste: De facut:oop scris ora 8:00;oop practic ora 11:00
    todo.printToDoList(std::cout);
}

int main() {
    todolist();
    return 0;
}
