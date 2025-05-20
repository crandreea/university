cmake_minimum_required(VERSION 3.27)
project(Colectie__cu_rel__Lab4)

set(CMAKE_CXX_STANDARD 14)

include_directories(.)

add_executable(Colectie__cu_rel__Lab4
        App.cpp
        Colectie.cpp
        Colectie.h
        IteratorColectie.cpp
        IteratorColectie.h
        TestExtins.cpp
        TestExtins.h
        TestScurt.cpp
        TestScurt.h)
