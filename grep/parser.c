#include "grep.h"
#include <string.h>

  //argc - ко-во аргументов
  // argv[] - массив строк с аргументами 
  // cfg - указатель на строуктуру
  // filename - сохраняем имя файла 
int parse_args(int argc, char* argv[], GrepConfig *cfg, char **filename){ 
  
  int i; //счетчик цикла
  int pattern_found = 0;// флаг, который показывает нашли ли мы паттер

  for(i = 1; i < argc; i++){
    if(argv[i][0] == '-'){ // для коротких флагов
      char *p = argv[i] + 1 ;// если мы встретили - берем указатель на флаг(прибавляя 1 к адресу)
      while(*p){
        if(*p == 'i') cfg->ignore_case = 1;
        else if(*p == 'v') cfg->invert_match = 1;
        else if(*p == 'c') cfg->count_only = 1;
        else if(*p == 'n') cfg->line_numbers = 1;
        else{
          fprintf(stderr, "Неизвестная опция\n");
          return 0;
        }
        p++;
      }
    }else if(!pattern_found){
        //это паттерн, первый нефлаговый аргументов
        cfg->pattern = argv[i];
        pattern_found = 1;
      }else{
        // Передаем в переменную *filemake сам файл(имя файла)
        *filename = argv[i];
        break;
      }
    }
    if(!pattern_found){
      fprintf(stderr, "Не найден паттерн\n");
      return 0;
    }
    return 1;
  
}

// -i игнорирует регистр букв
// -v инвертирует вывод, показывает все строки кроме совпадающих
// -с выведет ток количество совпадений 
// -n покажет номер строки рядом с совпадением
// "hello" паттерн который мы будем искать 
// test.txt - имя файла в котором будет проходить поиск
