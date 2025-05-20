#pragma once
#include "service.h"
#include <QListWidget>
#include <QPushButton>
class GUI : public QWidget{
private:
    ServiceRochie& Service;

    QListWidget* listRochii = new QListWidget;
    QPushButton* btnInchiriere = new QPushButton("Inchiriere rochie");
    QPushButton* btnSortMarime = new QPushButton("Sortare Marime");
    QPushButton* btnSortPret = new QPushButton("Sortare Pret");
    QPushButton* btnNesortat= new QPushButton("Nesortat");

    void initializare();
    void conectare();
    void reloadList(const vector<Rochie>& rochii);
public:
    explicit GUI(ServiceRochie service): Service(service){
        initializare();
        conectare();
        reloadList(Service.getAll());
    }

};