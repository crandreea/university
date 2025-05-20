#pragma once
#include <QTableView>
#include "service.h"
class Model : public QAbstractTableModel{
    vector<Melodie> elems;
    ServiceMelodii& Service;
public:
    Model(const vector<Melodie> elems, ServiceMelodii& Service):elems{elems}, Service{Service}{
    }

    int rowCount(const QModelIndex& parent = QModelIndex()) const override {
        return elems.size();
    }
    int columnCount(const QModelIndex& parent = QModelIndex()) const override {
        return 5;
    }

    QVariant data(const QModelIndex& index, int role = Qt::DisplayRole) const override {
        if (role == Qt::DisplayRole) {
            Melodie p = elems[index.row()];
            if (index.column() == 0) {
                return QString::number(p.getId());
            }
            else if (index.column() == 1) {
                return QString::fromStdString(p.getDenumire());
            }
            else if (index.column() == 2) {
                return QString::fromStdString(p.getArtist());
            }
            else if (index.column() == 3) {
                return QString::number(p.getRank());
            }
            else if(index.column() == 4)
                return QString::number(Service.nrMelodiiRank(p.getRank()));
        }

        return QVariant{};
    }

    void setMelodii(const vector<Melodie>& elems) {
        beginResetModel();
        this->elems = elems;
        endResetModel();
    }


};