#include "mystring.h"

size_t my_strlen(const char *str) { // Считает длинну строки, возвращает ее в size_t
  size_t lenght = 0;
  while (*str != '\0') {
    lenght++;
    str++;
  }
  return lenght;
}

// char *my_strcpy(char *dest, const char *src) {
//   char *result = dest;
//   while (*src != '\0') {
//     *dest = *src;
//     dest++;
//     src++;
//   }
//   return result;
// }

// int my_strcmp(const char *str1, const char *str2) {
//   int result = 0;

//   if (str1 == NULL || str2 == NULL) {
//     result = -9999;  // Возвращаем -9999 если одна из строк NULL, мой код
//     ошибки
//   } else {
//     while (*str1 != '\0' && *str2 != '\0' && result == 0) {
//       result = *str1 - *str2;
//       str1++;
//       str2++;
//     }
//     if (result == 0) {
//       result = *str1 - *str2;
//     }
//   }

//   return result;
// }

char *my_strncpy(char *dest, const char *src, size_t n) { // Копирует строку src в dest, возвращает dest
  char *result = dest;
  size_t i = 0;

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

  return result;
}

int my_strncmp(const char *str1, const char *str2, size_t n) { // Сравнивает две строки, возвращает 0 если они равны, 1 если первая больше, -1 если вторая больше
  int result = 0;
  size_t i = 0;
  
  while (i < n && str1[i] != '\0' && str2[i] != '\0' && result == 0) {
    result = (unsigned char)str1[i] - (unsigned char)str2[i];
    i++;
  }
  
  if (result == 0 && i < n) {
    result = (unsigned char)str1[i] - (unsigned char)str2[i];
  }
  
  return result;
}

void *my_memchr(const void *str, int c, size_t n) { // Ищет первое вхождение символа c в строку str, возвращает указатель на этот символ или NULL если символ не найден
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

int my_memcmp(const void *str1, const void *str2, size_t n) {
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
