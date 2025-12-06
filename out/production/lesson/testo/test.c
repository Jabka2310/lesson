#include <stddef.h>
#include <stdio.h>

size_t my_strlen(char *str);
char *my_strncpy(char *dest, char *str, size_t n);

int main(void) {
  char str[] = "Hello, world!";
  printf("Начальный указатель - %p\n", str);
  printf("Начальная строка - %s\n", str);
  printf("Тут возвращаем размер строки - %zu\n", my_strlen(str));
  printf("Конечный указатель - %p\n", str);
  printf("Конечная строка - %s\n", str);

  char dest[15];
  printf("%s\n", my_strncpy(dest, str, 13));

  return 0;
}

size_t my_strlen(char *str) {
  size_t lenght = 0;
  while (*str != '\0') {
    lenght++;
    str++;
  }
  return lenght;
}

char *my_strncpy(char *dest, char *str, size_t n){
  
  char *result = dest;
  size_t i = 0;
  
  while(i < n && *str != '\0'){
  *dest++ = *str++;
  i++;
  }

  while(i < n){
    *dest = '\0';
    dest++;
    i++;
  }
  return result;
}
