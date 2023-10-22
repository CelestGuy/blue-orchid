cmake_minimum_required(VERSION 3.26)
project(blue_orchid)

set(CMAKE_CXX_STANDARD 17)

add_executable(blue_orchid
        src/main.cpp
        src/blueorchid.cpp
        src/ioblueorchid.cpp
)
