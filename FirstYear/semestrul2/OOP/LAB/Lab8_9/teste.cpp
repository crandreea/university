#include "teste.h"

#include <cassert>
#include <sstream>



//teste repo
void test_repo(){
    DisciplinaRepo repo;
    repo.store(Disciplina("mate", 23, "lab", "Marius"));
    assert(repo.getAll().size()==1);
    repo.store(Disciplina("mate", 2, "curs", "Marius"));
    assert(repo.getAll().size()==2);

    try{
        repo.store(Disciplina("mate", 2, "curs", "Marius"));
        assert(false);
    }
    catch (Exception& ex){
        assert(ex.getMessage().find("Exista deja o disciplina de acest fel!")!= std::string::npos);
        assert(true);
    }

    Disciplina d = repo.search("mate", "curs", "Marius");
    assert(d.getName() == "mate");
    assert(d.getProf() == "Marius");
    assert(d.getType() == "curs");
    assert(d.getHours() == 2);


    try{
        repo.search("cv", "cv", "cv");
        assert(false);
    }
    catch (Exception& ex) {
        assert(ex.getMessage().find("Nu exista aceasta disciplina!")!= std::string::npos);
        assert(true);
    }
    vector<Disciplina> rez = repo.searchbyName("mate");
    assert(rez.size()==2);

    try{
        repo.searchbyName("cv");
        assert(false);
    }
    catch (Exception& ex){
        assert(ex.getMessage().find("Nu exista aceasta disciplina!")!= std::string::npos);
        assert(true);
    }

    d = Disciplina("a", 2, "b", "c");
    repo.store(d);
    repo.sterge(d);
    assert(repo.getAll().size()==2);
}

//teste service
void test_add(){
    DisciplinaRepo repo;
    Validator valid;
    Contract c;
    DisciplinaService service{repo, valid, c};
    service.addDisciplina("mate", 2, "lab", "a");
    assert(service.getAll().size()==1);
}

void test_modify(){
    DisciplinaRepo repo;
    Validator valid;
    Contract c;
    DisciplinaService service{repo, valid, c};
    service.addDisciplina("mate", 2, "lab", "a");
    service.modifyDisciplina("mate", 3, "lab", "a");
    assert(service.getAll()[0].getHours()==3);
}

void test_delete(){
    DisciplinaRepo repo;
    Validator valid;
    Contract c;
    DisciplinaService service{repo, valid, c};
    service.addDisciplina("mate", 2, "lab", "a");
    service.addDisciplina("info", 3, "lab", "b");
    service.deleteDisciplina("info", "lab", "b");
    assert(service.getAll().size()==1);

}

void test_search(){
    DisciplinaRepo repo;
    Validator valid;
    Contract c;
    DisciplinaService service{repo, valid, c};
    service.addDisciplina("mate", 2, "lab", "a");
    Disciplina d = service.searchDisciplina("mate", "lab", "a");
    assert(d.getHours() == 2);
}

void test_filtrareProf() {
    DisciplinaRepo repo;
    Validator valid;
    Contract c;
    DisciplinaService service{repo, valid, c};
    repo.store(Disciplina("mate", 2, "lab", "a"));
    assert(repo.getAll().size()==1);
    repo.store(Disciplina("mate", 3, "curs", "b"));
    repo.store(Disciplina("romana", 5, "seminar", "a"));
    vector<Disciplina> listaFiltrata1 = service.filtrareProf("a");
    assert(listaFiltrata1.size()==2);
    assert(listaFiltrata1[0].getHours()==2);

}

void test_filtrareH() {
    DisciplinaRepo repo;
    Validator valid;
    Contract c;
    DisciplinaService service{repo, valid, c};
    repo.store(Disciplina("mate", 5, "lab", "a"));
    assert(repo.getAll().size()==1);
    repo.store(Disciplina("mate", 3, "curs", "b"));
    repo.store(Disciplina("romana", 5, "seminar", "a"));
    vector<Disciplina> listaFiltrata1 = service.filtrareH(3);
    assert(listaFiltrata1.size()==1);
    assert(listaFiltrata1[0].getName()== "mate");

}


