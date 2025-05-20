
#pragma once
#include <QWidget>
#include <QLabel>
#include <QListWidget>
#include <QTableWidget>
#include <QPushButton>
#include <QLineEdit>
#include "service.h"

class GUI : public QWidget{
private:
    ServiceElev& Service;

    QListWidget* listElevi = new QListWidget;
    QLabel* lblClub = new QLabel("Cluburi: ");
    QLineEdit* cluburi;

    QPushButton* btnSortare = new QPushButton("Sortare Nume");

    QPushButton* btnDesenPictura = new QPushButton("Desen+Pictura");
    QPushButton* btnFotografie = new QPushButton("Fotografie");
    QPushButton* btnMuzica = new QPushButton("Muzica");
    QPushButton* btnInformatica = new QPushButton("Informatica");
    QPushButton* btnJurnalism = new QPushButton("Jurnalism");

    QTableWidget* tableAtelier = new QTableWidget(10, 3);

    void initializareGUI();
    void conectare();
    void reloadList(vector<Elev> elevi);
    void reloadTable(vector<Elev> elevi);

public:
    GUI(ServiceElev& service) : Service{service}{
        initializareGUI();
        conectare();
        reloadList(service.getAll());
        //reloadTable(service.getAll());
    }
};