#include <stdlib.h> // Для malloc()
#include "mystring.h"

// Самописный аналог toupper() (только для ASCII)
char s21_toupper(char c) {
    if (c >= 'a' && c <= 'z') {
        return c - ('a' - 'A');
    }
    return c;
}

// Основная функция
char *s21_to_upper(const char *str) {
    if (str == S21_NULL) {
        return S21_NULL;
    }

    s21_size_t length = s21_strlen(str);
    char *result = malloc(length + 1); // +1 для '\0'
    if (result == S21_NULL) {
        return S21_NULL;
    }

    for (s21_size_t i = 0; i < length; i++) {
        result[i] = s21_toupper(str[i]);
    }
    result[length] = '\0';

    return result;
}