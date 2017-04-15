//
// Created by zwpdbh on 15/04/2017.
//

#include "Sales_data.h"
#include <string>
#include <iostream>

using namespace std;

/**
 * The add function takes two Sales_data objects and
 * returns a new Sales_data representing their sum:
 */
Sales_data add(const Sales_data &lhs, const Sales_data &rhs) {
    Sales_data sum = lhs;
    sum.combine(rhs);
    return sum;
}

/**
 * The read function reads data from the given stream into the given object
 */
istream &read(istream &is, Sales_data &item) {
    double price = 0;
    is >> item.bookNo >> item.unit_sold >> price;
    return is;
}

/**
 * The print function prints the contents of the given object on the given stream.
 */
ostream &print(ostream &os, const Sales_data &item) {
    os << item.isbn() << " " << item.unit_sold
       << " " << item.revenue << " " << item.avg_price();

    return os;
}

std::string Sales_data::isbn() const {
    return this->bookNo;
}

/**
 * a function to return "this" object
 * The combine function is intended to act like the compound assignment operator, +=.
 * The object on which this function is called represents the left-hand operand of
 * the assignment. The right-hand operand is passed as an explicit argument:
 */
Sales_data &Sales_data::combine(const Sales_data &rhs) {
    unit_sold += rhs.unit_sold;
    revenue += rhs.revenue;
    return *this;
}

double Sales_data::avg_price() const {
    if (unit_sold) {
        return revenue/unit_sold;
    } else {
        return 0;
    }
}

Sales_data::Sales_data(const std::string &s, unsigned n, double p) {
    this->bookNo = s;
    this->unit_sold = n;
    this->revenue = n*p;
}

/**
 * As with any other member function, when we define a constructor outside of the class body,
 * we must specify the class of which the con- structor is a member.
 * Thus, Sales_data::Sales_data says that we’re defining the Sales_data member named Sales_data.
 */
Sales_data::Sales_data(std::istream &is) {
    read(is, *this);
}
