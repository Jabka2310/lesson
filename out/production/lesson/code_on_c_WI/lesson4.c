#include <stdio.h>

int main(void) {
    int n, a, b, c, d, res;
    scanf("%d", &n);
    a = n / 100;
    c = a / 10;
    d = a % 10;
    d = d * 10 + c;
    b = n % 100;
    res = d - b + 1;
    printf("%d", res);
    return 0;
}