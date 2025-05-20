#include "repo.h"
#include <fstream>
#include <sstream>

void RepoCaz::add(Caz caz) {
    if(exist(caz))
        throw Exception("Cazul exista");
    if(caz.get_tip() != "typo" && caz.get_tip() != "grammar" && caz.get_tip() != "incorrect tense")
        throw Exception("Tip incorect!");
    if(caz.get_severity() != "minor" && caz.get_severity() != "medium" && caz.get_severity() != "major")
        throw Exception("Severitate incorecta!");

    cazuri.push_back(caz);
}

void RepoCaz::read() {
    ifstream in(fileName);
    if(!in.is_open())
        throw Exception("Eroare deschidere fisier!");

    string line;
    while (getline(in, line)) {
        stringstream ss(line);

        int id;
        string good, bad, tip, severity;

        if ((ss >> id) && ss.get() == ',' &&
            std::getline(ss, good, ',') &&
            std::getline(ss, bad, ',') && std::getline(ss, tip, ',') && std::getline(ss, severity, ',')
            ) {

            Caz c(id, bad, good, tip, severity);
            add(c);
        }
    }

    in.close();

}




