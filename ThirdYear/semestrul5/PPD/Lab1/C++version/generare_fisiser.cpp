#include <stdio.h>
#include <stdlib.h>
#include <time.h>

#define MAXN 100000
#define MAXM 100000
#define MAXK 7

int main() {
    int N = 10000, M = 10000, n = 5, m = 5;
    FILE *f = fopen("date5.txt", "w");
    srand(time(NULL));

    fprintf(f, "%d", N);
    fprintf(f, " ");
    fprintf(f, "%d", M);
    fprintf(f, "\n");

    for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++)
            fprintf(f, "%d ", rand() % 10);
        fprintf(f, "\n");
    }

    fprintf(f, "%d", n);
    fprintf(f, " ");
    fprintf(f, "%d", m);
    fprintf(f, "\n");

    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++)
            fprintf(f, "%d ", rand() % 3 - 1);
        fprintf(f, "\n");
    }

    fclose(f);
    return 0;
}
