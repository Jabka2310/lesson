```C
void *my_memchr(const void *str, int c, size_t n) {

  void *result = NULL;

  const unsigned char *p = (const unsigned char *)str;

  

  while (n-- > 0 && result == NULL) {

    if (*p == (unsigned char)c) {

      result = (void *)p;

    }

    p++;

  }

  

  return result;

}

```

