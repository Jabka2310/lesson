```C
void *my_memcpy(void *dest, const void *src,

                size_t n) {  // Копирует n байт из src в dest, возвращает dest

  char *d = (char *)dest;

  const char *s = (const char *)src;

  

  while (n--) {

    *d++ = *s++;  // Порядок операций смотри ниже

  }

  //*d = *s;  Копирование байта из *s в *d

  // d++;     Увеличение указателя dest

  // s++;     Увеличение указателя src

  return dest;

}

```