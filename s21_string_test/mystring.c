#include "mystring.h"

size_t my_strlen(
    const char *str) {  // Считает длинну строки, возвращает ее в size_t
  size_t lenght = 0;
  while (*str != '\0') {
    lenght++;
    str++;
  }
  return lenght;
}

char *my_strncpy(char *dest, const char *src,
                 size_t n) {  // Копирует строку src в dest, возвращает dest
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

int my_strncmp(
    const char *str1, const char *str2,
    size_t n) {  // Сравнивает две строки, возвращает 0 если они равны, 1 если
                 // первая больше, -1 если вторая больше
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

void *my_memchr(
    const void *str, int c,
    size_t n) {  // Ищет первое вхождение символа c в строку str, возвращает
                 // указатель на этот символ или NULL если символ не найден
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

int my_memcmp(
    const void *str1, const void *str2,
    size_t n) {  // Сравнивает две области памяти, возвращает 0 если они равны,
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

void *my_memcpy(void *dest, const void *src,
                size_t n) {  // Копирует n байт из src в dest, возвращает dest
  char *d = (char *)dest;
  const char *s = (const char *)src;

  while (n--) {
    *d++ = *s++;  // Порядок операций смотри ниже
  }
  //*d = *s;  Копирование байта из *s в *d
  // d++;     Увеличение указателя dest
  // s++;     Увеличение указателя src
  return dest;
}

void *my_memset(void *str, int c,
                size_t n) {  // Заполняет память символом c, возвращает str
  char *p = (char *)str;
  while (n--) {
    *p++ = (char)c;  // ВАЖНО!!!!
    // Не работает с *str, работает только с str[]
    // Так как первое хранится в другой области памяти, а не в стеке
    //  Так же если использовать тип int, то будет ошибка, так как int занимает
    //  4 байта, а char 1 байт Помимо прочего, если задать 10 байт, и задать их
    //  нулями - то зачиться 3 целых байта вместо 2.5
  }
  return str;
}

char *my_strncat(char *dest, const char *src,
                 size_t n) {  // Конкатенирует две строки, возвращает dest
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

char *my_strchr(const char *str, int c) { // Ищет первое вхождение символа c в строку str, возвращает указатель на этот символ или NULL если символ не найден
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

