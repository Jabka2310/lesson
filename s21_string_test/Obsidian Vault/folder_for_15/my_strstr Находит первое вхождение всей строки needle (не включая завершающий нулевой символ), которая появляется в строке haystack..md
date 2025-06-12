```C
char *my_strstr(const char *haystack, const char *needle) {

  char *result = NULL;

  

  if (*needle == '\0') {

    result = (char *)haystack;

  } else {

    while (*haystack != '\0' && result == NULL) {

      const char *p1 = haystack;

      const char *p2 = needle;

  

      while (*p1 != '\0' && *p2 != '\0' && *p1 == *p2) {

        p1++;

        p2++;

      }

  

      if (*p2 == '\0') {

        result = (char *)haystack;

      }

  

      haystack++;

    }

  }

  

  return result;

}

```