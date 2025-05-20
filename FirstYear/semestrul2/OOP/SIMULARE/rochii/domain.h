#pragma once
#include <string>
#include <utility>
using std::string;
using namespace std;

class Rochie{
private:
    int Id{};
    string Name;
    string Marime;
    double Pret{};
    bool Disponibil{};


public:
    [[nodiscard]] int get_id() const{
        return Id;
    }
    string get_name() const{
        return Name;}

    string get_marime() const {
        return Marime;}
    [[nodiscard]] double get_pret() const{
        return Pret;}
    [[nodiscard]] bool get_disponibil() const{
        return Disponibil;}

    Rochie() = default;
    Rochie(int id, string name, string marime, double pret, bool disponibil):Id{id}, Name{std::move(name)}, Marime{std::move(marime)}, Pret{pret}, Disponibil{disponibil}{

    }
    Rochie(Rochie const &r): Id{r.get_id()}, Name{r.get_name()}, Marime{r.get_marime()}, Pret{r.get_pret()}, Disponibil{r.get_disponibil()}{
    }

};

class Exception : std::runtime_error{
private:
    string msg;
public:
    explicit Exception(string msgg) : std::runtime_error(msgg), msg{msgg}{

    }
    string get_mess(){
        return ("\n" + msg);
    }
};