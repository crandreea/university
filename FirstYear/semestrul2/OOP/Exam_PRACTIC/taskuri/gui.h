#pragma once
#include "service.h"
#include <QWidget>
#include <QLabel>
#include <QLineEdit>
#include <QPushButton>
#include <QTableWidget>
#include <QListWidget>
#include <QHBoxLayout>
#include <QVBoxLayout>
#include <QHeaderView>
#include <QFormLayout>
#include <QMessageBox>

class GUI : public QWidget{
private:
    TaskService& Service;
    QLabel* lblId = new QLabel("Id: ");
    QLabel* lblDescriere = new QLabel("Descriere: ");
    QLabel* lblNames = new QLabel("Programatori: ");
    QLabel* lblStare = new QLabel("Stare: ");

    QLineEdit* editId= new QLineEdit;
    QLineEdit* editDescriere= new QLineEdit;
    QLineEdit* editProgramatori= new QLineEdit;
    QLineEdit* editStare= new QLineEdit;

    QPushButton* btnAdd = new QPushButton("Add");


    QLabel* lblNume = new QLabel("Nume programator: ");
    QLineEdit* editNume = new QLineEdit;


    QTableWidget* table = new QTableWidget(5, 4);



public:
    explicit GUI(TaskService& Service):Service{Service}{
        initializare();
        connect();
        reloadTable(Service.sortByStare());

    }

    void initializare();
    void connect();
    void reloadTable(vector<Task> elems);

};



class Stare:  public QWidget, public Observer{
private:
    TaskService& Service;
    string stare;

    QPushButton* btnOpen = new QPushButton("Open");
    QPushButton* btnInprogress = new QPushButton("Inprogress");
    QPushButton* btnClosed  = new QPushButton("Closed");

    QListWidget* lista = new QListWidget;

    void initCRUD(){
        QVBoxLayout* lymain = new QVBoxLayout();
        setLayout(lymain);
        lymain->addWidget(lista);
        lymain->addWidget(btnOpen);
        lymain->addWidget(btnInprogress);
        lymain->addWidget(btnClosed);

    }

    void connectCRUD(){
        Service.add_observer(this);

        QObject::connect(btnOpen, &QPushButton::clicked, [&](){
            if(lista->selectionModel()->selectedIndexes().isEmpty())
                return;
            auto row = lista->selectionModel()->selectedIndexes().at(0).row();
            auto id = lista->model()->data(lista->model()->index(row, 0), Qt::DisplayRole).toString();

            QMessageBox::warning(this, "Warning", id[0]);
//            Task t;
//            for(auto const& el : Service.getAll()){
//                if(el.getId() == id)
//                    t = el;
//            }
//            Service.modify(id, t.getDescriere(), t.getNames(),"open");
//            reloadList(Service.filterByStare(stare));
        });


    }

    void reloadList(vector<Task> elems){
        lista->clear();
        for(auto const& el : elems){
            vector<string> participants = el.getNames();
            string p;
            for(auto const& part : participants)
            {
                p += part;
                p += "";
            }
            auto item = new QListWidgetItem(QString::fromStdString(std::to_string(el.getId()) + " " + el.getDescriere() + " " + p + " " + el.getStare()));
            lista->addItem(item);
        }
    }

public:
    explicit Stare(TaskService& Service, string stare):Service{Service}, stare{stare}{
        initCRUD();
        connectCRUD();
        reloadList(Service.filterByStare(stare));
    }

    void update() override {
        reloadList(Service.getAll());
    }

    ~Stare() {
        Service.remove_observer(this);
    }

};