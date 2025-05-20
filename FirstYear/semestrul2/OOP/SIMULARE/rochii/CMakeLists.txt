cmake_minimum_required(VERSION 3.27)
project(rochii)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_AUTOMOC ON)
set(CMAKE_AUTORCC ON)
set(CMAKE_AUTOUIC ON)

set(CMAKE_PREFIX_PATH "/Users/croitoruandreea/Qt/6.7.0/macos/lib/cmake")

find_package(Qt6 COMPONENTS
        Core
        Gui
        Widgets
        REQUIRED)

add_executable(rochii main.cpp
        test.cpp
        domain.h
        repo.h
        repo.cpp
        service.h
        service.cpp
        gui.cpp)
target_link_libraries(rochii
        Qt::Core
        Qt::Gui
        Qt::Widgets
)

