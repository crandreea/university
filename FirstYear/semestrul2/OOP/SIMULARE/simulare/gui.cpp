#include "gui.h"
#include <QHBoxLayout>
#include <QVBoxLayout>
#include <QFormLayout>
#include <QMessageBox>


void GUI::initializare() {
    QHBoxLayout* lyMain = new QHBoxLayout;
    setLayout(lyMain);

    QWidget* form = new QWidget;
    QFormLayout* lyform = new QFormLayout;
    form->setLayout(lyform);

    ediSeveritate = new QLineEdit;
    editTip = new QLineEdit;
    editGood = new QLineEdit;
    editBad = new QLineEdit;
    editId = new QLineEdit;

    lyform->addRow(lblId, editId);
    lyform->addRow(lblBad, editBad);
    lyform->addRow(lblGood, editGood);
    lyform->addRow(lblTip, editTip);
    lyform->addRow(lblSeveritate, ediSeveritate);

    lyform->addWidget(btnAdd);

    lyform->addWidget(sortBood);
    lyform->addWidget(sortTip);
    lyform->addWidget(sortSeveritate);

    lyMain->addWidget(form);

    QWidget* dreapta = new QWidget;
    QVBoxLayout* lydr = new QVBoxLayout;
    dreapta->setLayout(lydr);
    lydr->addWidget(table);

    lyMain->addWidget(dreapta);

}

void GUI::conectare() {
    QObject::connect(btnAdd, &QPushButton::clicked, [&](){
        try{
            int id = editId->text().toInt();
            string bad = editBad->text().toStdString();
            string good = editGood->text().toStdString();
            string tip = editTip->text().toStdString();
            string severitate = ediSeveritate->text().toStdString();

            ediSeveritate->clear();
            editTip->clear();
            editGood->clear();
            editBad->clear();
            editId->clear();

            Service.add(id, bad, good, tip, severitate);
            reload(Service.getAll());

        }
        catch(Exception& e){
            QMessageBox::warning(this, "Warning", QString::fromStdString(e.get_msg()));
        }
    });

    QObject::connect(sortSeveritate, &QRadioButton::clicked, [&](){
        try{
            reload(Service.sortBySeverity());
        }
        catch(Exception& e){
            QMessageBox::warning(this, "Warning", QString::fromStdString(e.get_msg()));
        }

    });
    QObject::connect(sortTip, &QRadioButton::clicked, [&](){
        try{
            reload(Service.sortByTip());
        }
        catch(Exception& e){
            QMessageBox::warning(this, "Warning", QString::fromStdString(e.get_msg()));
        }

    });
    QObject::connect(sortBood, &QRadioButton::clicked, [&](){
        try{
            reload(Service.sortByGood());
        }
        catch(Exception& e){
            QMessageBox::warning(this, "Warning", QString::fromStdString(e.get_msg()));
        }

    });


}

void GUI::reload(const vector<Caz> elems) {
    table->clearContents();
    table->setRowCount(elems.size());

    int line = 0;
    for(auto& elem : elems){
        table->setItem(line, 0, new QTableWidgetItem(QString::number(elem.get_id())));
        table->setItem(line, 1, new QTableWidgetItem(QString::fromStdString(elem.get_bad())));
        table->setItem(line, 2, new QTableWidgetItem(QString::fromStdString(elem.get_good())));
        auto item = new QTableWidgetItem(QString::fromStdString(elem.get_tip()));
        if(elem.get_tip() == "typo")
            item->setBackground(Qt::black);
        else if(elem.get_tip() == "grammar")
            item->setBackground(Qt::red);
        else if(elem.get_tip() == "incorrect tense")
            item->setBackground(Qt::blue);
        table->setItem(line, 3, item);

        auto item2 = new QTableWidgetItem(QString::fromStdString(elem.get_severity()));
        if(elem.get_severity() == "minor")
            item2->setBackground(Qt::green);
        else if(elem.get_tip() == "medium")
            item2->setBackground(Qt::yellow);
        else
            item2->setBackground(Qt::red);
        table->setItem(line, 4, item2);
        line++;
    }
}