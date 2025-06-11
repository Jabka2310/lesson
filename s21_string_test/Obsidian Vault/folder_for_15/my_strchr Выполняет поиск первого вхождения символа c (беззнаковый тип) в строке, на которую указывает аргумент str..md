```C
char *my_strchr(const char *str, int c) {

    char *result = NULL;

    while (*str != '\0' && result == NULL) {

        if (*str == (unsigned char)c) {

            result = (char *)str;

        }

        str++;

    }

    if (result == NULL && (unsigned char)c == '\0') {

        result = (char *)str;

    }

    return result;

}

```