```C
char *my_strtok(

    char *str,

    const char *delim) {  // Разбивает строку на токены по разделителям delim

  static char *last = NULL;

  char *token_start = NULL;

  

  if (str != NULL) {

    last = str;

  }

  

  if (last != NULL) {

    // Пропускаем начальные разделители

    while (*last != '\0' && my_strchr(delim, *last) != NULL) {

      last++;

    }

  

    if (*last != '\0') {

      token_start = last;

  

      // Ищем следующий разделитель

      while (*last != '\0' && my_strchr(delim, *last) == NULL) {

        last++;

      }

  

      if (*last != '\0') {

        *last = '\0';

        last++;

      } else {

        last = NULL;

      }

    } else {

      last = NULL;

    }

  }

  

  return token_start;

}

```