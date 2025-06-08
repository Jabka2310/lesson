#ifndef MYSTRING_H
#define MYSTRING_H

#include <stddef.h>  // Для типа size_t

size_t my_strlen(const char *str);
char *my_strcpy(char *dest, const char *src);
int my_strcmp(const char *str1, const char *str2);
void *my_memchr(const void *str, int c, size_t n);

#endif
