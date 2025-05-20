#pragma once
#include <QAbstractTableModel>
#include "disciplina.h"
#include <vector>

using namespace std;

class TableModel : public QAbstractTableModel {
    std::vector<Disciplina> elems;
public:
    TableModel(const std::vector<Disciplina>& elems) : elems{elems} {
    }

    int rowCount(const QModelIndex& parent = QModelIndex()) const override {
        return elems.size();
    }
    int columnCount(const QModelIndex& parent = QModelIndex()) const override {
        return 4;
    }

    QVariant data(const QModelIndex& index, int role = Qt::DisplayRole) const override {
        if (role == Qt::DisplayRole) {
            Disciplina p = elems[index.row()];
            if (index.column() == 0) {
                return QString::fromStdString(p.getName());
            }
            else if (index.column() == 1) {
                return QString::number(p.getHours());
            }
            else if (index.column() == 2) {
                return QString::fromStdString(p.getType());
            }
            else if (index.column() == 3) {
                return QString::fromStdString(p.getProf());
            }
        }

        return QVariant{};
    }

    void setOferte(const vector<Disciplina>& elems) {
        beginResetModel();
        this->elems = elems;
        endResetModel();
    }
};