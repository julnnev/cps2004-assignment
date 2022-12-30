cmake_minimum_required(VERSION 3.20)
project(task2)

set(CMAKE_CXX_STANDARD 20)

add_executable(task2 Launcher.cpp Board.cpp Board.h Game.cpp Game.h)

if(NOT EXISTS ${PROJECT_BINARY_DIR}/.gitignore)
    file(WRITE ${PROJECT_BINARY_DIR}/.gitignore "*")
endif()
