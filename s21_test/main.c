#include <stdio.h>
#include "mystring.h"

int main(void){
  const char *test = "Hello, world!";
  printf("%zu\n", my_strlen(test));
  return 0;
}
