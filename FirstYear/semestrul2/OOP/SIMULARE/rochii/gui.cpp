#include "gui.h"
#include <QVBoxLayout>
#include <QStringList>
#include <QTextStream>
#include <QMessageBox>
void GUI::initializare() {

    QVBoxLayout* lymain = new QVBoxLayout;
    setLayout(lymain);

    lymain->addWidget(listRochii);
    lymain->addWidget(btnInchiriere);
    lymain->addWidget(btnSortMarime);
    lymain->addWidget(btnSortPret);
    lymain->addWidget(btnNesortat);

}

void GUI::conectare() {
    QObject::connect(btnSortMarime, &QPushButton::clicked, [&](){
        reloadList(Service.sortByMarime());
    });
    QObject::connect(btnSortPret, &QPushButton::clicked, [&](){
        reloadList(Service.sortByPret());
    });
    QObject::connect(btnNesortat, &QPushButton::clicked, [&](){
        reloadList(Service.getAll());
    });


//    QObject::connect(btnInchiriere, &QPushButton::clicked, [&](){
//
//            QVariant data = listRochii->currentItem()->data(Qt::UserRole);
//            if(data == 1)
//                item->setBackground(Qt::red);
//            else
//                QMessageBox::warning(this, "Warning", QString::fromStdString("Rochia a fost inchiriata deja"));
//
//
//        });


}

void GUI::reloadList(const vector<Rochie>& rochii) {
    listRochii->clear();
    for(auto const& rochie : rochii){
        auto item = new QListWidgetItem(QString::fromStdString(rochie.get_name()+" "+rochie.get_marime()+" "+std::to_string(rochie.get_pret())));
        if (rochie.get_disponibil()) {
            item->setBackground(Qt::green);
        } else {
            item->setBackground(Qt::red);
        }
        item->setData(Qt::UserRole, QVariant::fromValue(rochie.get_disponibil()));
        listRochii->addItem(item);
    }
}