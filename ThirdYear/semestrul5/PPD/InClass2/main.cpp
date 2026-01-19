#include <chrono>
#include <iostream>
#include <random>
#include <thread>
#include <vector>

using namespace std;

const int SIZE = 10000000;

int x[SIZE], y[SIZE], z[SIZE];

int generateRandomNumber(int upperBoundary)
{
    return rand() % upperBoundary + 1;
}


void printArray(vector<int> a, int n)
{
    for (int i = 0; i < n; i++)
        cout << a[i] << " ";
    cout << "\n";
}

double operatorAdunare(int a, int b)
{
    int aux = a * a * a * a * a * a * a + b * b * b * b * b * b * b;
    return sqrt(aux);
}


void task(vector<int>& a, vector<int>& b, vector<int>& c, int start, int end)
{
    for (int i = start; i < end; i++)
    {
        c[i] = operatorAdunare(a[i], b[i]);
    }
}

void task_static(int start, int end)
{
    for (int i = start; i < end; i++)
    {
        z[i] = operatorAdunare(x[i], y[i]);
    }
}


int main()
{
    // secvential
    vector<int> a(SIZE), b(SIZE), c(SIZE);

    for (int i = 0; i < SIZE; i++)
    {
        a[i] = generateRandomNumber(1000000);
        b[i] = generateRandomNumber(1000000);
    }

    auto t_start = std::chrono::high_resolution_clock::now();


    for (int i = 0; i < SIZE; i++)
    {
        c[i] = operatorAdunare(a[i], b[i]);
    }

    auto t_end = std::chrono::high_resolution_clock::now();

    if (SIZE <= 10)
    {
        printArray(a, SIZE);
        printArray(b, SIZE);
        printArray(c, SIZE);
    }


    double elapsed_time_ms = std::chrono::duration<double, std::milli>(t_end - t_start).count();

    cout << elapsed_time_ms << "ms \n";

    // paralel
    cout << "Paralel \n";
    vector<int> c_paralel(SIZE);

    int p = 4;
    int start, end, rest;
    start = 0;
    end = SIZE / p;
    rest = SIZE % p;

    vector<thread> threads(p);


    t_start = std::chrono::high_resolution_clock::now();
    for (int i = 0; i < p; i++)
    {
        if (rest)
        {
            end++;
            rest--;
        }

        //threads[i] = thread(task, ref(a), ref(b), ref(c_paralel), start, end);
        threads[i] = thread(task_static, start, end);
        start = end;
        end = end + SIZE / p;
    }

    for (int i = 0; i < p; i++)
    {
        threads[i].join();
    }

    if (SIZE <= 10)
    {
        printArray(c_paralel, SIZE);
    }

    t_end = std::chrono::high_resolution_clock::now();

    elapsed_time_ms = std::chrono::duration<double, std::milli>(t_end - t_start).count();

    cout << elapsed_time_ms << "ms\n";


    // static

    return 0;
}


// #include <iostream>
// #include <random>
// #include <thread>
// using namespace std;
//
// #define SIZE 1000000
//
// int a[SIZE], b[SIZE], c[SIZE];
//
// void printVector(vector<int>& copy) {
//     for (int i = 0; i < SIZE; ++i)
//         cout << copy[i] << " ";
//     cout << std::endl;
// }
//
// int generateRandomNumber(int upperBoundary) {
//     return rand() % upperBoundary + 1;
// }
//
// int operatorAdunare(int a, int b) {
//     return a * a * a * a + b * b * b * b;
// }
//
// void task(vector<int> &a, vector<int> &b, vector<int> &c, int start, int end) {
//     for (int i = start; i < end; ++i) {
//         c[i] = operatorAdunare(a[i], b[i]);
//     }
// }
//
// void task_2(int start, int end) {
//     for (int i = start; i < end; ++i) {
//         c[i] = operatorAdunare(a[i], b[i]);
//     }
// }
//
// int main() {
//
//     //secvential
//     vector<int> a(SIZE), b(SIZE), c(SIZE);
//
//     for (int i = 0; i < SIZE; ++i) {
//         a[i] = generateRandomNumber(SIZE);
//         b[i] = generateRandomNumber(SIZE);
//     }
//
//     auto t_start = std::chrono::high_resolution_clock::now();
//
//     for (int i = 0; i < SIZE; ++i) {
//         c[i] = operatorAdunare(a[i], b[i]);
//     }
//
//     auto t_end = std::chrono::high_resolution_clock::now();
//     double elapsed_time_ms = std::chrono::duration<double, std::milli>(t_end-t_start).count();
//
//     if (SIZE <= 10) {
//         printVector(c);
//     }
//
//     cout << "Time " << elapsed_time_ms << endl;
//
//     // paralel
//     int p = 4;
//     int start = 0, end = SIZE / p, rest = SIZE % p;
//
//     vector<thread> threads(p);
//
//     auto t_start_t = std::chrono::high_resolution_clock::now();
//
//     for (int i = 0; i < p; ++i) {
//         if (rest) {
//             end ++;
//             rest --;
//         }
//
//         //threads[i] = thread(task, ref(a), ref(b), ref(c), start, end);
//         threads[i] = thread(task_2, start, end);
//
//         start = end;
//         end = end + SIZE / p;
//     }
//
//     for (int i = 0; i < p; ++i) {
//         threads[i].join();
//     }
//
//     auto t_end_t = std::chrono::high_resolution_clock::now();
//     double elapsed_time_ms_t = std::chrono::duration<double, std::milli>(t_end_t-t_start_t).count();
//
//     cout << "Time with threads" << elapsed_time_ms_t << endl;
//     return 0;
// }