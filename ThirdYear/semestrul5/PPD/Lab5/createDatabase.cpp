#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <sqlite3.h>

using namespace std;

void createDatabase() {
    sqlite3* db;
    int rc = sqlite3_open("studenti.db", &db);
    
    if (rc != SQLITE_OK) {
        cerr << "Eroare la creare db: " << sqlite3_errmsg(db) << endl;
        return;
    }
    
    char* errMsg = nullptr;
    
    for (int i = 1; i <= 10; i++) {
        string tableName = "proiect" + to_string(i);
        string fileName = tableName + ".txt";
        
        string createTable = "CREATE TABLE IF NOT EXISTS " + tableName + 
                           " (id INTEGER, nota INTEGER);";
        rc = sqlite3_exec(db, createTable.c_str(), nullptr, nullptr, &errMsg);
        
        if (rc != SQLITE_OK) {
            cerr << "Eroare la creare tabel " << tableName << ": " << errMsg << endl;
            sqlite3_free(errMsg);
            continue;
        }
        
        // sterge datele care exista deja
        string deleteData = "DELETE FROM " + tableName + ";";
        sqlite3_exec(db, deleteData.c_str(), nullptr, nullptr, nullptr);
        
        // introduc datele din fisier in tabel
        ifstream fin(fileName);
        if (!fin.is_open()) {
            cerr << "Nu se poate deschide " << fileName << endl;
            continue;
        }
        
        string line;
        sqlite3_exec(db, "BEGIN TRANSACTION;", nullptr, nullptr, nullptr);
        
        while (getline(fin, line)) {
            for (char &c : line)
                if (c=='(' || c==')' || c==',') c=' ';
            
            int id, nota;
            stringstream ss(line);
            ss >> id >> nota;
            
            string insert = "INSERT INTO " + tableName + 
                          " (id, nota) VALUES (" + 
                          to_string(id) + ", " + to_string(nota) + ");";
            
            rc = sqlite3_exec(db, insert.c_str(), nullptr, nullptr, &errMsg);
            if (rc != SQLITE_OK) {
                cerr << "Eroare la inserare: " << errMsg << endl;
                sqlite3_free(errMsg);
            }
        }
        
        sqlite3_exec(db, "COMMIT;", nullptr, nullptr, nullptr);
        fin.close();
        
        cout << "Tabelul " << tableName << " a fost creat si populat\n";
    }
    
    sqlite3_close(db);
    cout << "\nBaza de date 'studenti.db' a fost creata\n";
}

int main() {
    createDatabase();
    return 0;
}