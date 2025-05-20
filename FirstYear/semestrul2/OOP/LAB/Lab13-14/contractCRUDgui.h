#pragma once
#include "observer.h"
#include "contract.h"
#include <QWidget>
#include <QPushButton>
#include <QListWidget>
#include <QVBoxLayout>

class CRUD: public QWidget, public Observer{
private:
    Contract& cnt;

    QPushButton* btnGolire = new QPushButton("Golire");
    QPushButton* btnGenerare = new QPushButton("Generare random");

    QListWidget* lista = new QListWidget;

    void initCRUD(){
        QVBoxLayout* lymain = new QVBoxLayout;
        setLayout(lymain);
        lymain->addWidget(lista);
        lymain->addWidget(btnGenerare);
        lymain->addWidget(btnGolire);
    }

    void connectCRUD(){
        cnt.add_observer(this);
        QObject::connect(btnGolire, &QPushButton::clicked, [&](){
            cnt.stergeContract();
            reloadC(cnt.getAllContracte());
        });

        QObject::connect(btnGenerare, &QPushButton::clicked, [&](){
            cnt.adaugaDisciplinaRandom(cnt.getAllContracte(), 2);
            reloadC(cnt.getAllContracte());
        });
    }

    void reloadC(const vector<Disciplina>& elems){
        lista->clear();
        for(auto const el: cnt.getAllContracte()){
            auto item = new QListWidgetItem(QString::fromStdString(el.getName() + " " + std::to_string(el.getHours()) + " " + el.getType() + " " + el.getProf()));
            lista->addItem(item);
        }
    }

public:
    explicit CRUD(Contract& Cnt):cnt{Cnt}{
        initCRUD();
        connectCRUD();
        reloadC(Cnt.getAllContracte());
    }

    void update() override {
        reloadC(cnt.getAllContracte());
    }

    ~CRUD() {
        cnt.remove_observer(this);
    }
};