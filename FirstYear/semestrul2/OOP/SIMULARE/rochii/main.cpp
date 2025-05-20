#include <QApplication>
#include <QPushButton>
#include <iostream>

#include "repo.h"
#include "service.h"
#include "gui.h"

using namespace std;
int main(int argc, char *argv[]) {
    QApplication a(argc, argv);
    RepoFile repo("/Users/croitoruandreea/Desktop/ANUL1/sem2/OOP/SIMULARE/rochii/rochii.txt");
    ServiceRochie service{repo};
    GUI gui{service};

    gui.show();
    return QApplication::exec();
}
