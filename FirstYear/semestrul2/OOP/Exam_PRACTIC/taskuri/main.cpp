#include <QApplication>
#include <QPushButton>
#include "repo.h"
#include "service.h"
#include "gui.h"



int main(int argc, char *argv[]) {
    QApplication a(argc, argv);
    TaskRepo repo{"/Users/croitoruandreea/Desktop/ANUL1/sem2/OOP/Exam_PRACTIC/taskuri/taskuri.txt"};
    TaskService service{repo};
    GUI gui{service};
    Stare stare1{service, "open"};
    stare1.show();
    Stare stare2{service, "inprogress"};
    stare2.show();
    Stare stare3{service, "closed"};
    stare3.show();
    gui.show();
    return QApplication::exec();
}
