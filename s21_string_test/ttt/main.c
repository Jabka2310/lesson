#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// int main(void) {
//   int arr[10] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
//   char str[] = "Hello world!";
//   memset(str, 'H', 5);
//   printf("%s\n", str);
//   memset(arr, 0, 10);
//   for (int i = 0; i < 10; i++) {
//     printf("%d ", arr[i]);
//   }
//   printf("\n");
//   return 0;
// }

// int main() {
//   char *str = "Hello world!";
//   memset(str, 'H', 5);
//   printf("%s\n", str);
//   return 0;
// }

// int main(){
//   char str1[] = "Hello";
//   char str2[] = "World";
//   strncat(str1, str2, 5);
//   printf("%s\n", str1);
//   return 0;
// }

int main(void) {
  char str1[] = "Hello";
  char *str2 = NULL;
  printf("%s\n", strncat(str1, str2, 0));
  return 0;
}