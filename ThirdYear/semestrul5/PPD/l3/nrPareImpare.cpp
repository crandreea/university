#include <iostream>
#include "utils.h"
#include <mpi.h>

using namespace std;
using namespace std::chrono;

// MPI_Send("ce trimit", "cate trimit", "de ce tip", "cui trimit", "id trimitere - identic cu primire", MPI_COMM_WORLD)
// MPI_RECV("ce primesc", "cate primesc", "de ce tip", "de la cine", "id primire", MPI_COMM_WORLD, MPI_STATUS_IGNORE)
// MPI_Bcast("ce trimit", "cate trimit", "ce tip", "root" = 0, MPI_COMM_WORLD)
// MPI_Scatter("ce trimit", "cate trimit", "ce tip", "ce primesc", "cate primesc", "ce tip", "root" = 0, MPI_COMM_WORLD)

// struct Persoana {
//     string nume;
//     int varsta;
//     float inaltime;
// };

// vector<Persoana> persoane;
// persoane[0].nume

int sumaCif(int numar){
    int sum = 0;
    while(numar){
        sum += numar % 10;
        numar = numar / 10;
    }
    return sum;
}

int main(int argc, char **argv){
    MPI_Init(&argc, &argv);

    int rank, size;
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
    MPI_Comm_size(MPI_COMM_WORLD, &size);

    int X;
    vector<int> numbers;
    int N = 0;
    int ARez = 0, BRez = 0;

    if (rank == 0){
        cin >> X;
        read_numbers("numbers.txt", N, numbers);
    }

    MPI_Bcast(&X, 1, MPI_INT, 0, MPI_COMM_WORLD);
    MPI_Bcast(&N, 1, MPI_INT, 0, MPI_COMM_WORLD);

    if (rank == 0){
        int nrWorkeriPari = 0;
        int nrWorkeriImpari = 0;
        
        for(int proc = 1; proc < size; proc++){
            if(proc % 2 == 0){
                nrWorkeriPari++;
            } else {
                nrWorkeriImpari++;
            }
        }

        vector<int> pare, impare;
        for(int i = 0; i < N; i++){
            if(i % 2 == 0){
                pare.push_back(numbers[i]);
            }else{
                impare.push_back(numbers[i]);
            }
        }

        int N_WorkeriPari = (nrWorkeriPari > 0) ? (pare.size() / nrWorkeriPari) : 0;
        int N_WorkeriImpari = (nrWorkeriImpari > 0) ? (impare.size() / nrWorkeriImpari) : 0;

        int currPosPar = 0, currPosImpar = 0;

        for(int proc = 1; proc < size; proc ++){
            int chunkSize;
            vector<int> toSend;

            if(proc % 2 == 0 ){
                chunkSize = N_WorkeriPari;

                if(proc == size - 1 || proc == size - 2){
                    chunkSize = pare.size() - currPosPar;
                }

                for(int i = currPosPar; i < currPosPar + chunkSize; i++){
                    toSend.push_back(pare[i]);
                }

                currPosPar += chunkSize;

            }
            else{
                chunkSize = N_WorkeriImpari;

                if(proc == size - 1 || proc == size - 2){
                    chunkSize = impare.size() - currPosImpar;
                }

                for(int i = currPosImpar; i < currPosImpar + chunkSize; i++){
                    toSend.push_back(impare[i]);
                }

                currPosImpar += chunkSize;
            }

            int actualSize = toSend.size();
            MPI_Send(&actualSize, 1, MPI_INT, proc, 0, MPI_COMM_WORLD);

            if(actualSize > 0){
                MPI_Send(toSend.data(), actualSize, MPI_INT, proc, 0, MPI_COMM_WORLD);
            }
        }

        vector<int> rezultatFinal(N);
        int pozPar = 0, pozImpar = 1;

        for(int proc = 1; proc < size; proc++){
            int lenRecv = 0;
            MPI_Recv(&lenRecv, 1, MPI_INT, proc, 1, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
            
            if(lenRecv > 0){
                vector<int> rezultatPartial(lenRecv);
                MPI_Recv(rezultatPartial.data(), lenRecv, MPI_INT, proc, 1, MPI_COMM_WORLD, MPI_STATUS_IGNORE);

                if(proc % 2 == 0){
                    for(int i = 0; i < lenRecv; i++){
                        rezultatFinal[pozPar] = rezultatPartial[i];
                        pozPar += 2;
                    }
                }else{
                    for(int i = 0; i < lenRecv; i++){
                        rezultatFinal[pozImpar] = rezultatPartial[i];
                        pozImpar += 2;
                    }
                }
            }
            
            int ARecv, BRecv;
            MPI_Recv(&ARecv, 1, MPI_INT, proc, 1, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
            MPI_Recv(&BRecv, 1, MPI_INT, proc, 1, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
            
            BRez += BRecv;
            ARez += ARecv;
        }
        
        write_numbers("result.txt", rezultatFinal);

        cout << "Total A: " << ARez;
        cout << "Total B: " << BRez;

    }
    else{
        int len;
        MPI_Recv(&len, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);

        vector<int> toRec(len);
        if(len > 0){
            MPI_Recv(toRec.data(), len, MPI_INT, 0, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        }

        int A = 0, B = 0;
        for (int i = 0; i < len; i++){
            if (sumaCif(toRec[i]) < X){
                toRec[i] *= 2;
                A++;
            }
            else{
                toRec[i] /= 2;
                B++;
            }
        }

        MPI_Send(&len,1, MPI_INT, 0, 1, MPI_COMM_WORLD);
        MPI_Send(toRec.data(), len, MPI_INT, 0, 1, MPI_COMM_WORLD);
        MPI_Send(&A, 1, MPI_INT, 0, 1, MPI_COMM_WORLD);
        MPI_Send(&B, 1, MPI_INT, 0, 1, MPI_COMM_WORLD);
    }


    MPI_Finalize();
    return 0;
}