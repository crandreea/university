#pragma once

#include "disciplina.h"
#include "erori.h"
#include <string>
#include <utility>

using std::vector;
using std::string;
using std::ostream;


class RepoAbstract{
public:

    RepoAbstract() = default;
    //RepoAbstract(const RepoAbstract& d) = delete;
    /**
     * Adauga un element in lista elems
     * @param d - disciplina
     */
    virtual void store(const Disciplina& d) = 0;

    /**
     * Sterge un element dintr o lista elems
     * @param d - disciplina
     */
    virtual void sterge(Disciplina& d) = 0 ;

    /**
     * Returneaza toate elementele
     * @return
     */
    virtual vector<Disciplina>& getAll() = 0;

    /**
     * Cauta un element in lista
     * @param name - denumirea disciplinei
     * @param type - tipul disciplinei
     * @param prof - numele cadrului didactic
     * @return d - disciplina
     */
    virtual const Disciplina& search(const string& name, const string& type, const string& prof) = 0;
    virtual vector<Disciplina> searchbyName(const string& name) = 0;

};

class DisciplinaRepo : public RepoAbstract{
private:
    vector<Disciplina> elems;
    [[nodiscard]] bool exist(const Disciplina& d);

public:

    DisciplinaRepo() = default;
    DisciplinaRepo(const DisciplinaRepo& d) = delete;

    void store(const Disciplina& d) override;


    void sterge(Disciplina& d) override;


    vector<Disciplina>& getAll() override{
        return elems;
    }

    const Disciplina& search(const string& name, const string& type, const string& prof) override;
    vector<Disciplina> searchbyName(const string& name) override;
};


class DisciplinaRepoFile : public DisciplinaRepo {
private:
    string fileName;

    void loadFromFile();

    void writeToFile();

public:
    explicit DisciplinaRepoFile(string filename) : DisciplinaRepo(), fileName{std::move(filename)} {
        loadFromFile();
    }

    void store(const Disciplina& d) override{
        DisciplinaRepo::store(d);
        writeToFile();
    }

    void sterge(Disciplina& d) override{
        DisciplinaRepo::sterge(d);
        writeToFile();
    }

};