#include "ui.h"
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

void UI::afiseazaUI(const vector<Disciplina>& elems){
    cout<<"DISCIPLINE"<<endl;
    if(elems.empty())
        cout<<"Nu exista discipline...\n";
    else{
        for(const auto& dis : elems)
            cout << "Denumire: " << dis.getName() << "\tNr.ore/saptamana: " << dis.getHours() << "\tTip: " << dis.getType() << "\tNume cadru didactic: " << dis.getProf() << endl;
        cout<<endl;
    }

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
    return o1.getHours() < o2.getHours();
}

int sortTipProf(const Disciplina& o1, const Disciplina& o2) {
    if (o1.getProf() == o2.getProf()) {
        return (o1.getType() < o2.getType());
    }
    else {
        return o1.getProf() < o2.getProf() ;
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

//void UI::frecventaUI() {
//    cout << "Raport: " << endl;
//    unordered_map<string , int> dict = service.frecventa();
//    for (const auto &pereche: dict) {
//        cout << pereche.first << ": " << pereche.second << endl;
//    }
//}

void UI::contractAdaugaUI() {
    string denumire;
    cout << "Denumirea disciplinei pe care vreti sa o adaugati in contract: ";
    cin >> denumire;
    try {
        service.contractAdauga(denumire);
        cout << "Disciplina s-a adaugat in contract\n";
    } catch (Exception& ex) {
        cout << ex.getMessage() << endl;
    }
}

void UI::contractAdaugaRandomUI() {
    int numar = 0;
    char str[101];
    cout << "Intruduceti numarul de discipline random: ";
    cin >> str;
    numar = stoi(str);
    if (!(numar < 0 || strlen(str) > 10)) {
        try {
            int numar_oferte = service.contractAdaugaRandom(numar);
            cout << "S-au adaugat " << numar_oferte << " discipline random in contract" << endl;
        } catch (Exception& ex) {
            cout << ex.getMessage() << endl;
        }
    }
    else cout << "Numarul este invalid!" << endl;
}

void UI::contractStergeUI() {
    service.contractSterge();
    cout << "S-au sters toate disciplinele din contract!" << endl;
}

void UI::contractExportUI() {
    string filename;
    cout << "Introduceti numele fisierului pentru salvarea contractului(.csv sau .html): " << endl;
    cin >> filename;
    string path = "/Users/croitoruandreea/Desktop/ANUL1/sem2/OOP/LAB/Lab8/";
    //string referinta = "/home/albert/Documents/UBB/Semestrul 2/Programare orientata obiect/Laborator/Lab8-9/Agentie de turism/wishlist/";
    service.contractExport(path.append(filename));
}
void UI::MeniuContract() {
    while (true) {
        cout << "CONTRACT" << endl;
        cout << "Exista " << service.getAllContract().size() << " discipline in contract.\n";
        cout << "--- MENIU ---" << endl;
        cout<< "1. Adaugare disciplina in contract\n2. Adaugare discipline random in contract\n3. Goleste contract\n4. Afisare contract\n5. Export\n6. Iesire\n---\t---\t---\n";
        int optiune = 0;
        cout << "Optiunea: ";
        cin >> optiune;
        try {
            switch (optiune) {
                case 1:
                    contractAdaugaUI();
                    break;
                case 2:
                    contractAdaugaRandomUI();
                    break;
                case 3:
                    contractStergeUI();
                    break;
                case 4:
                    afiseazaUI(service.getAllContract());
                    break;
                case 5:
                    contractExportUI();
                    break;
                case 6:
                    cout << "Iesire...\n";
                    return;
                default:
                    cout << "Optiune invalida!\n";
            }
        } catch (Exception &ex) {
            cout << ex.getMessage() << endl;
        }
        cout<<endl;
    }
}

void UI::run() {
    cout<<"--- MENIU ---"<<endl;
    cout<<"1. Adaugare disciplina\n2. Afiseaza discipline\n3. Modifica numarul de ore\n4. Stergere disciplina\n5. Cautare disciplina\n6. Filtrare dupa numele cadrului didactic\n7. Filtrare dupa numarul de ore\n8. Sortare\n9. Contract\n10. Frecventa\n11. Undo\n0. Iesire\n";
    cout<<endl;
//    service.addDisciplina("sd", 2, "lab", "m");
//    service.addDisciplina("oop", 2, "lab", "i");
//    service.addDisciplina("oop", 2, "curs", "v");
//    service.addDisciplina("sda", 1, "seminar", "m");
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
                case 9:
                    MeniuContract();
                    break;
                case 10:
                    //frecventaUI();
                    break;
                case 11:
                    try {
                        service.undo();
                        cout << "Undo realizat!" << endl;
                    } catch (Exception &ex) {
                        cout << ex.getMessage() << endl;
                    }
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
