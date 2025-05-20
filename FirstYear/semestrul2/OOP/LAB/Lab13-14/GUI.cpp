#include "service.h"
#include "GUI.h"

#include <QHBoxLayout>
#include <QResizeEvent>
#include <QStringList>
#include <QFormLayout>
#include <QHeaderView>
#include <QMessageBox>
#include "contractCRUDgui.h"
#include "contractREADgui.h"
void ServiceGUI::initializareGUI() {

//main layout

    QHBoxLayout* lyMain = new QHBoxLayout;
    setLayout(lyMain);

//cadran stanga
    QWidget* leftBox = new QWidget;
    QVBoxLayout* lyLeftBox = new QVBoxLayout;
    leftBox->setLayout(lyLeftBox);
    QWidget* basics = new QWidget;
    QVBoxLayout* lyBasics = new QVBoxLayout;
    basics->setLayout(lyBasics);

//form
    QWidget* form = new QWidget;
    QFormLayout* lyForm = new QFormLayout;
    form->setLayout(lyForm);

    editDenumire = new QLineEdit;
    editOre = new QLineEdit;
    editTip = new QLineEdit;
    editProf = new QLineEdit;

    lyForm->addRow(lblDenumire, editDenumire);
    lyForm->addRow(lblOre, editOre);
    lyForm->addRow(lblTip, editTip);
    lyForm->addRow(lblProf, editProf);

    QWidget* buttons = new QWidget;
    QHBoxLayout* lyButton = new QHBoxLayout;
    buttons->setLayout(lyButton);
    lyButton->addWidget(btnAddD);
    lyButton->addWidget(btnDeleteD);
    lyButton->addWidget(btnModifyD);
    lyButton->addWidget(btnUndo);


    lyForm->setSpacing(15);
    lyBasics->addWidget(form);
    lyBasics->addWidget(buttons);
    lyLeftBox->addWidget(basics);


//groupbox sortare
    QWidget* sortare = new QWidget;
    QVBoxLayout* lySortare = new QVBoxLayout;
    sortare->setLayout(lySortare);

    lySortare->addWidget(lblGroupBox);

    QVBoxLayout *lyRadioBox = new QVBoxLayout;
    groupBox->setLayout(lyRadioBox);
    groupBox->setStyleSheet("background-color: #3B3B3B");
    lyRadioBox->addWidget(sortDenumire);
    lyRadioBox->addWidget(sortOre);
    lyRadioBox->addWidget(sortTipProf);
    lyRadioBox->setSpacing(15);

    lySortare->addWidget(groupBox);
    lySortare->addWidget(btnSortD);

    lyLeftBox->addWidget(sortare);

//form filtrari
    QWidget *filterForm = new QWidget;
    QFormLayout *lyFilterForm = new QFormLayout;
    filterForm->setLayout(lyFilterForm);

    editFilterProf = new QLineEdit;
    editFilterOre = new QLineEdit;


    lyFilterForm->addRow(lblFilter1, editFilterProf);
    QWidget* buttonFilter1 = new QWidget;
    QHBoxLayout* lyButtonFilter1 = new QHBoxLayout;
    buttonFilter1->setLayout(lyButtonFilter1);
    lyButtonFilter1->addWidget(btnFilterProf, 0, Qt::AlignCenter);
    lyFilterForm->addRow(buttonFilter1);

    lyFilterForm->addRow(lblFilter2, editFilterOre);
    QWidget* buttonFilter2 = new QWidget;
    QHBoxLayout* lyButtonFilter2 = new QHBoxLayout;
    buttonFilter2->setLayout(lyButtonFilter2);
    lyButtonFilter2->addWidget(btnFilterOre, 0, Qt::AlignCenter);
    lyFilterForm->addRow(buttonFilter2);


    lyLeftBox->addWidget(filterForm);
    lyLeftBox->addStretch();

    //lyLeftBox->addWidget(btnContract);
    QWidget* up = new QWidget;
    QFormLayout* lyUp = new QFormLayout;
    up->setLayout(lyUp);

    editDenumireC = new QLineEdit;
    editExport = new QLineEdit;
    editRandom = new QLineEdit;
    lyUp->addRow("Denumire disciplina: ", editDenumireC);
    lyUp->addWidget(btnAddC);
    lyUp->addRow(lblRandom, editRandom);
    lyUp->addWidget(btnRandom);
    lyUp->addRow(lblExport, editExport);
    lyUp->addWidget(btnExport);

    lyLeftBox->addWidget(up);
    lyLeftBox->addWidget(btnCRUD);
    lyLeftBox->addWidget(btnREAD);


//cadran dreapta
    QWidget* rightBox = new QWidget;
    QVBoxLayout* lyRightBox = new QVBoxLayout;
    rightBox->setLayout(lyRightBox);

    modelTable = new TableModel{Service.getAll()};
    tblV->setModel(modelTable);
    //QStringList header;
    //header << "Denumire" << "Nr. ore" <<  "Tip" <<  "Nume cadru didactic" ;
//    tblV->setHorizontalHeaderLabels(header);
//    tableD->horizontalHeader()->setSectionResizeMode(QHeaderView::ResizeToContents);
//    tableD->setMinimumWidth(288);

//    tableD->horizontalHeader()->setFont(font);



    lyRightBox->addWidget(tblV);
    lyRightBox->addWidget(btnReloadData);

    QFont font("Arial", 12);
    leftBox->setFont(font);
    rightBox->setFont(font);

    lyRightBox->addWidget(btnLab);
    lyRightBox->addWidget(btnSem);
    lyRightBox->addWidget(btnCurs);

    int cnt = 0;
    for (int i = 0; i < Service.getAll().size(); i++)
        if (Service.getAll()[i].getType() == "lab")
            cnt++;
    if (cnt == 0)
        btnLab->setVisible(false);

    cnt = 0;
    for (int i = 0; i < Service.getAll().size(); i++)
        if (Service.getAll()[i].getType() == "seminar")
            cnt++;
    if (cnt == 0)
        btnSem->setVisible(false);

    cnt = 0;
    for (int i = 0; i < Service.getAll().size(); i++)
        if (Service.getAll()[i].getType() == "curs")
            cnt++;
    if (cnt == 0)
        btnCurs->setVisible(false);



    lyMain->addWidget(leftBox);
    lyMain->addWidget(rightBox);


    //culori
    btnReloadData->setStyleSheet("background-color: #626262");
    btnContract->setStyleSheet("background-color: #626262");
    btnModifyD->setStyleSheet("background-color: #626262");
    btnAddD->setStyleSheet("background-color: #626262");
    btnDeleteD->setStyleSheet("background-color: #626262");
    btnUndo->setStyleSheet("background-color: #626262");
    btnFilterProf->setStyleSheet("background-color: #626262");
    btnFilterOre->setStyleSheet("background-color: #626262");
    btnSortD->setStyleSheet("background-color: #626262");

    rightBox->setStyleSheet("background-color: #494949");
    leftBox->setStyleSheet("background-color: #494949");

    //tableD->setStyleSheet("background-color: #626262");

}

