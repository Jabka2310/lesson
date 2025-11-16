#include <stdio.h>

int main(void) {
  int n, day;
  scanf("%d%d", &n, &day);
  n -= day;
  n /= 7;
  printf("%d\n", n + 1);
  return 0;
}
