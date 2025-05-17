// мы хотим искать строки, соответствующие паттерну 
// Для этого:
// Скомпилируем паттерн в регулярное выражение
// Проверим каждую строку из файла на соответствие этому выражение
// regex_t - хранит скомпилирование регулярное выражение 
// regcomp() - Компилирует регулярное выражение 
// regexec() - Проверяет совпала ли строчка с шаблоном

#include "grep.h"

#include <string.h>
#include <stdio.h>

int compile_regex(GrepConfig *cfg, const char *pattern){
  // cfg - указатель на конфигурацию где храним результат
  // pattern - строка, которую пользователь ввел как шаблоном
  int flags = REG_EXTENDED | REG_NOSUB; // REG_EXTENDED - используем расш. синтаксис рег. выражений
                                        //REG_NOSUB - не схраняем подстроки(мы их не используем?)
                                        // REG_ICASE - игнрировать регист, если указан флаг -i 
  if(cfg->ignore_case) flags |= REG_ICASE;
  
  int ret = regcomp(&cfg->regex, pattern, flags);
  cfg->regex_compiled = (ret == 0); // regcomp - пытаемся скомпилировать шаблон 
                                    // Если все прошло успешно ret == 0, то помечаем regex_compiled = 1
  if(ret != 0){
    char error_buf[100];
    regerror(ret, &cfg->regex, error_buf, sizeof(error_buf)); // Преобразует код ошибки в понятное выражение
    fprintf(stderr, "Ошибка регулярного выражения %s\n", error_buf); //error_buf - буфер для хранения сообщения об ошибке 
  }
  return ret;
}

int is_match(const char *line, GrepConfig *cfg){
  // Проверяем было ли скомпилировано регулярное выражение
  if(!cfg->regex_compiled) return 0;
  // Если не скомпилированно - не можем ничего проверять
  return regexc(&cfg->regex, line, 0, NULL, 0) == 0;
  // regexc() - проверяет совпадает ли line с регулярным выражением 
  // возвращает 0, если выражение найдено
}

// Пример использования регулярных выражений
// ./s21_grep -i "(стрелочка в верх)hello"test.txt 
//"(стрелочка в верх)hello" - ишем строки, которые начинаются с hello 
//-i - будет игнорировать регистр 
//функция compile_regex() - проевратит это в регулярное выражение, а is_match - будет проверять 
//каждую строку файла 
