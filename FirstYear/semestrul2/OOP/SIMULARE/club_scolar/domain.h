#pragma once
#include <string>
#include <utility>
using std::string;
using namespace std;

class Elev{
private:
    int Nr{};
    string Name;
    string Scoala;
    vector<string> Club;

public:
    Elev() = default;
    Elev(int nr, string name, string scoala, vector<string> club):Nr{nr}, Name{std::move(name)}, Scoala{std::move(scoala)}, Club{std::move(club)}{

    }
    Elev(const Elev& elev):Nr{elev.get_nr_matricol()}, Name{std::move(elev.get_name())}, Scoala{elev.get_scoala()}, Club{elev.get_club()}{

    }



    [[nodiscard]] string get_name() const{
        return Name;
    }

    [[nodiscard]] string get_scoala() const{
        return Scoala;
    }

    [[nodiscard]] int get_nr_matricol() const{
        return Nr;
    }

    [[nodiscard]] vector<string> get_club() const{
        return Club;
    }

};

class Exception : std::runtime_error{
    string msg;
public:
    Exception(string msg): std::runtime_error(msg), msg(msg){

    }
    string get_mess(){
        return ("\n" + msg);
    }
};