cmake_minimum_required(VERSION 3.27)
project(Matrice_Lab7)

set(CMAKE_CXX_STANDARD 14)

include_directories(.)

add_executable(Matrice_Lab7
        App.cpp
        Matrice.cpp
        Matrice.h
        TestExtins.cpp
        TestExtins.h
        TestScurt.cpp
        TestScurt.h
        Iterator.h
        Iterator.cpp)
