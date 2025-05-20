cmake_minimum_required(VERSION 3.27)
project(Lab2_4 C)

set(CMAKE_C_STANDARD 11)
set (CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -Wall -Wextra")
add_executable(Lab2_4 main.c
        service.c
        offer.h
        offer.c
        list.c
        list.h
        service.h
        tests.c)
