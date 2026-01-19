#include <iostream>
#include "mpi.h"
#include "utils.h"

using namespace std;
using namespace std::chrono;

int main(int argc, char **argv)
{
    MPI_Init(&argc, &argv);

    if (argc != 4)
    {
        MPI_Finalize();
        return 1;
    }

    const char *filenameNumber1 = argv[1];
    const char *filenameNumber2 = argv[2];
    const char *filenameOutput = (argc == 4) ? argv[3] : nullptr;

    int rank, size;
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
    MPI_Comm_size(MPI_COMM_WORLD, &size);

    auto start = high_resolution_clock::now();

    if (rank == 0)
    {
        int digits1, digits2;

        // citim nr de cifre pt fiecare nr
        utils::readDigits(filenameNumber1, digits1);
        utils::readDigits(filenameNumber2, digits2);

        int maxDigits = max(digits1, digits2);

        int numOfWorkers = size - 1;
        int chunkSize = maxDigits / numOfWorkers;

        // alocam memorie cu +1 in caz ca avem un carry la final
        unsigned char *fullResult = new unsigned char[maxDigits + 1];
        memset(fullResult, 0, maxDigits + 1);

        int currentPos = 0;
        int currentProcessId = 1;

        while (currentPos < maxDigits && currentProcessId <= numOfWorkers)
        {
            int currentChunkSize = chunkSize;

            if (currentPos + currentChunkSize > maxDigits)
            {
                currentChunkSize = maxDigits - currentPos;
            }

            unsigned char *chunk1 = nullptr;
            unsigned char *chunk2 = nullptr;

            // citim doar chunkuri de N/(p-1) cifre, nu intregul numar
            utils::readChunk(filenameNumber1, chunk1, currentPos, currentChunkSize);
            utils::readChunk(filenameNumber2, chunk2, currentPos, currentChunkSize);

            // trimitem nr de cifre
            MPI_Send(&currentChunkSize, 1, MPI_INT, currentProcessId, 0, MPI_COMM_WORLD);

            // trimitem N/(p-1) cifre
            MPI_Send(chunk1, currentChunkSize, MPI_UNSIGNED_CHAR, currentProcessId, 1, MPI_COMM_WORLD);
            MPI_Send(chunk2, currentChunkSize, MPI_UNSIGNED_CHAR, currentProcessId, 2, MPI_COMM_WORLD);

            delete[] chunk1;
            delete[] chunk2;

            // ne mutam pe urm chunk
            currentPos += currentChunkSize;
            currentProcessId++;
        }

        // primim rezultatele de la workeri
        currentPos = 0;
        for (int proc = 1; proc < size; ++proc)
        {
            int recvChunk;

            // primi nr de cifre
            MPI_Recv(&recvChunk, 1, MPI_INT, proc, 3, MPI_COMM_WORLD, MPI_STATUSES_IGNORE);

            // primit suma cifrelor
            MPI_Recv(&fullResult[currentPos], recvChunk, MPI_UNSIGNED_CHAR, proc, 4, MPI_COMM_WORLD, MPI_STATUSES_IGNORE);

            currentPos += recvChunk;
        }

        // stergem 0 urile adaugate anterior
        int resultDigits = currentPos;
        while (resultDigits > 1 && fullResult[resultDigits - 1] == 0)
        {
            resultDigits--;
        }

        auto end = high_resolution_clock::now();
        auto duration = duration_cast<microseconds>(end - start);

        const std::string expectedFilenameStr = "number_"
                                          + std::to_string(digits1) + "_"
                                          + std::to_string(digits2) + ".txt";
        const char* expectedFilename = expectedFilenameStr.c_str();
        if (!utils::isResultValid(expectedFilename,
                                fullResult, resultDigits)) {
            delete[] fullResult;
            MPI_Finalize();
            return 1;
        }
        
        // utils::writeResult(filenameOutput, fullResult, resultDigits);
        // utils::printNumber(fullResult, resultDigits);
        cout << duration.count() / 1000.0 << endl;
        delete[] fullResult;
    }
    else
    {
        int chunkSize;

        // primim nr de cifre 
        MPI_Recv(&chunkSize, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);

        unsigned char *chunk1 = new unsigned char[chunkSize];
        unsigned char *chunk2 = new unsigned char[chunkSize];

        // primim cifrele de la proc 0 
        MPI_Recv(chunk1, chunkSize, MPI_UNSIGNED_CHAR, 0, 1, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        MPI_Recv(chunk2, chunkSize, MPI_UNSIGNED_CHAR, 0, 2, MPI_COMM_WORLD, MPI_STATUS_IGNORE);

        unsigned char carry = 0;

        // procesele 2...p primesc carry-ul de la procesele anterioare
        if (rank > 1)
        {
            MPI_Recv(&carry, 1, MPI_UNSIGNED_CHAR, rank - 1, 5, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        }

        unsigned char *result = new unsigned char[chunkSize + 1];

        // adunam cifrele primite + carry ul primit
        for (int i = 0; i < chunkSize; i++)
        {
            unsigned char sum = chunk1[i] + chunk2[i] + carry;
            result[i] = sum % 10;
            carry = sum / 10;
        }

        // procesele 1...p-1 trimit carryl proceselor urmatoare
        if (rank < size - 1)
        {
            MPI_Send(&carry, 1, MPI_UNSIGNED_CHAR, rank + 1, 5, MPI_COMM_WORLD);
        }

        // verific daca suntem la ultimul proces si ne a ramas carry atunci crestem dim rez 
        int resultSize = chunkSize;
        if (rank == size - 1 && carry > 0)
        {
            result[chunkSize] = carry;
            resultSize = chunkSize + 1;
        }

        // trimitem nr de cifre din rez la proc 0
        MPI_Send(&resultSize, 1, MPI_INT, 0, 3, MPI_COMM_WORLD);

        // trimitem rez la proc 0
        MPI_Send(result, resultSize, MPI_UNSIGNED_CHAR, 0, 4, MPI_COMM_WORLD);

        delete[] chunk1;
        delete[] chunk2;
        delete[] result;
    }

    MPI_Finalize();
    return 0;
}
