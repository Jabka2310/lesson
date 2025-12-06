/*#include <stdio.h>

int main(void){
  int N, n, a = 0;
  scanf("%d", &N);
  for(int i = 1; i <= N; i++){
    scanf("%d", &n);
    if(a == n){
      continue;
    }else{
      printf("%d ", n);
    }
    a = n;
  }
  return 0;
}*/

/*#include <stdio.h>

int main(void)
{
  int n = 0, b = -9999, flag = 1;
  while(n != -9999)
  {
    scanf("%d", &n);
    switch(n)
    {
      case -9999:break;
      default:if(n <= b){
        flag = 0;
      }
    }b = n;
    }
(flag == 1? printf("YES"):printf("NO"));      
  return 0;
}*/

/*#include <stdio.h>



  int main(void){
  int n = 0, b = -9999, flag = 1, count = 0;
  while(n != -9999)
  {
    scanf("%d", &n);
    count ++;
    switch(n)
    {
      case -9999:break;
      default:if(n <= b){
        flag = 0;
        printf("%d ", count);
        return 0;
      }
    }b = n;
    }
if(flag == 1){
  printf("%d", 0);
}
  return 0;
}*/

/*#include <stdio.h>

  int main(void){
  int n = 1;
  do{
    scanf("%d", &n);
  }while(n != 2517);
  while(n != -9999){
    scanf("%d", &n);
    switch(n){
      case -9999:break;
      default:
      printf("%d ", n);
    }
  }
  return 0;
}*/

/*#include <stdio.h>

  int main(void){
  int n = 1, count = 0;
  do{
    scanf("%d", &n);
  }while(n != 0);
  n = 1;
  while(n != 0){
    scanf("%d", &n);
    switch(n){
      case 0:break;
      default:
      count += n;
    }
  }
  printf("%d", count);
  return 0;
}*/

/*#include <stdio.h>   

  int main(void){
  for(int i = 1; i<= 100; i++){
    if((i % 3 == 0) && (i % 5 == 0)){
      printf("FizzBuzz ");
    }else{
      if(i % 3 == 0){
        printf("Fizz ");
      }else{
        if(i % 5 == 0){
          printf("Buzz ");
        }else{
          printf("%d ", i);
        }
      }
    }
  }
  return 0;
}*/

/*#include <stdio.h>

int main(void){
  int N = 0, n = 0;
  scanf("%d", &N);
  int revers[N];
  for(int i = 0; i <= (N - 1); i++){
    scanf("%d", &n);
    revers[i] = n;
  }for(int j = (N - 1); j >= 0; j--){
    printf("%d ", revers[j]);
  }
  return 0;
}*/

/*#include <stdio.h>

int main(void){
  int N = 0, n = 1, flag = 1;
  scanf("%d", &N);
  int dovod[N], revers[N];
  for(int i = 0; i <= N - 1; i ++){
    scanf("%d", &n);
    dovod[i] = n;
  }
  for(int j = 0; j <= N - 1; j ++){
    if(dovod[j] != dovod[(N - 1) - j]){
      flag = 0;
    }
  }(flag == 1? printf("YES\n"):printf("NO\n"));
  return 0; 
}*/

/*#include <stdio.h>

int main(void){
  int N = 0, n = 0;
  scanf("%d", &N);
  int massiv[N];
  for(int i = 0; i <= N - 1; i++){
    scanf("%d", &n);
    massiv[i] = n;
  }for(int j = 0; j <= N - 1; j ++){
    if(massiv[j] % 2 == 0){
      printf("%d ", massiv[j]);
    }
  }for(int j = 0; j <= N - 1; j ++){
    if(massiv[j] % 2 == 1){
      printf("%d ", massiv[j]);
    }
  }
  return 0;
}*/

/*#include <stdio.h>

int main(void){
  int N = 0, n = 0, cnt = 0;
  scanf("%d", &N);
  int massive[N];
  for(int i = 0; i <= N - 1; i++){
    scanf("%d", &n);
    massive[i] = n;
  }for(int i = 0; i <= N - 1; i++){
    if(massive[i] < massive[N - 1]){
      printf("%d ", massive[i]);
      cnt ++;
    }
  }if(cnt == 0){
    printf("%d", 0);
  }
  return 0;
}*/

/*#include <stdio.h>

int main(void){
  int N = 0, n = 0, cnt = 0;
  scanf("%d", &N);
  int massive[N];
  for(int i = 0; i <= N - 1; i++){
    scanf("%d", &n);
    massive[i] = n;
  }for(int i = 0; i <= N - 1; i++){
    if((massive[i] < massive[N - 1]) && (massive[i] > massive[0])){
      printf("%d ", massive[i]);
      cnt ++;
    }
  }if(cnt == 0){
    printf("%d", 0);
  }
  return 0;
}*/

