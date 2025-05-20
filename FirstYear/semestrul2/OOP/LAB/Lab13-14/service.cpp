#include "service.h"
#include <string>
#include <vector>
#include <fstream>



void DisciplinaService::addDisciplina(const string &name, int h, const string &type, const string &prof) {
    Validator::validateDisciplina(name, h, type, prof);
    Disciplina d{name, h, type, prof};
    Repo.store(d);
    undoActions.push_back(std::make_unique<UndoAdauga>(d, Repo));
    notify();
}

void DisciplinaService::modifyDisciplina(const string &name, int h, const string &type, const string &prof) {
    Validator::validateDisciplina(name, h, type, prof);
    Disciplina d = Repo.search(name, type, prof);
    Repo.sterge(d);
    Disciplina new_d{name, h, type, prof};
    Repo.store(new_d);
    undoActions.push_back(std::make_unique<UndoModifica>(d, new_d, Repo));
    notify();

}

void DisciplinaService::deleteDisciplina(const string &name, const string &type, const string &prof) {
    Validator::validateDisciplina(name, 1, type, prof);
    Disciplina d = Repo.search(name, type, prof);
    Repo.sterge(d);
    undoActions.push_back(std::make_unique<UndoSterge>(d, Repo));
    notify();
}

const Disciplina &DisciplinaService::searchDisciplina(const string &name, const string &type, const string &prof) {
    return Repo.search(name, type, prof);
}

vector<Disciplina> DisciplinaService::filtrare(const function<bool(const Disciplina &)> &fct) {
    const vector<Disciplina> diss = getAll();
    vector<Disciplina> rez;
    std::copy_if(diss.begin(), diss.end(), back_inserter(rez), fct);
    return rez;
}


vector<Disciplina> DisciplinaService::filtrareProf(const std::string &prof) {
    return filtrare([prof](const Disciplina &d) {
        return d.getProf() == prof;
    });
}

vector<Disciplina> DisciplinaService::filtrareH(int h) {
    return filtrare([h](const Disciplina &d) {
        return d.getHours() <= h;
    });
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

vector<Disciplina> DisciplinaService::sortareDisciplineDenumire() {
    vector<Disciplina> &lista_sortata = getAll();
    sort(lista_sortata.begin(), lista_sortata.end(), sortDenumire);
    return lista_sortata;
}
vector<Disciplina> DisciplinaService::sortareDisciplineOre() {
    vector<Disciplina> &lista_sortata = getAll();
    sort(lista_sortata.begin(), lista_sortata.end(), sortOre);
    return lista_sortata;
}
vector<Disciplina> DisciplinaService::sortareDisciplineTipProf() {
    vector<Disciplina> &lista_sortata = getAll();
    sort(lista_sortata.begin(), lista_sortata.end(), sortTipProf);
    return lista_sortata;
}

void DisciplinaService::contractAdauga(const string &denumire) {
    vector<Disciplina> rez = Repo.searchbyName(denumire);
    for (const auto &d: rez)
        ContractCurr.adaugaDisciplinaContract(d);
}

int DisciplinaService::contractAdaugaRandom(int nr) {
    ContractCurr.adaugaDisciplinaRandom(getAll(), nr);
    return ContractCurr.getAllContracte().size();
}

const vector<Disciplina> &DisciplinaService::getAllContract() {
    return ContractCurr.getAllContracte();
}

void DisciplinaService::contractSterge() {
    ContractCurr.stergeContract();
}

//unordered_map<string, int> DisciplinaService::frecventa() {
//    unordered_map<string, int> dict;
//    vector<Disciplina> elems = getAll();
//    for (const auto &d: elems) {
//        dict[d.getType()]++;
//    }
//    return dict;
//}

void DisciplinaService::contractExport(const string &filename) {

    if (filename.find(".csv") == std::string::npos && filename.find(".html") == std::string::npos) {
        throw Exception("Fisierul nu este valid!");
    }
    else {
        ofstream fout(filename);
        if (filename.find(".html") != std::string::npos) {
            fout << "<html>";
            fout << "<style> table, th, td {border:1px solid black} body{background-color: #E6E6FA;} </style>";
            fout << "<body>";
            fout << "<h1> CONTRACT </h1>";
            fout << "<table><tr><th>Denumire</th> <th>Numar ore</th> <th>Tip</th> <th>tip </th><th>Nume cadru didactic</th></tr>";
            for (auto &o: getAllContract()) {
                fout << "<tr><td>" << o.getName() << "<td>" << o.getHours() << "</td><td>" << o.getType()<< "</td><td>" << o.getProf() << "</td></tr>";
            }
            fout << "</table></body>";
            fout << "<html>";
        }
        else {
            for (auto &o: getAllContract()) {
                fout << o.getName() << ";" << o.getHours() << ";" << o.getType()<< ";" << o.getProf() << endl;
            }
        }
        fout.close();
    }
}

void DisciplinaService::undo() {
    if (undoActions.empty())
        throw Exception("Undo nu se mai poate realiza...");
    undoActions.back()->doUndo();
    undoActions.pop_back();
}