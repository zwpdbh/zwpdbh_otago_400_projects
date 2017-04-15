//
// Created by zwpdbh on 15/04/2017.
//

#ifndef OTAGO_PROJECTS_MYLIB_H
#define OTAGO_PROJECTS_MYLIB_H


#include <string>

std::string::size_type find_char(const std::string &, char, unsigned int &);

void print(const int *beg, const int *end);
void print(const int *, size_t);

const std::string &shorterString(const std::string, const std::string);
#endif //OTAGO_PROJECTS_MYLIB_H
