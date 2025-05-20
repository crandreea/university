#pragma once
#include "service.h"
#include <QWidget>
#include <QTableWidget>
#include <QLabel>
#include <QLineEdit>
#include <QGroupBox>
#include <QRadioButton>
#include <QPushButton>

class GUI:public QWidget{
private:
    ServiceCaz& Service;

    QTableWidget* table = new QTableWidget(10, 5);
    QLabel* lblId = new QLabel("Id: ");
    QLabel* lblBad = new QLabel("Versiune gresita: ");
    QLabel* lblGood = new QLabel("Versiunea buna: ");
    QLabel* lblTip = new QLabel("Tipul: ");
    QLabel* lblSeveritate = new QLabel("Severitatea: ");


    QLineEdit* editId;
    QLineEdit* editBad;
    QLineEdit* editGood;
    QLineEdit* editTip;
    QLineEdit* ediSeveritate;

    QPushButton* btnAdd = new QPushButton("Adauga");

    QRadioButton* sortBood = new QRadioButton("Sortare versiune corecta");
    QRadioButton* sortTip = new QRadioButton("Sortare tip");
    QRadioButton* sortSeveritate = new QRadioButton("Sortare severitate");


    void initializare();
    void conectare();
    void reload(vector<Caz> cazuri);
public:
    explicit GUI(ServiceCaz& service):Service{service}{
        initializare();
        conectare();
        reload(Service.sortBySeverity());
    }
};