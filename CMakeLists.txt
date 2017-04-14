cmake_minimum_required(VERSION 3.7)
project(Otago_Projects)

set(CMAKE_CXX_STANDARD 11)
set(SOURCE_FILES main.cpp)


# cosc431 search xml
add_executable(cosc431_searchXML cosc431_searchXML/main.cpp cosc431_searchXML/Vocabulary.cpp cosc431_searchXML/Vocabulary.h)
