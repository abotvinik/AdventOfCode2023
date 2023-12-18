#include <stdio.h>
#include <stdlib.h>
#include <string.h>

char *wordNum[10] = {"zero","one","two","three","four","five","six","seven","eight","nine"};
char *reverseWordNum[10] = {"orez", "eno", "owt", "eerht", "ruof", "evif", "xis", "neves", "thgie", "enin"};
char *num[10] = {"0","1","2","3","4","5","6","7","8","9"};

char *strrev(char *str)
{
      char *p1, *p2;

      if (! str || ! *str)
            return str;
      for (p1 = str, p2 = str + strlen(str) - 1; p2 > p1; ++p1, --p2)
      {
            *p1 ^= *p2;
            *p2 ^= *p1;
            *p1 ^= *p2;
      }
      return str;
}

int main() {
    FILE* fp = fopen("Day1.txt","r");
    char buffer[256];
    size_t bufsize = 256;
    size_t characters;
    int total = 0;

    while(characters = fgets(buffer, 256, fp) != NULL) {
        int first = -1;
        int firstIndex = -1;
        int last = -1;
        int lastIndex = -1;
        for(int i = 0; i < 10; i++) {
            char *ptr;
            if((ptr = strstr(buffer, wordNum[i])) != NULL) {
                int index = ptr - buffer;
                if(first == -1) {
                    first = i;
                    firstIndex = index;
                }
                else if(index < firstIndex) {
                    first = i;
                    firstIndex = index;
                }
            }
            if((ptr = strstr(buffer, num[i])) != NULL) {
                int index = ptr - buffer;
                if(first == -1) {
                    first = i;
                    firstIndex = index;
                }
                else if(index < firstIndex) {
                    first = i;
                    firstIndex = index;
                }
            }
        }
        strrev(buffer);
        for(int i = 0; i < 10; i++) {
            char *ptr;
            if((ptr = strstr(buffer, reverseWordNum[i])) != NULL) {
                int index = ptr - buffer;
                if(last == -1) {
                    last = i;
                    lastIndex = index;
                }
                else if(index < lastIndex) {
                    last = i;
                    lastIndex = index;
                }
            }
            if((ptr = strstr(buffer, num[i])) != NULL) {
                int index = ptr - buffer;
                if(last == -1) {
                    last = i;
                    lastIndex = index;
                }
                else if(index < lastIndex) {
                    last = i;
                    lastIndex = index;
                }
            }
        }

        total = total + (first * 10 + last);
    }
    printf("%d\n",total);
    return(0);
}