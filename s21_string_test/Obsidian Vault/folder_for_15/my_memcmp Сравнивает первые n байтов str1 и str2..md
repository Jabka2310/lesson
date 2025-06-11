
```C
int my_memcmp(

    const void *str1, const void *str2,

    size_t n) {  // Сравнивает две области памяти, возвращает 0 если они равны,

                 // 1 если первая больше, -1 если вторая больше

  int result = 0;

  const unsigned char *p1 = (const unsigned char *)str1;

  const unsigned char *p2 = (const unsigned char *)str2;

  

  while (n-- > 0 && result == 0) {

    result = *p1 - *p2;

    p1++;

    p2++;

  }

  

  return result;

}

```