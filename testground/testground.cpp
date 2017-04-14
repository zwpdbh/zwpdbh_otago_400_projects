//
// Created by zwpdbh on 15/04/2017.
//

#include <iostream>
#include "mylib.h"

using namespace std;

/**
 * A function that returns a position and an occurrence count
 */


int main(int argc, char **argv) {

    //
    string s = "When we call find_char, we have to pass three arguments";
    unsigned int ctr;
    auto index = find_char(s, 'o', ctr);

    cout << index << " : " << ctr <<  endl;


    //
    int j[] = {1, 2};
    print(begin(j), end(j));
    print(j, end(j) - begin(j));

    return 0;
}
