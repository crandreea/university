#include "headers/validator.h"
#include "headers/erori.h"

bool validString(string str) {
    if (str.empty()) return false;
    for (int i = 0; i < str.size(); i++) {
        if (!((str.at(i) >= 'a' && str.at(i) <= 'z') || (str.at(i) >= 'A' && str.at(i) <= 'Z') || str.at(i) == ' ')) return false;
    }
    return true;
}

void Validator::validateDisciplina(string name, int h, string type, string prof) {
    string errors;

    if (!validString(std::move(name))) errors.append("Denumirea este invalida!\n");
    if (!validString(std::move(prof))) errors.append("Numele profesorului este invalida!\n");
    if (!validString(std::move(type))) errors.append("Tipul este invalid!\n");
    if (h <= 0) errors.append("Numarul de ore este invalid!\n");
    if (!errors.empty()) throw Exception(errors);
}
