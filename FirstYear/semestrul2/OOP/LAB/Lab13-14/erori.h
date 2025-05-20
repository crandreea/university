#pragma once
#include <string>
#include <utility>

using namespace std;

class Exception: public std::runtime_error{

    string msg;
public :
    explicit Exception(const string& err): std::runtime_error(err), msg(err) {};
    //explicit Exception(string m): std::runtime_error, msg{m}{}

    string getMessage(){
        return ("\n" + this->msg);
    }

};
