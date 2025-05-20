#include "gui.h"
#include <QHBoxLayout>
#include <QVBoxLayout>
#include <QFormLayout>

void GUI::initializareGUI() {
    Service.addElev(1, "Eric", "UBB", {"muzica"});
    Service.addElev(2, "aaa", "UBB", { "jurnalism", "muzica"});

    //main
    QHBoxLayout* lyMain = new QHBoxLayout;
    setLayout(lyMain);
    //dreapta tabel
    QWidget* RightBox = new QWidget;
    QVBoxLayout* lyRight = new QVBoxLayout;
    RightBox->setLayout(lyRight);

    lyRight->addWidget(tableAtelier);

    lyRight->addWidget(btnDesenPictura);
    lyRight->addWidget(btnFotografie);
    lyRight->addWidget(btnInformatica);
    lyRight->addWidget(btnJurnalism);
    lyRight->addWidget(btnMuzica);

    //stanga list
    QWidget* LeftBox = new QWidget;
    QVBoxLayout* lyLeft = new QVBoxLayout;
    LeftBox->setLayout(lyLeft);

    lyLeft->addWidget(listElevi);
    lyLeft->addWidget(btnSortare);

    QWidget* form = new QWidget;
    QFormLayout* lyform = new QFormLayout;
    form->setLayout(lyform);
    cluburi = new QLineEdit;
    lyform->addRow(lblClub, cluburi);

    lyLeft->addWidget(form);

    lyMain->addWidget(LeftBox);
    lyMain->addWidget(RightBox);
}

void GUI::conectare() {
    QObject::connect(btnSortare, &QPushButton::clicked, [&](){
        reloadList(Service.sortByName());
    });
    QObject::connect(btnMuzica, &QPushButton::clicked, [&](){
        reloadTable(Service.filterByClub("muzica"));
    });
    QObject::connect(btnInformatica, &QPushButton::clicked, [&](){
        reloadTable(Service.filterByClub("informatica"));
    });
    QObject::connect(btnDesenPictura, &QPushButton::clicked, [&](){
        reloadTable(Service.filterByClub("desen+pictura"));
    });
    QObject::connect(btnJurnalism, &QPushButton::clicked, [&](){
        reloadTable(Service.filterByClub("jurnalism"));
    });
    QObject::connect(btnFotografie, &QPushButton::clicked, [&](){
        reloadTable(Service.filterByClub("fotografie"));
    });

    QObject::connect(listElevi, &QListWidget::itemClicked, [this](QListWidgetItem* item){
        QVariant data = listElevi->currentItem()->data(Qt::UserRole);
        auto ateliere = data.value<vector<QString>>();

        QString content;
        for(auto const &atelier:ateliere){
            content+=atelier + " ";
        }

        cluburi->clear();
        cluburi->setText(content);
    });


}

void GUI::reloadList(vector<Elev> elevi) {
    listElevi->clear();
    for(const auto&elev : elevi){
        auto item = new QListWidgetItem(QString::fromStdString(elev.get_name() + " " + elev.get_scoala()));
        vector<QString> ateliere;
        for (const auto& atelier : elev.get_club()) {
            ateliere.push_back(QString::fromStdString(atelier));
        }

        item->setData(Qt::UserRole, QVariant::fromValue(ateliere));
        listElevi->addItem(item);
    }

}

void GUI::reloadTable(vector<Elev> elevi) {
    tableAtelier->clear();
    tableAtelier->setRowCount(elevi.size());
    int line = 0;
    for(auto const& elev : elevi){
        tableAtelier->setItem(line, 0, new QTableWidgetItem(QString::number(elev.get_nr_matricol())));
        tableAtelier->setItem(line, 1, new QTableWidgetItem(QString::fromStdString(elev.get_name())));
        tableAtelier->setItem(line, 2, new QTableWidgetItem(QString::fromStdString(elev.get_scoala())));
        line ++;
    }

}