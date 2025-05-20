#include <QApplication>
#include "repo.h"
#include "service.h"
#include "gui.h"

int main(int argc, char *argv[]) {
    QApplication a(argc, argv);
    RepoCaz repo{"/Users/croitoruandreea/Desktop/simulare/cazuri.txt"};
    ServiceCaz service{repo};
    GUI gui{service};
    gui.show();
    return QApplication::exec();
}
