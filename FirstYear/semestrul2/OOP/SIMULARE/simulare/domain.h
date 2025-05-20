#pragma once
#include <string>
using namespace std;
class Caz{
private:
    int Id;
    string Bad;
    string Good;
    string Tip;
    string Severity;

public:
    int get_id() const{
        return Id;
    }

    [[nodiscard]] string get_bad() const{
        return Bad;
    }

    [[nodiscard]] string get_good() const{
        return Good;
    }

    [[nodiscard]] string get_tip() const{
        return Tip;
    }

    [[nodiscard]] string get_severity() const{
        return Severity;
    }

    Caz(int id, string& bad, string& good, string& tip, string& severity):Id{id}, Bad{bad}, Good{good}, Tip{tip}, Severity{severity}{

    }

};

class Exception : std::runtime_error{
private:
    string msg;
public:
    explicit Exception(string msgg): std::runtime_error(msgg), msg{msgg}{

    }
    string get_msg(){
        return ("\n" + msg);
    }
};