void ServiceGUI::connectSignalsSlots() {
    QObject::connect(btnAddD, &QPushButton::clicked, [&](){
        try{
            string denumire = editDenumire->text().toStdString();
            int ore = editOre->text().toInt();
            string tip = editTip->text().toStdString();
            string prof = editProf->text().toStdString();

            editProf->clear();
            editTip->clear();
            editOre->clear();
            editDenumire->clear();

            Service.addDisciplina(denumire, ore, tip, prof);
            reloadDiscipline(Service.getAll());

            QMessageBox::information(this, "Info", QString::fromStdString("Disciplina adaugata cu succes!"));

        }
        catch (Exception& e){
            QMessageBox::warning(this, "Warning", QString::fromStdString(e.getMessage()));
        }
    });

    QObject::connect(btnDeleteD, &QPushButton::clicked, [&](){
        try{
            string denumire = editDenumire->text().toStdString();
            //int ore = editOre->text().toInt();
            string tip = editTip->text().toStdString();
            string prof = editProf->text().toStdString();

            editProf->clear();
            editTip->clear();
            editOre->clear();
            editDenumire->clear();

            Service.deleteDisciplina(denumire, tip, prof);
            reloadDiscipline(Service.getAll());

            QMessageBox::information(this, "Info", QString::fromStdString("Disciplina stearsa cu succes!"));

        }
        catch (Exception& e){
            QMessageBox::warning(this, "Warning", QString::fromStdString(e.getMessage()));
        }
    });

    QObject::connect(btnModifyD, &QPushButton::clicked, [&](){
        try{
            string denumire = editDenumire->text().toStdString();
            int ore = editOre->text().toInt();
            string tip = editTip->text().toStdString();
            string prof = editProf->text().toStdString();

            editProf->clear();
            editTip->clear();
            editOre->clear();
            editDenumire->clear();

            Service.modifyDisciplina(denumire, ore, tip, prof);
            reloadDiscipline(Service.getAll());

            QMessageBox::information(this, "Info", QString::fromStdString("Disciplina modificata cu succes!"));

        }
        catch (Exception& e){
            QMessageBox::warning(this, "Warning", QString::fromStdString(e.getMessage()));
        }
    });

    QObject::connect(btnSortD, &QPushButton::clicked, [&](){
        if(sortDenumire->isChecked())
            reloadDiscipline(Service.sortareDisciplineDenumire());
        else if(sortOre->isChecked())
            reloadDiscipline(Service.sortareDisciplineOre());
        else if(sortTipProf->isChecked())
            reloadDiscipline(Service.sortareDisciplineTipProf());
    });

    QObject::connect(btnFilterOre, &QPushButton::clicked, [&](){
        int crt = editFilterOre->text().toInt();
        editFilterOre->clear();
        reloadDiscipline(Service.filtrareH(crt));
    });

    QObject::connect(btnFilterProf, &QPushButton::clicked, [&](){
        string crt = editFilterProf->text().toStdString();
        editFilterProf->clear();
        reloadDiscipline(Service.filtrareProf(crt));
    });

    QObject::connect(btnUndo, &QPushButton::clicked, [&](){
        try{
            Service.undo();
            reloadDiscipline(Service.getAll());
            QMessageBox::information(this, "Info", QString::fromStdString("Undo realizat cu succes!"));
        }
        catch (Exception& e){
            QMessageBox::warning(this, "Warning", QString::fromStdString(e.getMessage()));
        }
    });

    QObject::connect(btnReloadData, &QPushButton::clicked, [&](){
        reloadDiscipline(Service.getAll());
    });

//    QObject::connect(btnContract, &QPushButton::clicked, [&](){
//        QWidget* fereastraC = new QWidget;
//        QVBoxLayout* lyContract = new QVBoxLayout;
//        fereastraC->setLayout(lyContract);
//
//        QWidget* up = new QWidget;
//        QFormLayout* lyUp = new QFormLayout;
//        up->setLayout(lyUp);
//
//        editDenumireC = new QLineEdit;
//        editExport = new QLineEdit;
//        editRandom = new QLineEdit;
//
//        lyUp->addRow("Denumire disciplina: ", editDenumireC);
//        lyUp->addWidget(btnAddC);
//        lyUp->addRow(lblRandom, editRandom);
//        lyUp->addWidget(btnRandom);
//        lyUp->addRow(lblExport, editExport);
//        lyUp->addWidget(btnExport);
//
//        lyContract->addWidget(up);
//        lyContract->addWidget(listC);
//        lyContract->addWidget(btnGoleste);
//
//        fereastraC->show();
//    });

    QObject::connect(btnCRUD, &QPushButton::clicked, [&](){
        auto fereastra1 = new CRUD{Service.getContract()};
        fereastra1->show();

    });

    QObject::connect(btnREAD, &QPushButton::clicked, [&](){
        auto fereastra2 = new Desen{Service.getContract()};
        fereastra2->show();
    });

    QObject::connect(btnAddC, &QPushButton::clicked, [&](){
        try{
            string denumire = editDenumireC->text().toStdString();
            editDenumireC->clear();
            Service.contractAdauga(denumire);
            reloadContract(Service.getAllContract());
        }
        catch(Exception& e){
            QMessageBox::warning(this, "Warning", QString::fromStdString(e.getMessage()));
        }
    });

    QObject::connect(btnRandom, &QPushButton::clicked, [&](){
        try{
            int random = editRandom->text().toInt();
            editRandom->clear();
            Service.contractAdaugaRandom(random);
            reloadContract(Service.getAllContract());

        }
        catch(Exception& e){
            QMessageBox::warning(this, "Warning", QString::fromStdString(e.getMessage()));
        }
    });

    QObject::connect(btnExport, &QPushButton::clicked, [&](){
        try{
            string fisier = editExport->text().toStdString();
            editExport->clear();
            Service.contractExport(fisier);
            listC->clear();
            Service.contractSterge();
            reloadContract(Service.getAllContract());

        }
        catch(Exception& e){
            QMessageBox::warning(this, "Warning", QString::fromStdString(e.getMessage()));
        }
    });

    QObject::connect(btnGoleste, &QPushButton::clicked, [&](){
        try{
            Service.contractSterge();
            reloadContract(Service.getAllContract());

        }
        catch(Exception& e){
            QMessageBox::warning(this, "Warning", QString::fromStdString(e.getMessage()));
        }
    });

    QObject::connect(btnLab, &QPushButton::clicked, [&](){
        int cnt = 0;
        for (int i = 0; i < Service.getAll().size(); i++)
            if (Service.getAll()[i].getType() == "lab")
                cnt++;
        QMessageBox::information(this, "Info", QString::fromStdString("Numar laboratoare: %1").arg(cnt));
    });

    QObject::connect(btnSem, &QPushButton::clicked, [&](){
        int cnt = 0;
        for (int i = 0; i < Service.getAll().size(); i++)
            if (Service.getAll()[i].getType() == "seminar")
                cnt++;
        QMessageBox::information(this, "Info", QString::fromStdString("Numar seminarii: %1").arg(cnt));
    });

    QObject::connect(btnCurs, &QPushButton::clicked, [&](){
        int cnt = 0;
        for (int i = 0; i < Service.getAll().size(); i++)
            if (Service.getAll()[i].getType() == "curs")
                cnt++;
        QMessageBox::information(this, "Info", QString::fromStdString("Numar cursuri: %1").arg(cnt));
    });

}
    void ServiceGUI::reloadDiscipline(vector<Disciplina> elems) {
        modelTable->setOferte(elems);
//    tableD->clearContents();
//    tableD->setRowCount(elems.size());
//
//    int line = 0;
//    for(auto& elem : elems){
//        tableD->setItem(line, 0, new QTableWidgetItem(QString::fromStdString(elem.getName())));
//        tableD->setItem(line, 1, new QTableWidgetItem(QString::number(elem.getHours())));
//        tableD->setItem(line, 2, new QTableWidgetItem(QString::fromStdString(elem.getType())));
//        tableD->setItem(line, 3, new QTableWidgetItem(QString::fromStdString(elem.getProf())));
//        line++;
//    }

        int cnt = 0;
        for (int i = 0; i < Service.getAll().size(); i++)
            if (Service.getAll()[i].getType() == "lab")
                cnt++;
        if (cnt == 0)
            btnLab->setVisible(false);
        else
            btnLab->setVisible(true);

        cnt = 0;
        for (int i = 0; i < Service.getAll().size(); i++)
            if (Service.getAll()[i].getType() == "seminar")
                cnt++;
        if (cnt == 0)
            btnSem->setVisible(false);
        else
            btnSem->setVisible(true);

        cnt = 0;
        for (int i = 0; i < Service.getAll().size(); i++)
            if (Service.getAll()[i].getType() == "curs")
                cnt++;
        if (cnt == 0)
            btnCurs->setVisible(false);
        else
            btnCurs->setVisible(true);
}

void ServiceGUI::reloadContract(vector<Disciplina> elems) {
    listC->clear();
    for(const auto& elem: elems){
        auto item = new QListWidgetItem(QString::fromStdString(elem.getName() + " " + std::to_string(elem.getHours()) + " " + elem.getType() + " " + elem.getProf()));
        listC->addItem(item);
    }
}