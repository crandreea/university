#include "service.h"
#include "repo.h"

void ServiceCaz::add(int id, string& bad, string& good, string& tip, string& severity){
    Caz c{id, bad, good, tip, severity};
    Repo.add(c);
}

bool sortGood(Caz& c1, Caz& c2){
    return c1.get_good()>c2.get_good();
}
bool sortTip(Caz& c1, Caz& c2){
    return c1.get_tip()>c2.get_tip();
}

vector<Caz> ServiceCaz::sortByGood(){
    vector<Caz> list = Repo.getAll();
    sort(list.begin(), list.end(), sortGood);
    return list;

}
vector<Caz> ServiceCaz::sortByTip(){
    vector<Caz> list = Repo.getAll();
    sort(list.begin(), list.end(), sortTip);
    return list;
}
vector<Caz> ServiceCaz::sortBySeverity(){
    vector<Caz> list = Repo.getAll();
    vector<Caz> sortata;
    int cnt = 0;
    for(auto const &el : list)
        if(el.get_severity() == "minor")
            {
                sortata.push_back(el);
            }

    for(auto const &el : list)
        if(el.get_severity() == "mediums")
            {
                sortata.push_back(el);
            }
    for(auto const &el : list)
       if (el.get_severity() == "major") {
                    sortata.push_back(el);
                }
                return sortata;
}