/*#include <stdio.h>

int main(void){
  int N = 0, count = 0;
  double avg = 0;
  scanf("%d", &N);
  int massive[N];
  for(int i = 0; i < N; i++){
    scanf("%d", &massive[i]);
    count += massive[i];
  }avg = (double)count / N;
  printf("%.2lf", avg);
  return 0;
}*/

/*#include <stdio.h>

int main(void){
  int N = 0, count = 0;
  double avg = 0;
  scanf("%d", &N);
  int massive[N], massive2[N];
  for(int i = 0; i < N; i++){
    scanf("%d", &massive[i]);
    count += massive[i];
  }avg = (double)count / N;
  for(int i = 0; i < N; i++){
    if(massive[i] > avg){
      printf("%d ", massive[i]);
    }
  }for(int i = 0; i < N; i++){
    if(massive[i] <= avg){
      printf("%d ", massive[i]);
    }
  }
  return 0;
}*/

/*#include <stdio.h>

int main(void){
  int N = 0, count = 0, seredina = 0;
  scanf("%d", &N);
  seredina = N / 2;
  int massive[N];
  for(int i = 0; i < N; i++){
    scanf("%d", &massive[i]);
  } for(int i = 0; i < N; i++){
    if(i >= seredina){
      printf("%d ",massive[i]);
    } 
    }for(int i = 0; i < N; i++){
    if(i < seredina){
      printf("%d ",massive[i]);
    }
    }
  return 0;
}*/

/*#include <stdio.h>

int main(void){
  int N = 0, min = 9999, max = -9999, index_min = 0, index_max = 0; 
  scanf("%d", &N);
  int massive[N];
  for(int i = 0; i < N; i++){
    scanf("%d", &massive[i]);
  }for(int i = 0; i < N; i++){
    if(massive[i] <= min){
      min = massive[i];
      index_min = i;
    }if(massive[i] >= max){
      max = massive[i];
      index_max = i;
    }
  }
  for(int i = 0; i < N; i++){
    if(massive[i] == min){
      massive[i] = max;
    }else{
      if(massive[i] == max){
      massive[i] = min;
    }
    }
  }for(int i = 0; i < N; i++){
    printf("%d ", massive[i]);
  }
  return 0;
}*/

//Двумерные массивы

/*#include <stdio.h>

int main(void){
  int N, M;
  scanf("%d %d", &N, &M);
  int massive[N][M];
  for(int i = 0; i < N; i++){
    for(int j = 0; j < M; j++){
      scanf("%d", &massive[i][j]);
    }
  }
  for(int i = 0; i < M; i++){
    for(int j = 0; j < N; j++){
      printf("%d ", massive[j][i]);
    }printf("\n");
  }
  return 0;
}*/

/*include <stdio.h>

int main(void){
  int N, k = 1;
  scanf("%d", &N);
  int massive[N][N], size = N;
  for(int i = 0; i < N; i++){
    for(int j = 0; j < N; j++){
      if(i % 2 == 0){
        massive[i][j] = k;
        k ++;
      }else{
        massive[i][j] = size;
        size --;
      }
    }
    k = 1;
    size = N;
  }
  for(int i = 0; i < N; i++){
    for(int j = 0; j < N; j++){
      printf("%d ", massive[i][j]);
    }printf("\n");
  }

  return 0;
}*/

/*#include <stdio.h>

int main(void){
  int N, osnova = 0, pobochka = 0;
  scanf("%d", &N);
  int massive[N][N];
  for(int i = 0; i < N; i++){
    for(int j = 0; j < N; j++){
      scanf("%d", &massive[i][j]);
      if(i < j){
        osnova += massive[i][j];
      }
      if(i + j >= N){
          pobochka += massive[i][j]; 
        }
    }
  }
  if(osnova <= pobochka){
    printf("%d %d", osnova, pobochka);
  }else{
    printf("%d %d", pobochka, osnova);
  }
  return 0;
}*/

/*#include <stdio.h>

int main(void){
  int N, M, sum[10] = {0};
  scanf("%d %d", &N, &M);
  int massive[N][M], count[M];
  for(int j = 0; j < M; j ++){
    for(int i = 0; i < N; i++){
      scanf("%d", &massive[i][j]);
      sum[j] += massive[i][j];
    }
  }
  for(int i = 0; i < M; i ++){
    printf("%d ", sum[i]);
  }
  return 0;
}*/