int sortbyDenumire(const Disciplina& o1, const Disciplina& o2) {
    return o1.getName() < o2.getName();
}

int sortbyOre(const Disciplina& o1, const Disciplina& o2) {
    return o1.getHours() < o2.getHours();
}

int sortbyTipProf(const Disciplina& o1, const Disciplina& o2) {
    if (o1.getProf() == o2.getProf()) {
        return (o1.getType() < o2.getType());
    }
    else {
        return o1.getProf() < o2.getProf() ;
    }
}

void test_sortare(){

        DisciplinaRepo repo;
        Validator valid;
        Contract c;
        DisciplinaService service{ repo, valid , c};
        service.addDisciplina("a", 100, "cs", "bb");
        service.addDisciplina("b", 10, "s", "bb");
        service.addDisciplina("c", 1030, "cs", "a");

        const vector<Disciplina> list = service.sortareDiscipline(sortbyDenumire);
        const vector<Disciplina> list2 = service.sortareDiscipline(sortbyOre);
        const vector<Disciplina> list3 = service.sortareDiscipline(sortbyTipProf);

        assert(list.size() == 3);
        assert(list[0].getName() == "a");

        assert(list2.size() == 3);
        assert(list2[0].getName() == "b");

        assert(list3.size() == 3);
        assert(list3[0].getType() == "cs");

}

void test_contract(){
    DisciplinaRepo repo;
    Validator valid;
    Contract c;
    DisciplinaService service{repo, valid, c};
    service.addDisciplina("mate", 2, "lab", "p");
    service.addDisciplina("mate", 1, "curs", "a");
    service.addDisciplina("info", 2, "seminar", "r");
    service.addDisciplina("sport", 2, "curs", "n");
    service.contractAdauga("mate");
    assert(service.getAllContract().size()==2);
    try{
        service.contractAdauga("cv");
    }
    catch (Exception& ex){
        assert(true);
    }
    assert(service.getAllContract().size()==2);
    service.contractSterge();
    service.contractAdaugaRandom(3);
    assert(service.getAllContract().size()==3);

    string fisier_csv = "/Users/croitoruandreea/Desktop/ANUL1/sem2/OOP/LAB/Lab8/test.csv";
    string fisier_html = "/Users/croitoruandreea/Desktop/ANUL1/sem2/OOP/LAB/Lab8/test.html";
    string fisier_alt = "/Users/croitoruandreea/Desktop/ANUL1/sem2/OOP/LAB/Lab8/test.txt";
    service.contractExport(fisier_csv);
    service.contractExport(fisier_html);
    try {
        service.contractExport(fisier_alt);
    }
    catch (Exception& exception) {
        assert(true);
    }
}

void test_undo() {
    DisciplinaRepo repo;
    Validator valid;
    Contract cos;
    DisciplinaService service{repo, valid, cos};
    try {
        service.undo();
    } catch (Exception& ex) {
        assert(true);
    }
    service.addDisciplina("a", 3, "b", "c");
    service.addDisciplina("a", 2, "c", "c");
    service.modifyDisciplina("a", 6, "c", "c");
    service.deleteDisciplina("a", "b", "c");
    service.undo();
    assert(service.getAll().size() == 2);
    auto oferta = repo.search("a", "c", "c");
    service.undo();
    service.undo();
    try {
        repo.search("cv", "cv", "cv");
    } catch (Exception& ex) {
        assert(true);
    }
    service.undo();
    assert(service.getAll().empty());
}

void test_fisier() {
    DisciplinaRepoFile repo{"/Users/croitoruandreea/Desktop/ANUL1/sem2/OOP/LAB/Lab8/test.txt"};
    Validator valid;
    Contract cos;
    DisciplinaService service{repo, valid, cos};
    assert(service.getAll().size() == 0);
    service.addDisciplina("c", 2, "c", "c");
    assert(service.getAll().size() == 1);
    service.deleteDisciplina("c", "c", "c");
}

void testall(){
    test_repo();
    test_add();
    test_modify();
    test_delete();
    test_search();
    test_filtrareProf();
    test_filtrareH();
    test_sortare();
    test_contract();
    test_undo();
    test_fisier();

}