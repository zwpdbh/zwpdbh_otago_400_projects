cmake_minimum_required(VERSION 3.7)
project(Otago_Projects)

set(CMAKE_CXX_STANDARD 11)
set(SOURCE_FILES main.cpp)


# cosc431 search xml
add_executable(cosc431_searchXML cosc431_searchXML/main.cpp cosc431_searchXML/Dictionary.cpp cosc431_searchXML/Dictionary.h cosc431_searchXML/TermEntry.cpp cosc431_searchXML/TermEntry.h cosc431_searchXML/Postings.cpp cosc431_searchXML/Postings.h cosc431_searchXML/mylib.cpp cosc431_searchXML/mylib.h)

# testground
add_executable(testground testground/testground.cpp testground/mylib.cpp testground/mylib.h testground/Sales_data.cpp testground/Sales_data.h)