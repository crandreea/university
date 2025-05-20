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

    d = Disciplina("a", 2, "b", "c");
    repo.store(d);
    repo.sterge(d);
    assert(repo.getAll().size()==2);
}

//teste service
void test_add(){
    DisciplinaRepo repo;
    Validator valid;
    DisciplinaService service{repo, valid};
    service.addDisciplina("mate", 2, "lab", "a");
    assert(service.getAll().size()==1);
}

void test_modify(){
    DisciplinaRepo repo;
    Validator valid;
    DisciplinaService service{repo, valid};
    service.addDisciplina("mate", 2, "lab", "a");
    service.modifyDisciplina("mate", 3, "lab", "a");
    assert(service.getAll().get(0).getHours()==3);
}

void test_delete(){
    DisciplinaRepo repo;
    Validator valid;
    DisciplinaService service{repo, valid};
    service.addDisciplina("mate", 2, "lab", "a");
    service.addDisciplina("info", 3, "lab", "b");
    service.deleteDisciplina("info", "lab", "b");
    assert(service.getAll().size()==1);

}

void test_search(){
    DisciplinaRepo repo;
    Validator valid;
    DisciplinaService service{repo, valid};
    service.addDisciplina("mate", 2, "lab", "a");
    Disciplina d = service.searchDisciplina("mate", "lab", "a");
    assert(d.getHours() == 2);
}

void test_filtrareProf() {
    DisciplinaRepo repo;
    Validator valid;
    DisciplinaService service{repo, valid};
    repo.store(Disciplina("mate", 2, "lab", "a"));
    assert(repo.getAll().size()==1);
    repo.store(Disciplina("mate", 3, "curs", "b"));
    repo.store(Disciplina("romana", 5, "seminar", "a"));
    VectorDinamic<Disciplina> listaFiltrata1 = service.filtrareProf("a");
    assert(listaFiltrata1.size()==2);
    assert(listaFiltrata1.get(0).getHours()==2);

}

void test_filtrareH() {
    DisciplinaRepo repo;
    Validator valid;
    DisciplinaService service{repo, valid};
    repo.store(Disciplina("mate", 5, "lab", "a"));
    assert(repo.getAll().size()==1);
    repo.store(Disciplina("mate", 3, "curs", "b"));
    repo.store(Disciplina("romana", 5, "seminar", "a"));
    VectorDinamic<Disciplina> listaFiltrata1 = service.filtrareH(3);
    assert(listaFiltrata1.size()==1);
    assert(listaFiltrata1.get(0).getName()== "mate");

}

int sortbyDenumire(const Disciplina& o1, const Disciplina& o2) {
    return o1.getName().compare(o2.getName());
}

int sortbyOre(const Disciplina& o1, const Disciplina& o2) {
    return o1.getHours() > o2.getHours();
}

int sortbyTipProf(const Disciplina& o1, const Disciplina& o2) {
    if (o1.getProf() == o2.getProf()) {
        return (o1.getProf().compare(o2.getProf()));
    }
    else {
        return o1.getType().compare(o2.getType()) ;
    }
}

void test_sortare(){

        DisciplinaRepo repo;
        Validator valid;
        DisciplinaService service{ repo, valid };
        service.addDisciplina("a", 100, "cs", "bb");
        service.addDisciplina("b", 10, "s", "bb");
        service.addDisciplina("c", 1030, "cs", "b");

        auto list = service.sortareDiscipline(sortbyDenumire);
        auto list2 = service.sortareDiscipline(sortbyOre);
        auto list3 = service.sortareDiscipline(sortbyTipProf);

        assert(list.size() == 3);
        assert(list.get(0).getName() == "a");

        assert(list2.size() == 3);
        assert(list2.get(0).getName() == "b");

        assert(list3.size() == 3);
        assert(list3.get(0).getType() == "cs");

}
template <typename MyVector>
MyVector testCopyIterate(MyVector v) {
    int totalHours = 0;
    for (const auto& el : v) {
        totalHours += el.getHours();
    }
    Disciplina p{ "total",totalHours, "tt", "sdd" };
    v.push_back(p);
    return v;
}

template <typename MyVector>
void addOferte(MyVector& v, int cate) {
    for (int i = 0; i < cate; i++) {
        Disciplina p{ std::to_string(i) + "_denumire", i + 10,std::to_string(i) + "_tip",std::to_string(i) + "_prof" };
        v.push_back(p);
    }
}

template <typename MyVector>
void testCreateCopyAssign() {
    MyVector v;//default constructor
    addOferte(v, 100);
    assert(v.size() == 100);
    assert(v.get(50).getType() == "50_tip");

    MyVector v2{ v };//constructor de copiere
    assert(v2.size() == 100);
    assert(v2.get(50).getType() == "50_tip");

    MyVector v3;//default constructor
    v3 = v;//operator=   assignment
    assert(v3.size() == 100);
    assert(v3.get(50).getType() == "50_tip");

    auto v4 = testCopyIterate(v3);
    assert(v4.size() == 101);
    assert(v4.get(50).getType() == "50_tip");
}

template <typename MyVector>
void testMoveConstrAssgnment() {
    std::vector<MyVector> v;
    //adaugam un vector care este o variabila temporara
    //se v-a apela move constructor
    v.push_back(MyVector{});

    //inseram, la fel se apeleaza move costructor de la vectorul nostru
    v.insert(v.begin(), MyVector{});

    assert(v.size() == 2);

    MyVector v2;
    addOferte(v2, 50);

    v2 = MyVector{};//move assignment

    assert(v2.size() == 0);

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


    testCreateCopyAssign<VectorDinamic<Disciplina>>();
    testMoveConstrAssgnment<VectorDinamic<Disciplina>>();

}