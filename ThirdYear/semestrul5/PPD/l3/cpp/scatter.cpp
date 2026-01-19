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

    int digits1 = 0, digits2 = 0;
    int totalLen = 0;
    int chunkSize = 0;

    unsigned char *number1 = nullptr;
    unsigned char *number2 = nullptr;
    unsigned char *fullResult = nullptr;

    if (rank == 0)
    {
        // citim full numerele
        utils::readNumber(filenameNumber1, number1, digits1);
        utils::readNumber(filenameNumber2, number2, digits2);

        int maxDigits = max(digits1, digits2);

        if (maxDigits % size != 0)
        {
            totalLen = (maxDigits / size) * size + size;
        }
        else
        {
            totalLen = maxDigits;
        }

        unsigned char *temp1 = new unsigned char[totalLen];
        unsigned char *temp2 = new unsigned char[totalLen];

        // copiem numerele citite in vectorii de lung noua
        memcpy(temp1, number1, digits1);
        memset(temp1 + digits1, 0, totalLen - digits1);

        memcpy(temp2, number2, digits2);
        memset(temp2 + digits2, 0, totalLen - digits2);

        delete[] number1;
        delete[] number2;

        number1 = temp1;
        number2 = temp2;

        chunkSize = totalLen / size;

        // alocam sp pt rezultatul final
        fullResult = new unsigned char[totalLen + 1];
        memset(fullResult, 0, totalLen + 1);
    }

    // trimitem datele la toate procesele
    MPI_Bcast(&totalLen, 1, MPI_INT, 0, MPI_COMM_WORLD);
    MPI_Bcast(&chunkSize, 1, MPI_INT, 0, MPI_COMM_WORLD);
    MPI_Bcast(&digits1, 1, MPI_INT, 0, MPI_COMM_WORLD);
    MPI_Bcast(&digits2, 1, MPI_INT, 0, MPI_COMM_WORLD);

    unsigned char *localChunk1 = new unsigned char[chunkSize];
    unsigned char *localChunk2 = new unsigned char[chunkSize];

    // imartim cifrele intre procese
    MPI_Scatter(number1, chunkSize, MPI_UNSIGNED_CHAR, localChunk1, chunkSize, MPI_UNSIGNED_CHAR, 0, MPI_COMM_WORLD);
    MPI_Scatter(number2, chunkSize, MPI_UNSIGNED_CHAR, localChunk2, chunkSize, MPI_UNSIGNED_CHAR, 0, MPI_COMM_WORLD);

    unsigned char carry = 0;

    // proc 1...p primesc carry ul de la proc anterioare
    if (rank > 0)
    {
        MPI_Recv(&carry, 1, MPI_UNSIGNED_CHAR, rank - 1, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
    }

    // calc suma
    unsigned char *localResult = new unsigned char[chunkSize];
    for (int i = 0; i < chunkSize; i++)
    {
        unsigned char sum = localChunk1[i] + localChunk2[i] + carry;
        localResult[i] = sum % 10;
        carry = sum / 10;
    }

    // proc 0..p-1 trimit carry ul la urm proc
    if (rank < size - 1)
    {
        MPI_Send(&carry, 1, MPI_UNSIGNED_CHAR, rank + 1, 0, MPI_COMM_WORLD);
    }

    // proc p trimite carry ul la proc 0 (master)
    if (rank == size - 1)
    {
        MPI_Send(&carry, 1, MPI_UNSIGNED_CHAR, 0, 1, MPI_COMM_WORLD);
    }

    // proc 0 aduca localResult de la fieacre proc
    MPI_Gather(localResult, chunkSize, MPI_UNSIGNED_CHAR, fullResult, chunkSize, MPI_UNSIGNED_CHAR, 0, MPI_COMM_WORLD);

    if (rank == 0)
    {
        unsigned char finalCarry = 0;

        // primeste ultimul carry de la proc final
        MPI_Recv(&finalCarry, 1, MPI_UNSIGNED_CHAR, size - 1, 1, MPI_COMM_WORLD, MPI_STATUS_IGNORE);

        fullResult[totalLen] = finalCarry;

        // stergem 0 urile in plus
        int resultLen = totalLen + 1;
        while (resultLen > 1 && fullResult[resultLen - 1] == 0)
        {
            resultLen--;
        }

        auto end = high_resolution_clock::now();
        auto duration = duration_cast<microseconds>(end - start);

        const std::string expectedFilenameStr = "number_" + std::to_string(digits1) + "_" + std::to_string(digits2) + ".txt";
        const char *expectedFilename = expectedFilenameStr.c_str();
        if (!utils::isResultValid(expectedFilename, fullResult, resultLen))
        {
            MPI_Finalize();
            return 1;
        }

        cout << duration.count() / 1000.0;

        delete[] number1;
        delete[] number2;
        delete[] fullResult;
    }

    delete[] localChunk1;
    delete[] localChunk2;
    delete[] localResult;

    MPI_Finalize();
    return 0;
}
