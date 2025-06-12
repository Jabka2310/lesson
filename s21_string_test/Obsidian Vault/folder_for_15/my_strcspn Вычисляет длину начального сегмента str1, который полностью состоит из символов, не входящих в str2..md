```C
size_t my_strcspn(const char *str1, const char *str2) {

    size_t result = 0;

    const char *p1 = str1;

    while (*p1 != '\0' && my_strchr(str2, *p1) == NULL) {

        p1++;

        result++;

    }

    return result;

}

```