#include <stdio.h>

#include "mystring.h"

int main() {
  int str[10] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
  my_memset(str, 0, 10);
  for (int i = 0; i < 10; i++) {
    printf("%d ", str[i]);
  }
  return 0;
}
