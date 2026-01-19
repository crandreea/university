cmake_minimum_required(VERSION 3.20)
project(l4)

set(CMAKE_CXX_STANDARD 20)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

find_package(MPI REQUIRED)
include_directories(${MPI_INCLUDE_PATH})

set(CMAKE_RUNTIME_OUTPUT_DIRECTORY ${CMAKE_BINARY_DIR})

add_executable(async async.cpp utils.h)
target_link_libraries(async ${MPI_LIBRARIES})

add_executable(scatter scatter.cpp utils.h)
target_link_libraries(scatter ${MPI_LIBRARIES})

add_executable(sequential sequential.cpp utils.h)
target_link_libraries(sequential ${MPI_LIBRARIES})

add_executable(standard standard.cpp utils.h)
target_link_libraries(standard ${MPI_LIBRARIES})
