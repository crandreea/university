cmake_minimum_required(VERSION 3.27)
project(SDA)

set(CMAKE_CXX_STANDARD 14)

include_directories("TAD Dictionar")

add_executable(SDA
        "TAD Dictionar/App.cpp"
        "TAD Dictionar/Dictionar.cpp"
        "TAD Dictionar/Dictionar.h"
        "TAD Dictionar/IteratorDictionar.cpp"
        "TAD Dictionar/IteratorDictionar.h"
        "TAD Dictionar/TesteExtins.cpp"
        "TAD Dictionar/TestScurt.cpp")
