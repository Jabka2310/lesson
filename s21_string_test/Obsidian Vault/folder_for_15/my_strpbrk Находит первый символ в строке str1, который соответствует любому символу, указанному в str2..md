```C
char *my_strpbrk(const char *str1,

                 const char *str2) {  // Ищет первое вхождение символа из str2 в

                                      // str1, возвращает указатель на этот

                                      // символ или NULL если символ не найден

  char *result = NULL;

  

  while (*str1 != '\0' && result == NULL) {

    const char *p2 = str2;

    while (*p2 != '\0' && result == NULL) {

      if (*str1 == *p2) {

        result = (char *)str1;

      }

      p2++;

    }

    str1++;

  }

  

  return result;

}

```