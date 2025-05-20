#pragma once
#include <string>
#include <iostream>

using std::cout;
using std::string;

class Disciplina{

    string Name;
    int H{};
    string Type;
    string Prof;

public:
    /**
     * Getter pentru denumire
     * @return
     */
    [[nodiscard]] string getName() const{
        return Name;
    }

    /**
     * Getter pentru tip
     * @return
     */
    [[nodiscard]] string getType() const{
        return Type;
    }

    /**
     * Getter pentru nume profesor
     * @return
     */
    [[nodiscard]] string getProf() const{
        return Prof;
    }

    /**
     * Getter pentru numar de ore pe saptamana
     * @return
     */
    [[nodiscard]] int getHours() const{
        return H;
    }

    Disciplina() = default;
    /**
     * Constructor disciplina
     * @param name
     * @param h
     * @param type
     * @param prof
     */
    Disciplina(string  name, int h, string  type, string  prof) : Name{std::move(name)}, H{(h)}, Type{std::move(type)}, Prof{std::move(prof)}{

    }

    Disciplina(const Disciplina& d): Name{d.getName()}, H{d.getHours()}, Type{d.getType()}, Prof{d.getProf()} {
        //cout<<"Copie efectuata cu succes!\n";
    }
};