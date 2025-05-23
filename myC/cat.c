#include <stdio.h>

void print_file(char* name);

int main(int argc, char **argv){
  
  for(int i = 1; i < argc; i++){
    print_file(argv[i]);
  }
  return 0;
}


void print_file(char* name){
    FILE *f = fopen(name, "r");

    if(f != NULL){
      int c = fgetc(f);
      while(c != EOF){
        fputc(c, f);
        c++;
      }
    }
  }

