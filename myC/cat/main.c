#include <stdio.h>
#include <stdlib.h>

void print_file(char* name);

int main(int argc, char** argv){
  
  for(int i = 1; i < argc; i++){
    print_file(argv[i]);
  }
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
  }
  fclose(f);
}