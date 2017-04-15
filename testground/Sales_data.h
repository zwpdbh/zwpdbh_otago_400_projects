//
// Created by zwpdbh on 15/04/2017.
//

#ifndef OTAGO_PROJECTS_SALES_DATA_H
#define OTAGO_PROJECTS_SALES_DATA_H


#include <string>



class Sales_data {
    friend Sales_data add(const Sales_data&, const Sales_data&);

    friend std::ostream &print(std::ostream&, const Sales_data&);

    friend std::istream &read(std::istream&, Sales_data&);
private:
    double avg_price() const;
    std::string bookNo;
    unsigned int unit_sold = 0;
    double revenue = 0.0;

public:
    std::string isbn() const;
    Sales_data &combine (const Sales_data&);

    // constructors
    Sales_data() = default;

    // It is worth noting that both constructors have empty function bodies. The only work these constructors need to do is give the data members their values.
    // If there is no further work, then the function body is empty.
    Sales_data(const std::string &s): bookNo(s) {}

    // The constructor initializer is a list of member names, each of which is followed by
    // that member’s initial value in parentheses (or inside curly braces).
    // Multiple member initializations are separated by commas.

//    Sales_data(const std::string &s, unsigned n, double p):
//            bookNo(s), unit_sold(n), revenue(p*n) {}

    Sales_data(const std::string &s, unsigned n, double p);
    Sales_data(std::istream &);


};


#endif //OTAGO_PROJECTS_SALES_DATA_H
