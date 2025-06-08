#include <stdio.h>
#include <string.h>

int main() {
  char *str1 = "bananaban";
  char *str2 = NULL;
  printf("%d\n", strcmp(str1, str2));
  return 0;
}