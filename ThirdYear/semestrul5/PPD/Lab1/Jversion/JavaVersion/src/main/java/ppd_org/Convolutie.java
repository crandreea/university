package ppd_org;

import java.io.*;
import java.util.*;

public class Convolutie {

    static int N, M, n, m, p;
    static int[][] F;
    static int[][] C;
    static int[][] V;

    static void citireMatrici(String filePath) {
        try (Scanner sc = new Scanner(new File(filePath))) {
            N = sc.nextInt();
            M = sc.nextInt();

            F = new int[N][M];
            V = new int[N][M];

            for (int i = 0; i < N; i++)
                for (int j = 0; j < M; j++)
                    F[i][j] = sc.nextInt();

            n = sc.nextInt();
            m = sc.nextInt();

            C = new int[n][m];

            for (int i = 0; i < n; i++)
                for (int j = 0; j < m; j++)
                    C[i][j] = sc.nextInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void afisare(String filePath) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    pw.print(V[i][j] + " ");
                }
                pw.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    static void convolutieSecventiala() {
        int offset = n / 2;
        for (int i = offset; i < N - offset; i++) {
            for (int j = offset; j < M - offset; j++) {
                int s = 0;
                for (int u = 0; u < n; u++)
                    for (int v = 0; v < m; v++)
                        s += F[i - offset + u][j - offset + v] * C[u][v];
                V[i][j] = s;
            }
        }
    }

    static class WorkerLinCol implements Runnable {
        int start, end;
        boolean horizontal;

        WorkerLinCol(int start, int end, boolean horizontal) {
            this.start = start;
            this.end = end;
            this.horizontal = horizontal;
        }

        @Override
        public void run() {
            int offset = n / 2;
            if (horizontal) {
                for (int i = Math.max(start, offset); i < Math.min(end, N - offset); i++) {
                    for (int j = offset; j < M - offset; j++) {
                        int s = 0;
                        for (int u = 0; u < n; u++)
                            for (int v = 0; v < m; v++)
                                s += F[i - offset + u][j - offset + v] * C[u][v];
                        V[i][j] = s;
                    }
                }
            } else {
                for (int j = Math.max(start, offset); j < Math.min(end, M - offset); j++) {
                    for (int i = offset; i < N - offset; i++) {
                        int s = 0;
                        for (int u = 0; u < n; u++)
                            for (int v = 0; v < m; v++)
                                s += F[i - offset + u][j - offset + v] * C[u][v];
                        V[i][j] = s;
                    }
                }
            }
        }
    }


    static void convolutieParalelaLiniiColoane(boolean horizontal) {
        Thread[] threads = new Thread[p];
        int start = 0, end = 0, rest, baza;

        if (horizontal) {
            baza = N / p;
            rest = N % p;
        } else {
            baza = M / p;
            rest = M % p;
        }

        long startTime = System.nanoTime();

        for (int i = 0; i < p; i++) {
            end = start + baza + (rest > 0 ? 1 : 0);
            if (rest > 0) rest--;

            threads[i] = new Thread(new WorkerLinCol(start, end, horizontal));
            threads[i].start();
            start = end;
        }

        for (int i = 0; i < p; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long endTime = System.nanoTime();
        System.out.println(endTime - startTime);
    }

    static void mainSecvential() {
        citireMatrici("date5.txt");
        long startTime = System.nanoTime();

        convolutieSecventiala();

        long endTime = System.nanoTime();
        System.out.println("Timp secvențial: " + (endTime - startTime) / 1_000_000.0 + " ms");
        afisare("output_java_secvential.txt");
    }

    static void mainParalel(int nrThrd) {
        p = nrThrd;
        citireMatrici("date5.txt");
        convolutieParalelaLiniiColoane(true);
        afisare("output_java_paralel.txt");
    }

    public static void main(String[] args) {
        int threads = 1;
        if (args.length > 0)
            threads = Integer.parseInt(args[0]);

        mainSecvential();
        //mainParalel(threads);
    }
}
