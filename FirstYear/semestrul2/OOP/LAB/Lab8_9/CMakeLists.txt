cmake_minimum_required(VERSION 3.27)
project(Lab8)

set(CMAKE_CXX_STANDARD 17)

add_executable(Lab8 main.cpp
        disciplina.cpp
        disciplina.h
        ui.h
        ui.cpp
        service.cpp
        service.h
        repo.cpp
        repo.h
        teste.cpp
        teste.h
        erori.h
        validator.cpp
        contract.h
        contract.cpp
        undo.h)
