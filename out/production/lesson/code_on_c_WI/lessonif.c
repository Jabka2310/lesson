#include <stdio.h>
#include <locale.h>

int main() {
  setlocale(LC_ALL, "");
  
  int a, b, c;
  scanf("%d%d%d", &a, &b, &c);
  if (a <= b && b <= c){
    printf("%d %d %d\n", a, b, c);
  }else{
    if (a <= c && c <= b){
      printf("%d %d %d\n", a, c, b);
    }else{
      if (b <= a && a <= c){
        printf("%d %d %d\n", b, a, c);
      }else{
        if (b <= c && c <= a){
          printf("%d %d %d\n", b, c, a);
        }else{
          if (c <= a && a <= b){
            printf("%d %d %d\n", c, a, b);
          }else{
            if (c <= b && b <= a){
              printf("%d %d %d\n", c, b, a);
            }
          }
        }
      }
    }
  }

  return 0;
}

/*#include <stdio.h>

int main(void) {
  int a = 0, k = 0;
  int res = 0;

  scanf("%d %d", &a, &k);
  res = 1;
//пока степень больше нуля
  while(k > 0){
// если степень чётная, то
    if (k%2 == 0){   
      res = res*res; // умножаем текущий результат на себя
      k = k/2;  // степень делим пополам
    }else { //если степень нечётная
      res = res * a; // то умножаем на исходное число
      k = k - 1;   // от степени вычитаем единицу
   }
 }
  printf("%d\n", res);

  return 0;
}*/