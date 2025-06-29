```C
#include "../15functions/s21_string.h"

  

#include <stdlib.h> // Для malloc()

  
  

char s21_toupper(char c) {

if (c >= 'a' && c <= 'z') {

return c - ('a' - 'A');

}

return c;

}

  
  

char *s21_to_upper(const char *str) {

if (str == s21_NULL) {

return s21_NULL;

}

  

s21_size_t length = s21_strlen(str);

char *result = malloc(length + 1);

if (result == s21_NULL) {

return s21_NULL;

}

  

for (s21_size_t i = 0; i < length; i++) {

result[i] = s21_toupper(str[i]);

}

result[length] = '\0';

  

return result;

}
```