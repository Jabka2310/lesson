#include <stdio.h>
#include <stdlib.h>


#include "mystring.h"

int main(void) {
  char *str1 = "Hello, world!";
  char *str2 = calloc(7, sizeof(char));
  printf("%s\n", strncpy(str2, str1, 5));
  free(str2);
  return 0;
}
