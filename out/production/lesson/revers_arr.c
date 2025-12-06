#include <stdio.h>

// void print_arr(int* mass, int* effect);

void array_reverse(int* array, int size);
void array_reverse_ptr(int* array, int* limit);

int main(void) {
    int arr[3] = {0, 1, 2};
    int* razmer = arr + 3;
    array_reverse(arr, 3);
    for (int i = 0; i < 3; i++) {
        printf("%d ", arr[i]);
    }
    printf("\n");
    array_reverse_ptr(arr, razmer);
    for (int i = 0; i < 3; i++) {
        printf("%d ", arr[i]);
    }
    printf("\n");
    return 0;
}

void array_reverse(int* array, int size) {
    int array2[size];
    for (int i = 0; i < size; i++) {
        array2[i] = array[i];
    }
    for (int i = 0; i < size; i++) {
        array[i] = array2[size - 1 - i];
    }
}
void array_reverse_ptr(int* array, int* limit) {
    int* start = array;
    int* end = limit - 1;

    while (start < end) {
        // Обмен значений через временную переменную
        int temp = *start;
        *start = *end;
        *end = temp;

        // Перемещаем указатели к центру
        start++;
        end--;
    }
}
