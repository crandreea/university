#include <QApplication>

#include "repo.h"
#include "service.h"
#include "validator.h"
#include "teste.h"
#include "GUI.h"


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
