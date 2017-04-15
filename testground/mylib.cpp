//
// Created by zwpdbh on 15/04/2017.
//

#include "mylib.h"
#include <iostream>

using namespace std;

// a function that returns a position and an occurrence count
string::size_type find_char(const string &s, char c, unsigned int &occurs) {
    auto ret = s.size();
    occurs = 0;

    for (unsigned int i = 0; i < s.size(); i++) {
        if (s[i] == c) {
            if (ret == s.size()) {
                ret = i;    // remember the first occurrence of c
            }
            occurs++;       // increment the occurrence count
        }
    }

    return ret;
}


// print the content of an int array
void print(const int *beg, const int *end) {
    while (beg != end) {
        cout << *beg++ << " ";
    }
}

void print(const int *arr, size_t size) {
    for (unsigned int i = 0; i < size; i++) {
        cout << arr[i] << " ";
    }
}

// return a REFERENCE to the shorter of two strings
const string &shorterString(const string &s1, const string &s2) {
    return s1.size() <= s2.size() ? s1 : s2;
}