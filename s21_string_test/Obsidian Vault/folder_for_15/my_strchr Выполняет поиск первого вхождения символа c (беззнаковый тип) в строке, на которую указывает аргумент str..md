```C
char *my_strchr(const char *str, int c) {

  if (str == NULL) {

    return NULL;

  }

  

  while (*str != '\0') {

    if (*str == (char)c) {

      return (char *)str;

    }

    str++;

  }

  

  // Проверяем случай, когда c == '\0'

  if ((char)c == '\0') {

    return (char *)str;

  }

  

  return NULL;

}

```