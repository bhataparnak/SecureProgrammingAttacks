#include <stdio.h>

int  main() {
  char* shell = (char *)getenv("MYSHELL");
  if(shell)
   printf("0x%x %s\n", (unsigned int)shell, shell);

  return 0;
}
