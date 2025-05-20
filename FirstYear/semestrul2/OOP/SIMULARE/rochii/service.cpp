#include "service.h"

//void ServiceRochie::addRochie(int id, std::string name, std::string marime, double pret, bool disp) {
//    Rochie r(id, name, marime, pret, disp);
//    Repo.add(r);
//}

bool sortMarime(Rochie& r1, Rochie& r2){
    return r1.get_marime()>r2.get_marime();
}
bool sortPret(Rochie& r1, Rochie& r2){
    return r1.get_pret()>r2.get_pret();
}
vector<Rochie> ServiceRochie::sortByMarime(){
    vector<Rochie> list = getAll();
    sort(list.begin(), list.end(), sortMarime);
    return list;
}
vector<Rochie> ServiceRochie::sortByPret(){
    vector<Rochie> list = getAll();
    sort(list.begin(), list.end(), sortPret);
    return list;
};

