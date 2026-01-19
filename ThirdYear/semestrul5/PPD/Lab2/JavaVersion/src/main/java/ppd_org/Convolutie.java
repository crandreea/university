package ppd_org;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Convolutie {

    static int N, M, n, m, p;
    static int[][] F;
    static int[][] C;

    static void citireMatrici(String filePath) {
        try (Scanner sc = new Scanner(new File(filePath))) {
            N = sc.nextInt();
            M = sc.nextInt();

            F = new int[N][M];

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
                    pw.print(F[i][j] + " ");
                }
                pw.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static int convolutie_pe_linie(int[] valori_linie, int coloana, int linie_kernel) {
        int left = Math.max(coloana - 1, 0);
        int right = Math.min(M - 1, coloana + 1);
        return valori_linie[left] * C[linie_kernel][0] + valori_linie[coloana] * C[linie_kernel][1] + valori_linie[right] * C[linie_kernel][2];
    }

    static void convolutieSecventiala() {
        int[] prevLinie = new int[M];
        int[] currLinie = new int[M];
        int[] auxLinie = new int[M];


        for (int j = 0; j < M; ++j) {
            prevLinie[j] = F[0][j];
            currLinie[j] = F[0][j];
        }

        long t_start = System.nanoTime();

        for (int i = 0; i < N; i++) {
            int[] nextRow = F[Math.min(N - 1, i + 1)];
            for (int j = 0; j < M; j++) {
                int output = 0;
                output = convolutie_pe_linie(prevLinie, j, 0) + convolutie_pe_linie(currLinie, j, 1)
                        + convolutie_pe_linie(nextRow, j, 2);
                auxLinie[j] = output;
            }

            if (M >= 0) System.arraycopy(auxLinie, 0, F[i], 0, M);

            System.arraycopy(currLinie, 0, prevLinie, 0, M);
            System.arraycopy(nextRow, 0, currLinie, 0, M);
        }

        long t_end = System.nanoTime();
        double elapsed_ms = (t_end - t_start) / 1_000_000.0 ;
        System.out.println(elapsed_ms);
    }

    static class ConvolutieLinii implements Runnable {
        private final CyclicBarrier barrier;
        private final int start;
        private final int end;


        ConvolutieLinii(CyclicBarrier barrier, int start, int end) {
            this.barrier = barrier;
            this.start = start;
            this.end = end;
        }


        @Override
        public void run() {
            int[] frontieraUp = new int[M];
            int[] frontieraDown = new int[M];

            if (start > 0) {
                System.arraycopy(F[start - 1], 0, frontieraUp, 0, M);
            } else {
                System.arraycopy(F[0], 0, frontieraUp, 0, M);
            }

            if (end < N) {
                System.arraycopy(F[end], 0, frontieraDown, 0, M);
            } else {
                System.arraycopy(F[N - 1], 0, frontieraDown, 0, M);
            }

            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                Thread.currentThread().interrupt();
                return;
            }

            if (start >= end) {
                return;
            }

            int[] prevLinie = new int[M];
            int[] currLinie = new int[M];
            int[] auxLinie = new int[M];

            System.arraycopy(frontieraUp, 0, prevLinie, 0, M);
            System.arraycopy(F[start], 0, currLinie, 0, M);

            for (int i = start; i < end; i++) {
                int[] nextRow;
                if (i + 1 < end) {
                    nextRow = F[i + 1];
                } else {
                    nextRow = frontieraDown;
                }

                for (int j = 0; j < M; j++) {
                    int output = convolutie_pe_linie(prevLinie, j, 0)
                            + convolutie_pe_linie(currLinie, j, 1)
                            + convolutie_pe_linie(nextRow, j, 2);
                    auxLinie[j] = output;
                }

                System.arraycopy(auxLinie, 0, F[i], 0, M);

                System.arraycopy(currLinie, 0, prevLinie, 0, M);
                System.arraycopy(nextRow, 0, currLinie, 0, M);
            }
        }


    }


    static void convolutie_paralela_linii() throws InterruptedException {
        Thread[] threads = new Thread[p];
        CyclicBarrier barrier = new CyclicBarrier(p);

        int start = 0;
        int baza = N / p;
        int rest = N % p;

        long t_start = System.nanoTime();

        for (int i = 0; i < p; i++) {
            int add = (rest > 0) ? 1 : 0;
            int end = start + baza + add;
            if (rest > 0) rest--;

            ConvolutieLinii worker = new ConvolutieLinii(barrier, start, end);
            threads[i] = new Thread(worker);
            threads[i].start();

            start = end;
        }

        for (int i = 0; i < p; i++) {
            if (threads[i] != null) threads[i].join();
        }

        long t_end = System.nanoTime();
        double elapsed_ms = (t_end - t_start) / 1_000_000.0;
        System.out.println(elapsed_ms);
    }

    static void verif_corectitudine(String file1, String file2) throws IOException {
        try (BufferedReader r1 = Files.newBufferedReader(Path.of(file1));
             BufferedReader r2 = Files.newBufferedReader(Path.of(file2))) {
            String s1, s2;
            while (true) {
                s1 = r1.readLine();
                s2 = r2.readLine();
                if (s1 == null && s2 == null) break;
                if (s1 == null || s2 == null) throw new RuntimeException("Different file lengths");
                StringTokenizer t1 = new StringTokenizer(s1);
                StringTokenizer t2 = new StringTokenizer(s2);
                while (t1.hasMoreTokens() && t2.hasMoreTokens()) {
                    int x = Integer.parseInt(t1.nextToken());
                    int y = Integer.parseInt(t2.nextToken());
                    if (x != y) throw new RuntimeException("Mismatch found: " + x + " != " + y);
                }
            }
        }
    }

    static void mainSecvential() {
        citireMatrici("date1.txt");
        convolutieSecventiala();
        afisare("output.txt");
    }

    static void mainParalel(int nrThrd) throws InterruptedException {
        p = nrThrd;
        citireMatrici("date1.txt");
        convolutie_paralela_linii();
        afisare("output_java_paralel.txt");
        // verif_corectitudine("output.txt", "output_paralel.txt");
    }

    public static void main(String[] args) throws InterruptedException {
//        int threads = 16;
//        if (args.length > 0)
//            threads = Integer.parseInt(args[0]);

        //mainSecvential();
        mainParalel(16);
    }
}
