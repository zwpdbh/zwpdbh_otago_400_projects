//
// Created by zwpdbh on 15/04/2017.
//

#ifndef OTAGO_PROJECTS_MYLIB_H
#define OTAGO_PROJECTS_MYLIB_H

#include <stddef.h>
#include <stdio.h>

extern void *emalloc(size_t);
extern void *remalloc(void *, size_t);
extern int getword(char *s, int limit, FILE *stream);
extern int primegt(int n);
extern unsigned int word_to_int(char *word);


#endif //OTAGO_PROJECTS_MYLIB_H
