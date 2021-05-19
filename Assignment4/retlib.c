#include <stdlib.h>
#include <stdio.h>
#include <string.h>

int bof(FILE *badfile)
{
char buffer[150];

fread(buffer, sizeof(char), 300, badfile);
return 1;
}
int main(int argc, char **argv)
{
FILE *badfile;

badfile = fopen("badfile", "r");
bof(badfile);

printf("Returned Properly\n");
fclose(badfile);

return 1;
}
