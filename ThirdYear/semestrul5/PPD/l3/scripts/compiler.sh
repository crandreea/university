#!/bin/bash

BUILD_DIR="./cpp/cmake-build-debug"
mkdir -p "$BUILD_DIR"

mpicxx -std=c++20 -O2 -o "$BUILD_DIR/async" ./cpp/async.cpp
mpicxx -std=c++20 -O2 -o "$BUILD_DIR/scatter" ./cpp/scatter.cpp
mpicxx -std=c++20 -O2 -o "$BUILD_DIR/standard" ./cpp/standard.cpp
mpicxx -std=c++20 -O2 -o "$BUILD_DIR/standard_optimized" ./cpp/standard_optimized.cpp

g++ -std=c++20 -O2 -o "$BUILD_DIR/sequential" ./cpp/sequential.cpp
