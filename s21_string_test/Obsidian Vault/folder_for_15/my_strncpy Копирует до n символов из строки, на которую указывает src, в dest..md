```C

char *my_strncpy(char *dest, const char *src, size_t n) {

  char *result = NULL;

  size_t i = 0;

  if (dest != NULL && src != NULL) {

    result = dest;

    while (i < n && *src != '\0') {

      *dest = *src;

      dest++;

      src++;

      i++;

    }

    while (i < n) {

      *dest = '\0';

      dest++;

      i++;

    }

  }

  return result;

}
```