cmake_minimum_required(VERSION 3.27)
project(melodie1)

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

add_executable(melodie1 main.cpp
        domain.h
        repo.h
        service.h
        gui.h
        tests.h
        model.h)
target_link_libraries(melodie1
        Qt::Core
        Qt::Gui
        Qt::Widgets
)

