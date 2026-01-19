#include <iostream>
#include <fstream>
#include <cstring>
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

    int rank, size;
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
    MPI_Comm_size(MPI_COMM_WORLD, &size);

    auto start = high_resolution_clock::now();

    if (rank == 0)
    {
        int digits1, digits2;
        utils::readDigits(filenameNumber1, digits1);
        utils::readDigits(filenameNumber2, digits2);

        int maxDigits = max(digits1, digits2);

        int chunkSize = maxDigits / (size - 1);

        int currentProcessId = 1;
        int currentPos = 0;

        unsigned char *fullResult = new unsigned char[maxDigits + 1];
        int fullResultSize = 0;

        while (currentPos < maxDigits && currentProcessId < size)
        {
            int currentChunk = chunkSize;

            if (currentPos + currentChunk > maxDigits)
            {
                currentChunk = maxDigits - currentPos;
            }

            unsigned char *chunk1 = nullptr;
            unsigned char *chunk2 = nullptr;

            utils::readChunk(filenameNumber1, chunk1, currentPos, currentChunk);
            utils::readChunk(filenameNumber2, chunk2, currentPos, currentChunk);

            MPI_Send(&currentChunk, 1, MPI_INT, currentProcessId, 0, MPI_COMM_WORLD);

            MPI_Send(chunk1, currentChunk, MPI_UNSIGNED_CHAR, currentProcessId, 1, MPI_COMM_WORLD);
            MPI_Send(chunk2, currentChunk, MPI_UNSIGNED_CHAR, currentProcessId, 2, MPI_COMM_WORLD);

            delete[] chunk1;
            delete[] chunk2;

            currentPos += currentChunk;
            currentProcessId++;
        }

        currentPos = 0;
        for (int proc = 1; proc < size; ++proc)
        {
            int recvChunk;

            MPI_Recv(&recvChunk, 1, MPI_INT, proc, 3, MPI_COMM_WORLD, MPI_STATUSES_IGNORE);
            MPI_Recv(&fullResult[currentPos], recvChunk, MPI_UNSIGNED_CHAR, proc, 4, MPI_COMM_WORLD, MPI_STATUSES_IGNORE);

            currentPos += recvChunk;
        }

        fullResultSize = currentPos;

        int resultLen = fullResultSize;
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
            delete[] fullResult;
            MPI_Finalize();
            return 1;
        }

        cout << duration.count() / 1000.0;

        delete[] fullResult;
    }
    else
    {
        int chunkSize;

        MPI_Recv(&chunkSize, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);

        unsigned char *chunk1 = new unsigned char[chunkSize];
        unsigned char *chunk2 = new unsigned char[chunkSize];

        MPI_Recv(chunk1, chunkSize, MPI_UNSIGNED_CHAR, 0, 1, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        MPI_Recv(chunk2, chunkSize, MPI_UNSIGNED_CHAR, 0, 2, MPI_COMM_WORLD, MPI_STATUS_IGNORE);

        unsigned char *partialSum = new unsigned char[chunkSize];
        unsigned char *localCarry = new unsigned char[chunkSize];

        for (int i = 0; i < chunkSize; i++)
        {
            unsigned char sum = chunk1[i] + chunk2[i];
            partialSum[i] = sum % 10;
            localCarry[i] = sum / 10;
        }

        unsigned char carry_in = 0;
        if (rank > 1)
        {
            MPI_Recv(&carry_in, 1, MPI_UNSIGNED_CHAR, rank - 1, 5, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        }

        unsigned char *result = new unsigned char[chunkSize + 1];
        unsigned char carry_out = carry_in;

        if (carry_out != 0)
        {
            // modifc cond carry
            for (int i = 0; i < chunkSize; i++)
            {
                unsigned char sum = partialSum[i] + carry_out;
                result[i] = sum % 10;
                carry_out = (sum / 10) + localCarry[i];
            }
        }

        if (rank < size - 1)
        {
            MPI_Send(&carry_out, 1, MPI_UNSIGNED_CHAR, rank + 1, 5, MPI_COMM_WORLD);
        }

        int resultSize = chunkSize;
        if (rank == size - 1 && carry_out > 0)
        {
            result[chunkSize] = carry_out;
            resultSize = chunkSize + 1;
        }

        MPI_Send(&resultSize, 1, MPI_INT, 0, 3, MPI_COMM_WORLD);
        MPI_Send(result, resultSize, MPI_UNSIGNED_CHAR, 0, 4, MPI_COMM_WORLD);

        delete[] chunk1;
        delete[] chunk2;
        delete[] partialSum;
        delete[] localCarry;
        delete[] result;
    }

    MPI_Finalize();
    return 0;
}
