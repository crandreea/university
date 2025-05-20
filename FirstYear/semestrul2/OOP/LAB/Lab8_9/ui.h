#pragma once
#include "service.h"
#include "disciplina.h"

class UI{
    private:
        DisciplinaService& service;
        void adaugaUI();
        void modificaUI();
        void stergeUI();
        void cautareUI();
        void filtrareProfUI();
        void filtrareHUI();
        void sortareUI();
        static void afiseazaUI(const vector<Disciplina>& elems);

    public:
        explicit UI(DisciplinaService& service): service{service}{
        }
        UI(const UI& ot) = delete;
        void run();

        void frecventaUI();

        void contractAdaugaRandomUI();
        void contractAdaugaUI();
        void contractStergeUI();
        void contractExportUI();
        void MeniuContract();
};