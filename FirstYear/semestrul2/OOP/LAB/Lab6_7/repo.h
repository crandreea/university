#pragma once

#include "disciplina.h"
#include "vector_dinamic.h"
#include "erori.h"
#include <string>
#include <utility>

//using std::vector;
using std::string;
using std::ostream;

class DisciplinaRepo{
private:
    VectorDinamic<Disciplina> elems;
    [[nodiscard]] bool exist(const Disciplina& d) const;

public:

    DisciplinaRepo() = default;
    DisciplinaRepo(const DisciplinaRepo& d) = delete;
    /**
     * Adauga un element in lista elems
     * @param d - disciplina
     */
    void store(const Disciplina& d);

    /**
     * Sterge un element dintr o lista elems
     * @param d - disciplina
     */
    void sterge(Disciplina& d);

    /**
     * Returneaza toate elementele
     * @return
     */
    VectorDinamic<Disciplina>& getAll(){
        return elems;
    }

    /**
     * Cauta un element in lista
     * @param name - denumirea disciplinei
     * @param type - tipul disciplinei
     * @param prof - numele cadrului didactic
     * @return d - disciplina
     */
     const Disciplina& search(const string& name, const string& type, const string& prof) const;

};

/*
class DisciplinaRepoException {
    string msj;
public:
    explicit DisciplinaRepoException(string m): msj{std::move( std::move(m) )} {}
    friend ostream& operator<<(ostream& out, const DisciplinaRepoException& ex);
};

 ostream& operator<<(ostream& out, const Exception& ex) {
    cout<<ex;
    return out;
}
*/