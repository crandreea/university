#pragma once
#include <QPainter>
#include <QWidget>
#include "observer.h"
#include "contract.h"

class Desen : public QWidget, public Observer{
private:
    Contract& cnt;
public:
    explicit Desen(Contract& Cnt):cnt{Cnt}{
        Cnt.add_observer(this);
    }

    void update() override{
        repaint();
    }

    void paintEvent(QPaintEvent* ev) override{
        QPainter p{this};
        srand(time(0));
        for(const auto el:cnt.getAllContracte()){
            int x = rand() % width();
            int y = rand() % height();
            int radius = 10 + rand() % 40;
            qDebug()<<x<<" "<<y<<"\n";
            p.drawEllipse(QPoint(x, y), radius, radius);
            // p.drawLine(x, y, width(), height());
        }
    }
};