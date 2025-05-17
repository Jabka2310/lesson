#ifndef GREP_H
#define GREP_H

#include <stdio.h>
#include <regex.h>

typedef struct{
  regex_t regex; // Скомпилированный шаблон поиска
  const char *pattern; //Шаблон для поиска
  int ignore_case; //Игнор регистра
  int invert_match; //Показывает только несовпадающие строки
  int count_only; //ток счетчик совпадающих строк 
  int line_numbers; // показывает номер строки 
  int regex_compiled; // проверка на успешность компиляции регулярного выражения
} GrepConfig;

void init_config(GrepConfig* cfg);
void free_config(GrepConfig* cfg);
int compile_regex(GrepConfig* cfg, const char* pattern);
int is_match(const char* line, GrepConfig *cfg);
int parse_args(int argc, char* argv[], GrepConfig* cfg, char **filename);
void process_file(FILE *file, GrepConfig* cfg);

#endif

// Создали заголовочнай файл
// Создали стрктуру для хранения флагов
// Объявление функций
