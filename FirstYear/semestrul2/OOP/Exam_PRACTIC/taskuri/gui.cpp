#include "gui.h"
#include <QHBoxLayout>
#include <QVBoxLayout>
#include <QHeaderView>
#include <QFormLayout>
#include <QMessageBox>

void GUI::initializare() {
    QHBoxLayout* lymain = new QHBoxLayout;
    setLayout(lymain);

    QWidget* form = new QWidget;
    QFormLayout* lyForm = new QFormLayout;
    form->setLayout(lyForm);
    lyForm->addRow(lblId, editId);
    lyForm->addRow(lblDescriere, editDescriere);
    lyForm->addRow(lblNames, editProgramatori);
    lyForm->addRow(lblStare, editStare);
    lyForm->addWidget(btnAdd);
    lyForm->addRow(lblNume, editNume);

    lymain->addWidget(form);

    QStringList header;
    header << "Id" << "Descriere" <<"Programatori" << "Stare";
    table->setHorizontalHeaderLabels(header);
    table->horizontalHeader()->setSectionResizeMode(QHeaderView::ResizeToContents);
    lymain->addWidget(table);

}

void GUI::connect() {
    QObject::connect(btnAdd, &QPushButton::clicked, [this](){
        try{
            int id = editId -> text().toInt();
            string descriere = editDescriere -> text().toStdString();
            string programatori = editProgramatori->text().toStdString();
            string stare = editStare->text().toStdString();

            editStare->clear();
            editProgramatori->clear();
            editId->clear();
            editDescriere->clear();

            Service.add(id, descriere, programatori, stare);
            reloadTable(Service.getAll());
        }
        catch (exception& e){
            QMessageBox::warning(this,"Warning", "Invalid arguments!");
        }

    });

    QObject::connect(editNume, &QLineEdit::textChanged,[this](){
        string name = editNume -> text().toStdString();
        if(name.empty())
            reloadTable(Service.getAll());
        else
            reloadTable(Service.filterByName(name));
    });


}

void GUI::reloadTable(vector<Task> elems) {
    table->clear();
    table->setRowCount(elems.size());
    int line = 0;
    for(auto const& el : elems){
        table ->setItem(line, 0, new QTableWidgetItem(QString::number(el.getId())));
        table ->setItem(line, 1, new QTableWidgetItem(QString::fromStdString(el.getDescriere())));
        table ->setItem(line, 2, new QTableWidgetItem(QString::number(el.getNames().size())));
        table ->setItem(line, 3, new QTableWidgetItem(QString::fromStdString(el.getStare())));
        line++;
    }

}

