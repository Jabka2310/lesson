#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct flags {
    int i;
    int v;
    int c;
    int l;
    int n;
} flag;

int main(int argc, char** argv) {
    int count_file = 0;

    flag f = {0};
    int count_e = 0;
    for (int i = 1; i < argc; i++) {
        if (strcmp(argv[i], "-e") == 0) {
            count_e++;
        }
    }
    if (count_e == 0) {
        count_e = 1;
    }
    char pattern[count_e][30];
    int pattern_count = 0;
    int is_e = 0;
    if (count_e == 0) {
        strcpy(pattern[0], argv[1]);
        pattern_count++;
    } else {
        for (int i = 1; i < argc; i++) {
            if (!(strcmp(argv[i], "-e"))) {
                strcpy(pattern[pattern_count], argv[i + 1]);
                pattern_count++;
                is_e = 1;
            }
        }
    }

    int count_flag = 0;

    for (int i = 1; i < argc; i++) {
        if (argv[i][0] == '-' && argv[i][1] != 'e') {
            count_flag++;
            if (argv[i][1] == 'i') {
                f.i = 1;
            } else if (argv[i][1] == 'v') {
                f.v = 1;
            } else if (argv[i][1] == 'c') {
                f.c = 1;
            } else if (argv[i][1] == 'l') {
                f.l = 1;
            } else if (argv[i][1] == 'n') {
                f.n = 1;
            }
        }
    }

    if (is_e == 0) {
        count_file = argc - 2 - count_flag;
    } else {
        count_file = argc - count_e * 2 - count_flag;
    }

    // char *arr_file

    int count_cycle = 0;
    int max_len_file = 0;

    char arr_file[count_file][100];

    for (int i = 1; i < argc; i++) {
        if (is_e == 0) {
            if (i == 1) {
                i = 2;
            }
            if (argv[i][0] != '-') {
                strcpy(arr_file[count_cycle], argv[i]);
                count_cycle++;
            }
        } else {
            if (argv[i][0] == '-') {
                if (argv[i][1] == 'e') {
                    i++;
                }
            } else {
                strcpy(arr_file[count_cycle], argv[i]);
                count_cycle++;
            }
        }
    }

    // printf("%d\n", count_file);
    // for (int i = 0; i < count_file; i++) {
    //     printf("%s ", arr_file[i]);
    // }

    char buffer[1024];

    for (int i = 0; i < count_file; i++) {
        FILE* f = fopen(arr_file[i], "rb");
        while (fgets(buffer, 1024, f)) {
            printf("%s", buffer);
        }
        fclose(f);
    }

    return 0;
}
