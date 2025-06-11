#ifndef MYSTRING_H
#define MYSTRING_H

#include <stddef.h>  // Для типа size_t, потом заменю на s21_size_t
                     // Потом сделать макрос для NULL

size_t my_strlen(const char *str);
char *my_strncpy(char *dest, const char *src, size_t n);
int my_strncmp(const char *str1, const char *str2, size_t n);
void *my_memchr(const void *str, int c, size_t n);
int my_memcmp(const void *str1, const void *str2, size_t n);
void *my_memcpy(void *dest, const void *src, size_t n);
void *my_memset(void *str, int c, size_t n);
char *my_strncat(char *dest, const char *src, size_t n);
char *my_strchr(const char *str, int c);
#endif
