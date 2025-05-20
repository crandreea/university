#pragma once
#include "service.h"
#include "vector_dinamic.h"
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
        static void afiseazaUI(const VectorDinamic<Disciplina>& elems);

    public:
        explicit UI(DisciplinaService& service): service{service}{
        }
        UI(const UI& ot) = delete;
        void run();
    };