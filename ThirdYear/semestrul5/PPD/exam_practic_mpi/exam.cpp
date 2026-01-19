#include <iostream>
#include "mpi.h"
#include <fstream>

using namespace std;
using namespace std::chrono;

vector<string> g(vector<string> sir, int n){
    if(sir.size() < n){
        int len = sir.size();
        vector<string> result(len + 1);
        //inversez sirul + #
        int j = 0;
        for(int i = len - 1; i > 0; i--){
            result[j++] = sir[i];
        }

        result[len] = "#";
        return result;
    }
    else{
        vector<string> result(n + 1);
        //primele n + !
        for(int i = 0; i < n; i++){
            result[i] = sir[i];
        }

        result[n] = "!";
        return result;
    } 
}

int main(int argc, char **argv){
    MPI_Init(&argc, &argv);

    int rank, size;
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
    MPI_Comm_size(MPI_COMM_WORLD, &size);

    auto start = high_resolution_clock::now();

    int N;
    int n;
    vector<string> strings(N);
    int chunkSize;

    if (rank == 0){

        ifstream fin("date.txt");
        if (!fin) {
            cerr << "Eroare la deschiderea fisierului " << "\n";
            exit(1);
        }

        fin >> N;
        for (int i = 0; i < N; i++)
            fin >> strings[i];

        fin >> n;
        fin.close();

        cout<< "N:" << N << endl;
        cout<<"n:" << n << endl;

        MPI_Bcast(&N, 1, MPI_INT, 0, MPI_COMM_WORLD);
        MPI_Bcast(&n, 1, MPI_INT, 0, MPI_COMM_WORLD);

        int rest = N % size;
        int curPos = 0;
        chunkSize = N / size;

        for(int proc = 1; proc < size; proc ++ ){
            chunkSize = N / size;

            if (rest > 0){
                chunkSize += 1;
                rest --;
            }

            MPI_Send(&chunkSize, 1, MPI_INT, proc, 0, MPI_COMM_WORLD);
            
            vector<string> toSend(chunkSize);
            for(int i = curPos; i < curPos + chunkSize; i++){
                toSend.push_back(strings[i]);
            }

            curPos += chunkSize;
            MPI_Send(toSend.data(), chunkSize, MPI_CHAR, proc, 0, MPI_COMM_WORLD);
        }

        //la root ii raman ultimele chunkSize elemente
        vector<string> result(chunkSize);
        int A, B;
        for(int i = N - chunkSize; i < N; i++){
            int len = strings[i].size();
            if(len < n){
                //inversez sirul + #
                int j = 0;
                for(int i = len - 1; i > 0; i--){
                    result.push_back(strings[i]);
                }

                result.push_back("#");
                A++;
            }
            else{
                //primele n + !
                for(int i = 0; i < n; i++){
                    result.push_back(strings[i]);
                }

                result.push_back("!");
                B++;
            } 
        }

        MPI_Send(&A, 1, MPI_INT, 1, 2, MPI_COMM_WORLD);
        MPI_Send(&B, 1, MPI_INT, 1, 2, MPI_COMM_WORLD);

        
        int lenResultPartial;
        vector<string> resultFinal;
        vector<string> resultPartial(lenResultPartial);

        for(int proc = 1; proc < size; proc++){
            MPI_Recv(&lenResultPartial, 1, MPI_INT, proc, 1, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
            MPI_Recv(resultPartial.data(), lenResultPartial, MPI_CHAR, proc, 1, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
            
            for(int i = 0; i< lenResultPartial; i++){
                resultFinal.push_back(resultPartial[i]);
            }
        }
        
        for(int i = 0; i < chunkSize; i++){
            resultFinal.push_back(result[i]);
        }
        
        
        ofstream fout("result.txt");
        if (!fout) {
            cerr << "Eroare la scrierea fisierului "<< "\n";
            exit(1);
        }

        for (string x : resultFinal)
            fout << x << "\n";

        fout.close();
        
    }else{
        int len;
        vector<string> toRecv(len);
        int A, B;

        MPI_Recv(&len, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        MPI_Recv(toRecv.data(), len, MPI_INT, 0, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);

        vector<string> result;

        if(len < n){
            //inversez sirul + #
            int j = 0;
            for(int i = len - 1; i > 0; i--){
                result.push_back(toRecv[i]);
            }

            result.push_back("#");
            A++;
        }
        else{
            //primele n + !
            for(int i = 0; i < n; i++){
                result.push_back(toRecv[i]);
            }

            result.push_back("!");
            B++;
        } 

        int lenToSend = result.size();
        MPI_Send(&lenToSend, 1, MPI_INT, 0, 1, MPI_COMM_WORLD);
        MPI_Send(result.data(), lenToSend, MPI_CHAR, 0, 1, MPI_COMM_WORLD);

        MPI_Send(&A, 1, MPI_INT, 1, 2, MPI_COMM_WORLD);
        MPI_Send(&B, 1, MPI_INT, 1, 2, MPI_COMM_WORLD);

        if(rank == 1){
            int A, B, AFinal, BFinal;

            for(int proc = 1; proc < size; proc++){
                MPI_Recv(&A, 1, MPI_INT, proc, 2, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
                MPI_Recv(&B, 1, MPI_INT, proc, 2, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
                AFinal += A;
                BFinal += B;
            }

            cout << "Total cazuri A: "<< AFinal << endl;
            cout << "Total cazuri B: "<< BFinal;
        }

    }

    MPI_Finalize();
    return 0;
}