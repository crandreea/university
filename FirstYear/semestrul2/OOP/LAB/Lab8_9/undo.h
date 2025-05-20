#pragma once
#include "disciplina.h"
#include "repo.h"

class ActiuneUndo{
public:
    virtual void doUndo() = 0;
    virtual ~ActiuneUndo(){};
};



class UndoAdauga : public ActiuneUndo{
    Disciplina discAdaugata;
    RepoAbstract& Repo;
public:
    UndoAdauga(const Disciplina &disc, RepoAbstract &repo) : discAdaugata{disc}, Repo{repo}{

    }

    void doUndo() override{
        Repo.sterge(discAdaugata);
    }
};

class UndoSterge : public ActiuneUndo{
    Disciplina discStearsa;
    RepoAbstract& Repo;
public:
    UndoSterge(const Disciplina &disc, RepoAbstract &repo) : discStearsa{disc}, Repo{repo}{

    }

    void doUndo() override{
        Repo.store(discStearsa);
    }
};

class UndoModifica : public ActiuneUndo{
    Disciplina discVeche, discNoua;
    RepoAbstract& Repo;
public:
    UndoModifica(const Disciplina& disc, const Disciplina & discnou, RepoAbstract &repo) : discVeche{disc}, discNoua{discnou}, Repo{repo}{

    }

    void doUndo() override{
        Repo.sterge(discVeche);
        Repo.store(discNoua);
    }
};