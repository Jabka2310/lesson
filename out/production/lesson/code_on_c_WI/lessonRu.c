#include <stdio.h>
#include <locale.h> // дл€ функции setlocale

int main(void) { 
  setlocale(LC_ALL, ""); // чтобы использовать русские символы
  
  printf("¬ыберите правильный вариант ответа. Ќачало и конец ¬торой мировой войны:\n");
  printf("  a. 1 сент€бр€ 1939 -- 2 сент€бр€ 1945\n");
  printf("  b. 1 сент€бр€ 1939 -- 9 ма€ 1945\n");
  printf("  c. 22 июн€ 1941 -- 9 ма€ 1945\n");
  printf("  d. 22 июн€ 1941 -- 2 сент€бр€ 1945\n");
  
  char answer; 
  printf("¬ведите ваш вариант ответа: ");
  scanf("%c",&answer);

  switch (answer) {
    case 'a': printf("GOOD!\n"); break;
    case 'b': 
    case 'c': 
    case 'd': printf("BAD!\n"); break;
    default:  printf("ERROR!\n"); break;
  }

  return 0;
}