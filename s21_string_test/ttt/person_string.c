#include <stddef.h>


size_t my_strlen(char *str){
  size_t lenght = 0;
  while(*str != '\0'){
    lenght++;
    str++;
  }
  return lenght;
}
