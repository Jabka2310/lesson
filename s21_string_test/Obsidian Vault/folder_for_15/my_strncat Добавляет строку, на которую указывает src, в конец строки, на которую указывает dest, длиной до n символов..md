```C
char *my_strncat(char *dest, const char *src, size_t n) {

    char *d = dest;

    // Доходим до конца строки

    while (*d != '\0') {

        d++;

    }

    // Копируем не более n символов

    while (n > 0 && *src != '\0') {

        *d++ = *src++;

        n--;

    }

    // Добавляем завершающий нуль

    *d = '\0';

    return dest;

}
```