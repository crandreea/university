#pragma once
#include "service.h"
#include <QWidget>
#include <QFormLayout>
#include <QHBoxLayout>
#include <QVBoxLayout>
#include <QPushButton>
#include <QListWidget>
#include <QTableWidget>
#include <QLineEdit>
#include <QLabel>
#include <QMessageBox>
#include <QPainter>
#include "model.h"

class GUI: public QWidget{
private:
    ServiceMelodii& Service;

    QLabel* text = new QLabel("Titlu: ");
    QLineEdit* editTitlu = new QLineEdit;

    QSlider* slider = new QSlider;

    QPushButton* btnDelete = new QPushButton("Delete");
    QPushButton* btnModify = new QPushButton("Update");

    Model* modelTable = new Model{Service.getAll(), Service};;
    QTableView* tblV = new QTableView;

public:
    GUI(ServiceMelodii& Service):Service{Service}{
        initializare();
        conectare();
        reloadTable(Service.getAll());
    }

    void initializare(){
        QVBoxLayout* lyMain = new QVBoxLayout;
        setLayout(lyMain);

        tblV->setModel(modelTable);
        lyMain->addWidget(tblV);

        lyMain->addWidget(text);
        lyMain->addWidget(editTitlu);
        slider->setMinimum(0);
        slider->setMaximum(10);
        lyMain->addWidget(slider);
        lyMain->addWidget(btnModify);

        lyMain->addWidget(btnDelete);
    }

    void conectare(){

        QObject::connect(tblV->selectionModel(), &QItemSelectionModel::selectionChanged, [this] {
            if(tblV->selectionModel()->selectedIndexes().isEmpty()) {
                editTitlu->setText("");
                return;
            } else {
                auto row = tblV->selectionModel()->selectedIndexes().at(0).row();
                auto titlu = tblV->model()->data(tblV->model()->index(row, 1), Qt::DisplayRole).toString();
                editTitlu->setText(titlu);
            }
        });

        QObject::connect(btnModify, &QPushButton::clicked, [this]{
            if(tblV->selectionModel()->selectedIndexes().isEmpty())
                return ;
            auto row = tblV->selectionModel()->selectedIndexes().at(0).row();
            auto id = tblV->model()->data(tblV->model()->index(row, 0), Qt::DisplayRole).toInt();
            auto titlu_nou = editTitlu->text().toStdString();
            auto rank_now = slider->value();
            try{
                Service.modifica(id, titlu_nou, rank_now);
                reloadTable(Service.getAll());
            }catch (exception& e){
                QMessageBox::warning(this, "Warning", "Eroare");
            }

        });

        QObject::connect(btnDelete, &QPushButton::clicked, [this]{
            if(tblV->selectionModel()->selectedIndexes().isEmpty())
                return ;
            auto row = tblV->selectionModel()->selectedIndexes().at(0).row();
            auto id = tblV->model()->data(tblV->model()->index(row, 0), Qt::DisplayRole).toInt();
            try{
                Service.sterge(id);
                reloadTable(Service.getAll());
            }catch(exception& e){
                QMessageBox::warning(this, "Warning", "Eroare");
            }

        });


    }
    void reloadTable(vector<Melodie> elems){
        modelTable->setMelodii(elems);
    }

    void paintEvent(QPaintEvent* ev) override
    {
        QPainter p{this};
        p.setPen(Qt::magenta);
        int rank = 0;
        for(auto &c : Service.rankSort()) {
            int x = (rank + 1) * 10;
            p.drawLine(x, p.device()->height(), x, p.device()->height() - c * 5);
            rank++;
        }
    }

};