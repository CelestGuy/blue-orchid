cmake_minimum_required(VERSION 3.26)
project(blue_orchid)

set(CMAKE_CXX_STANDARD 17)

add_library(blue_orchid
        src/blueorchid.cpp
        src/ioblueorchid.cpp
)
