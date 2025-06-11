```C

int my_strncmp(const char *str1, const char *str2, size_t n) {

  int result = 0;

  size_t i = 0;

  

  if (str1 == NULL || str2 == NULL) {

    result = -9999;  // Код ошибки для NULL указателей

  } else {

    while (i < n && *str1 != '\0' && *str2 != '\0' && result == 0) {

      result = *str1 - *str2;

      str1++;

      str2++;

      i++;

    }

  

    if (result == 0 && i < n) {

      result = *str1 - *str2;

    }

  }

  

  return result;

}
```