/*#include <stdio.h>

int main(void){
  int N = 0, M = 0, sum = 0;
  scanf("%d %d", &N, &M);
  int massive[N][M], massive1[M][N], massive_sum[10];
  for(int i = 0; i < N; i ++){
    for(int j = 0; j < M; j++){
      scanf("%d", &massive[i][j]);
    }
  }
  for(int i = 0; i < M; i ++){
    for(int j = 0; j < N; j++){
      massive1[i][j] = massive[j][i];
    }
  }
  for(int i = M - 1; i >=  0; i --){
    for(int j = N - 1; j >= 0; j --){
      //printf("%d ", massive1[i][j]);
      sum += massive1[i][j];
    }
    //printf("\n");
    printf("%d ", sum);
    sum = 0;
  }

  return 0;
}*/

/*#include <stdio.h> // ШО ЗА ПИЗДЕЦ, НЕНАВИЖУ матрицы

int main(void){
  int N = 0, M = 0, massive_sum[10] = {0}, min = 9999, max = -9999, count_max = 0, count_min = 0;
  scanf("%d %d", &N, &M);
  int massive[N][M], massive1[M][N];//Создание стандартной матрицы
  for(int i = 0; i < N; i ++){
    for(int j = 0; j < M; j++){
      scanf("%d", &massive[i][j]);
    }
  }
  for(int i = 0; i < M; i ++){//Создание инвертированной матрицы
    for(int j = 0; j < N; j++){
      massive1[i][j] = massive[j][i];
    }
  }
  for(int i = 0; i < M; i++){// Запись суммы в одинарный массив
    for(int j = 0; j < N; j++){
      massive_sum[i] += massive1[i][j];
    }
  }
  for(int i = 0; i < M; i++){
    if(massive_sum[i] > max){
      max = massive_sum[i];
      count_max = i;
    }
    if(massive_sum[i] < min){
        min = massive_sum[i];
        count_min = i;
    } 
  }printf("\n");
  for(int i = 0; i < N; i++){
    for(int j = 0; j < M; j++){
      if(j == count_min){
        printf("%d ", massive[i][count_max]);
      }else{
      if(j == count_max){
        printf("%d ", massive[i][count_min]);
      }else{
        printf("%d ", massive[i][j]);
      }
      }
    }
    printf("\n");
  }
  return 0;
}*/

/* #include <stdio.h>

int main(void){
  int N, k;
  scanf("%d", &N);
  int massive[N][N];
  for(int i = 0; i < N; i++){
    for(int j = 0; j < N; j++){
      scanf("%d", &massive[i][j]);
    }
  }
  scanf("%d", &k);
  for(int i = 0; i < k; i++){
    for(int j = N -1; j >= 0; j--){
      int tmp = massive[j][N - 1];
      for(int c = N - 1; c >= 0; c--){
        massive[j][c] = massive[j][c - 1];
      }
      massive[j][0] = tmp;
    }
  }
  for(int i = 0; i < N; i++){
    for(int j = 0; j < N; j++){
      printf("%d ", massive[i][j]);
    }
    printf("\n");
  }
  return 0;
}*/

/*#include <stdio.h> ХУИТА ХУЕТ _ АЛЬФА МАТРИЦА
#include <stdlib.h>

int main()
{
    int N, M;

    scanf("%d%d", &N, &M);
    int **a = (int **)malloc(N * sizeof(int *));
    for (int i = 0; i < N; ++i)
    {
        a[i] = (int *)malloc(M * sizeof(int));
    }

    int iStart = 0, iBord = 0, jStart = 0, jBord = 0;

    int k = 1;
    int i = 0;
    int j = 0;

    while (k <= N * M)
    {
        a[i][j] = k;
        if (i == iStart && j < M - jBord - 1)
            ++j;
        else if (j == M - jBord - 1 && i < N - iBord - 1)
            ++i;
        else if (i == N - iBord - 1 && j > jStart)
            --j;
        else
            --i;

        if ((i == iStart + 1) && (j == jStart) && (jStart != M - jBord - 1))
        {
            ++iStart;
            ++iBord;
            ++jStart;
            ++jBord;
        }
        ++k;
    }

    for (int i = 0; i < N; ++i)
    {
        for (int j = 0; j < M; ++j)
        {
            printf("%3d", a[i][j]);
        }
        printf("\n");
    }

    for (int i = 0; i < N; ++i)
    {
        if (a[i])
        {
            free((int *)a[i]);
        }
    }
    if (a)
    {
        free(a);
    }

    return 0;
}*/

#include <stdio.h>

int main (void){
  char str[101] = {0};
  fgets(str, 101, stdin);
  fflush(stdin);
  int i = 0, slova = 0, state, OUT = 0, IN = 1;
  state = OUT;
  while(str[i] != '\n'){
    if(str[i] != ' ' && state == OUT){
      state = IN;
      ++slova;
    }else{
      if(str[i] == ' '){
        state = OUT;
      }
    }
    i ++;
  }
  if(i - 1 == ' '){
    --slova;
  }
  printf("%d", slova);
  return 0;
}