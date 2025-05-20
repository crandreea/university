#pragma once
#include "repo.h"
#include "service.h"
#include <cassert>

void testRepo(){
    string file = "/Users/croitoruandreea/Desktop/ANUL1/sem2/OOP/Exam_PRACTIC/melodie1/teste.txt";
    RepoMelodie repo(file);
    auto lista = repo.getAll();
    assert(lista.size() == 8);
    repo.modifica(6,"kkk",5);
    lista = repo.getAll();
    assert(lista.size() == 8);
    Melodie m{8,"hhhdh","oscar",7};
    repo.sterge(m);
    lista = repo.getAll();
    assert(lista.size() == 7);
    repo.writeToFile();
}

void testService(){
    string file = "/Users/croitoruandreea/Desktop/ANUL1/sem2/OOP/Exam_PRACTIC/melodie1/teste.txt";
    RepoMelodie repo(file);
    ServiceMelodii service{repo};
    auto lista = service.getAll();
    assert(lista.size() == 7);
    service.modifica(1,"xxcc", 7);
    assert(lista.size() == 7);
    repo.writeToFile();
}

