#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void print_file(char* name);
void flags_parser(char *flags, int argc, char **argv, int *index);

int main(int argc, char** argv){

  char flags[7] = "\0"; // Массив флагов
  int index_end_flag = 1;
  flags_parser(flags, argc, argv, &index_end_flag);
  return 0;
}

void print_file(char* name){
  
  FILE *f = NULL;
  f = fopen(name, "rd");
  if(f == NULL){
    fprintf(stderr, "Ошибка открытия файла\n");
    return;
  }else{
    int c;
    while((c  = fgetc(f)) != EOF){
      putc(c, stdout);
      c = fgetc(f);
    }
    fclose(f);
  }
}

void flags_parser(char *flags, int argc, char **argv, int *index){
  // Передаем массив флагов, количество аргументов, массив с массивами аргументов 
  // и указатель на индекс конца флага

  // Пройдемся по всем аргументам кроме 1
  for(int i = 1; i < argc, i++){
    // Если аргумент начинается не на "-"
    // или является строкой "-" или "--"("--" сигнал что опции кончились)
    // Считаем что флаги опций закончились и начались имена файлов
    if(argv[i][0] != '-' || strcmp(argv[i], "--") == 0 || strcmp(argv[i], "-")){
      break;
    }else{
      *index = i;
      // Флаги могут быть написаны слитно, например -bE 
      // поэтому проходимся по каждому символу аргумента, кроме 1
      for(size_t j = 1; j < strlen(argv[i]); j++){
        append_flags(flags, argv[i][j]);
      }
    }
  }
}
