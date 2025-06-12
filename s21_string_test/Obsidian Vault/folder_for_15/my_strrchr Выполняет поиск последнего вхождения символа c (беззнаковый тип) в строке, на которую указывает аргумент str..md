```C
char *my_strrchr(

    const char *str,

    int c) {  // Ищет последнее вхождение символа c в строку str, возвращает

              // указатель на этот символ или NULL если символ не найден

  const char *last = NULL;

  

  while (*str != '\0') {

    if (*str == (unsigned char)c) {

      last = str;

    }

    str++;

  }

  

  if ((unsigned char)c == '\0') {

    last = str;

  }

  

  return (char *)last;

}

```