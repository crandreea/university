#include "ui.h"
#include "vector_dinamic.h"
#include "erori.h"
#include <iostream>
using namespace std;

void UI::adaugaUI(){
    string name, type, prof;
    int h = -1;
    cout<<"Denumire: ";
    cin>>name;
    cout<<"Ore pe saptamana: ";
    cin>>h;
    cout<<"Tip: ";
    cin>>type;
    cout<<"Nume cadru didactic: ";
    cin>>prof;

    try{
        service.addDisciplina(name, h, type, prof);
        cout<<"Disciplina a fost adaugata!"<<endl;
    }
    catch (Exception& exception){
        cout<<exception.getMessage();
    }

}

void UI::modificaUI(){
    string name, type, prof;
    int h = -1;
    cout<<"Denumire: ";
    cin>>name;
    cout<<"Tipul: ";
    cin>>type;
    cout<<"Nume cadru didactic: ";
    cin>>prof;
    cout<<endl;
    cout<<"Noul numar de ore: ";
    cin>>h;

    try{
        service.modifyDisciplina(name, h, type, prof);
        cout<<"Disciplina a fost modificata!"<<endl;
    }
    catch (Exception& exception){
        cout<<exception.getMessage();
    }

}

void UI::stergeUI(){
    string name, type, prof;
    cout<<"Denumire: ";
    cin>>name;
    cout<<"Tip: ";
    cin>>type;
    cout<<"Nume cadru didactic: ";
    cin>>prof;

    try{
        service.deleteDisciplina(name, type, prof);
        cout<<"Disciplina a fost stearsa!"<<endl;
    }
    catch (Exception& exception){
        cout<<exception.getMessage();
    }

}

void UI::afiseazaUI(const VectorDinamic<Disciplina>& elems){
    cout<<"DISCIPLINE"<<endl;
    for(const auto& dis : elems)
        cout << "Denumire: " << dis.getName() << "\tNr.ore/saptamana: " << dis.getHours() << "\tTip: " << dis.getType() << "\tNume cadru didactic: " << dis.getProf() << endl;
    cout<<endl;
}

void UI::cautareUI(){
    string name, type, prof;
    cout<<"Denumire: ";
    cin>>name;
    cout<<"Tip: ";
    cin>>type;
    cout<<"Nume cadru didactic: ";
    cin>>prof;

    try{
        Disciplina dis = service.searchDisciplina(name, type, prof);
        cout << "Denumire: " << dis.getName() << "\tNr.ore/saptamana: " << dis.getHours() << "\tTip: " << dis.getType() << "\tNume cadru didactic: " << dis.getProf() << endl;
        cout<<endl;
    }
    catch (Exception &exception){
        cout<<exception.getMessage();
    }

}

void UI::filtrareProfUI() {
    string prof;
    cout<<"Introduceti numele cadrului didactic: ";
    cin>>prof;
    afiseazaUI(service.filtrareProf(prof));
}

void UI::filtrareHUI() {
    int h = -1;
    cout<<"Introduceti numarul de ore: ";
    cin>>h;
    afiseazaUI(service.filtrareH(h));
}

int sortDenumire(const Disciplina& o1, const Disciplina& o2) {
    return o1.getName() < o2.getName();
}

int sortOre(const Disciplina& o1, const Disciplina& o2) {
    return o1.getHours() > o2.getHours();
}

int sortTipProf(const Disciplina& o1, const Disciplina& o2) {
    if (o1.getProf() == o2.getProf()) {
        return (o1.getType() < o2.getType());
    }
    else {
        return o1.getProf() < o2.getType() ;
    }
}

void UI::sortareUI() {
    cout<<"1. Denumire | 2. Numar ore | 3. Cadru didactic"<<endl;
    string optiune;
    cout<<"Optiunea: ";
    cin>>optiune;
    if (optiune == "1") {
        auto lista_sortata = service.sortareDiscipline(sortDenumire);
        afiseazaUI(lista_sortata);
    } else if(optiune == "2") {
        auto lista_sortata = service.sortareDiscipline(sortOre);
        afiseazaUI(lista_sortata);
    } else if(optiune == "3") {
        auto lista_sortata = service.sortareDiscipline(sortTipProf);
        afiseazaUI(lista_sortata);
    }
}
void UI::run() {
    cout<<"--- MENIU ---"<<endl;
    cout<<"1. Adaugare disciplina\n2. Afiseaza discipline\n3. Modifica numarul de ore\n4. Stergere disciplina\n5. Cautare disciplina\n6. Filtrare dupa numele cadrului didactic\n7. Filtrare dupa numarul de ore\n8. Sortare\n0. Iesire\n";
    cout<<endl;

    while(true){
        int optiune = -1;
        cout<<"Optiunea: ";
        cin>>optiune;
        try{
            switch (optiune) {
                case 1:
                    adaugaUI();
                    break;
                case 2:
                    afiseazaUI(service.getAll());
                    break;
                case 3:
                    modificaUI();
                    break;
                case 4:
                    stergeUI();
                    break;
                case 5:
                    cautareUI();
                    break;
                case 6:
                    filtrareProfUI();
                    break;
                case 7:
                    filtrareHUI();
                    break;
                case 8:
                    sortareUI();
                    break;
                case 0:
                    cout << "Iesire din aplicatie...";
                    return;
                default:
                    cout << "Comanda invalida!!!\n";
            }
        }
        catch (const Exception& ex){
            cout<<ex.what()<<endl;
        }

    }

}
