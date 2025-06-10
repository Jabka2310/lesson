#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(void) {
  int arr1[] = {1, 2, 3, 4, 5};
  int arr2[3];
  printf("%p\n", memcpy(arr2, arr1, 6));
  return 0;
}
