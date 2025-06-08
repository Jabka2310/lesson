#include <stdio.h>

#include "mystring.h"

int main(void) {
  char *str1 = "bananaban";
  char *str2 = NULL;
  printf("%d\n", my_strcmp(str1, str2));
  return 0;
}
