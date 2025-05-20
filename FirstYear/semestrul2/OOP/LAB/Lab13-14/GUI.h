#pragma once

#include <QLabel>
#include <QLineEdit>
#include <QTableWidget>
#include <QListWidget>
#include <QPushButton>
#include <QRadioButton>
#include <QGroupBox>

#include "service.h"
#include "myTable.h"
class ServiceGUI : public QWidget{
private:
    DisciplinaService &Service;

    QLabel* lblDenumire = new QLabel{"Denumire: "};
    QLabel* lblOre = new QLabel{"Numar ore/sapt.: "};
    QLabel* lblTip = new QLabel{"Tip: "};
    QLabel* lblProf = new QLabel{"Nume cadru didactic: "};

    QLineEdit* editDenumire ;
    QLineEdit* editOre;
    QLineEdit* editTip;
    QLineEdit* editProf;

    QPushButton* btnAddD = new QPushButton("Adauga");
    QPushButton* btnDeleteD = new QPushButton("Sterge");
    QPushButton* btnModifyD = new QPushButton("Modifica");

    QGroupBox *groupBox = new QGroupBox;
    QLabel* lblGroupBox = new QLabel("Criterii de sortare: ");
    QRadioButton *sortDenumire = new QRadioButton("Denumire");
    QRadioButton *sortOre = new QRadioButton("Numar ore/sapt.");
    QRadioButton *sortTipProf = new QRadioButton("Tip + Nume cadru didactic");
    QPushButton* btnSortD = new QPushButton("Sorteaza discipline");

    QLabel* lblFilter1 = new QLabel{ "Numele cadrului didactic dupa care se filtreaza: " };
    QLineEdit* editFilterProf;
    QPushButton* btnFilterProf = new QPushButton("Filtreaza");

    QLabel* lblFilter2 = new QLabel{ "Numarul de ore dupa care se filtreaza: " };
    QLineEdit* editFilterOre;
    QPushButton* btnFilterOre = new QPushButton("Filtreaza");

    QPushButton* btnUndo = new QPushButton("Undo");
    QPushButton* btnReloadData = new QPushButton("Reload data");

    TableModel* modelTable;
    QTableView* tblV = new QTableView;

   // QTableWidget* tableD = new QTableWidget(10, 4);

//pentru contract
    QPushButton* btnContract = new QPushButton("Contract");

    QLabel* lblExport = new QLabel{"Nume fisier:" };
    QLabel* lblRandom = new QLabel{"Numar discipline random: "};

    QLineEdit* editExport;
    QLineEdit* editRandom;
    QLineEdit* editDenumireC;

    QPushButton* btnAddC = new QPushButton("Adauga disciplina");
    QPushButton* btnGoleste = new QPushButton("Goleste");
    QPushButton* btnExport = new QPushButton("Exporta");
    QPushButton* btnRandom = new QPushButton("Random");

    QPushButton* btnLab = new QPushButton("Laboratoare");
    QPushButton* btnSem = new QPushButton("Seminarii");
    QPushButton* btnCurs = new QPushButton("Cursuri");

    QPushButton* btnCRUD = new QPushButton("Contract CRUD GUI");
    QPushButton* btnREAD = new QPushButton("Contract READ GUI");

    QListWidget* listC = new QListWidget;

    void initializareGUI();
    void connectSignalsSlots();
    void reloadDiscipline(vector<Disciplina> elems);
    void reloadContract(vector<Disciplina> elems);


public:
    ServiceGUI(DisciplinaService& service): Service{service}{
        initializareGUI();
        connectSignalsSlots();
        reloadDiscipline(service.getAll());
        reloadContract(service.getAllContract());
    }


};