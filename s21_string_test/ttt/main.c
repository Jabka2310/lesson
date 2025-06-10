#include <stdio.h>
#include <string.h>
#include <stdlib.h>

int main(void){
  char *arr = "ello, woHrld!";
  printf("%p\n", memchr(arr, 'H', 6));
 return 0;
}
