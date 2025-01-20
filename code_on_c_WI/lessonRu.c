#include <stdio.h>
#include <locale.h> // ��� ������� setlocale

int main(void) { 
  setlocale(LC_ALL, ""); // ����� ������������ ������� �������
  
  printf("�������� ���������� ������� ������. ������ � ����� ������ ������� �����:\n");
  printf("  a. 1 �������� 1939 -- 2 �������� 1945\n");
  printf("  b. 1 �������� 1939 -- 9 ��� 1945\n");
  printf("  c. 22 ���� 1941 -- 9 ��� 1945\n");
  printf("  d. 22 ���� 1941 -- 2 �������� 1945\n");
  
  char answer; 
  printf("������� ��� ������� ������: ");
  scanf("%c",&answer);

  switch (answer) {
    case 'a': printf("GOOD!\n"); break;
    case 'b': 
    case 'c': 
    case 'd': printf("BAD!\n"); break;
    default:  printf("ERROR!\n"); break;
  }

  return 0;
}