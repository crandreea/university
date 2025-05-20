#include <QApplication>

#include "headers/repo.h"
#include "headers/service.h"
#include "headers/validator.h"
#include "headers/teste.h"
#include "headers/GUI.h"


int main(int argc, char *argv[]) {
    testall();
    QApplication a(argc, argv);
    string fileName = "/Users/croitoruandreea/Desktop/ANUL1/sem2/OOP/LAB/Lab10/discipline.txt";
    //DisciplinaRepo repo;
    DisciplinaRepoFile repo{fileName};
    Validator valid;
    Contract contract;
    DisciplinaService service{repo, valid, contract};
    //UI ui{service};
    //ui.run();
    ServiceGUI gui{service};
    gui.show();
    return a.exec();
}
