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

		// pregatim send requesturile
		MPI_Request *sendRequests = new MPI_Request[3 * size];
		int *chunkSizes = new int[size];
		int reqCount = 0;

		int currentPos = 0;

		unsigned char **chunks1 = new unsigned char *[size];
		unsigned char **chunks2 = new unsigned char *[size];

		for (int proc = 1; proc < size; proc++)
		{
			int currentChunk = chunkSize;

			if (currentPos + currentChunk > maxDigits)
			{
				currentChunk = maxDigits - currentPos;
			}

			chunkSizes[proc] = currentChunk;

			// citim pe chunkuri
			utils::readChunk(filenameNumber1, chunks1[proc], currentPos, currentChunk);
			utils::readChunk(filenameNumber2, chunks2[proc], currentPos, currentChunk);

			// trimitem marimea chunkului
			MPI_Isend(&chunkSizes[proc], 1, MPI_INT, proc, 0, MPI_COMM_WORLD, &sendRequests[reqCount++]);

			// trimitem chunkurile la procese
			MPI_Isend(chunks1[proc], currentChunk, MPI_UNSIGNED_CHAR, proc, 1, MPI_COMM_WORLD, &sendRequests[reqCount++]);
			MPI_Isend(chunks2[proc], currentChunk, MPI_UNSIGNED_CHAR, proc, 2, MPI_COMM_WORLD, &sendRequests[reqCount++]);

			currentPos += currentChunk;
		}

		MPI_Request *recvSizeRequests = new MPI_Request[size - 1];
		int *recvSizes = new int[size];
		unsigned char **results = new unsigned char *[size];

		// primim lungimea rezultatului
		for (int proc = 1; proc < size; proc++)
		{
			MPI_Irecv(&recvSizes[proc], 1, MPI_INT, proc, 3, MPI_COMM_WORLD, &recvSizeRequests[proc - 1]);
		}

		MPI_Waitall(size - 1, recvSizeRequests, MPI_STATUSES_IGNORE);

		MPI_Request *recvDataRequests = new MPI_Request[size - 1];
		for (int proc = 1; proc < size; proc++)
		{
			results[proc] = new unsigned char[recvSizes[proc]];
			MPI_Irecv(results[proc], recvSizes[proc], MPI_UNSIGNED_CHAR, proc, 4, MPI_COMM_WORLD, &recvDataRequests[proc - 1]);
		}

		MPI_Waitall(size - 1, recvDataRequests, MPI_STATUSES_IGNORE);
		MPI_Waitall(reqCount, sendRequests, MPI_STATUSES_IGNORE);

		int totalResultSize = 0;
		for (int proc = 1; proc < size; proc++)
		{
			totalResultSize += recvSizes[proc];
		}

		unsigned char *fullResult = new unsigned char[totalResultSize];

		int pos = 0;
		for (int proc = 1; proc < size; proc++)
		{
			memcpy(fullResult + pos, results[proc], recvSizes[proc]);
			pos += recvSizes[proc];
		}

		int resultDigits = totalResultSize;
		while (resultDigits > 1 && fullResult[resultDigits - 1] == 0)
		{
			resultDigits--;
		}

		auto end = high_resolution_clock::now();
		auto duration = duration_cast<microseconds>(end - start);

		const std::string expectedFilenameStr = "number_" + std::to_string(digits1) + "_" + std::to_string(digits2) + ".txt";
		const char *expectedFilename = expectedFilenameStr.c_str();

		if (!utils::isResultValid(expectedFilename, fullResult, resultDigits))
		{
			MPI_Finalize();
			return 1;
		}

		cout << duration.count() / 1000.0;

		delete[] fullResult;
		delete[] sendRequests;
		delete[] chunkSizes;
		delete[] recvSizeRequests;
		delete[] recvSizes;
		delete[] recvDataRequests;
		for (int proc = 1; proc < size; proc++)
		{
			delete[] chunks1[proc];
			delete[] chunks2[proc];
			delete[] results[proc];
		}
		delete[] chunks1;
		delete[] chunks2;
		delete[] results;
	}
	else
	{
		int chunkSize;
		unsigned char *chunk1 = nullptr;
		unsigned char *chunk2 = nullptr;

		MPI_Request req_size, req_chunk1, req_chunk2, req_carry_in;

		// primim chunk size ul
		MPI_Irecv(&chunkSize, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, &req_size);
		MPI_Wait(&req_size, MPI_STATUS_IGNORE);

		chunk1 = new unsigned char[chunkSize];
		chunk2 = new unsigned char[chunkSize];

		// primim chunkurile
		MPI_Irecv(chunk1, chunkSize, MPI_UNSIGNED_CHAR, 0, 1, MPI_COMM_WORLD, &req_chunk1);
		MPI_Irecv(chunk2, chunkSize, MPI_UNSIGNED_CHAR, 0, 2, MPI_COMM_WORLD, &req_chunk2);

		unsigned char carry_in = 0;

		// proc 2...p asteapta carry ul de la pro precedent
		if (rank > 1)
		{
			MPI_Irecv(&carry_in, 1, MPI_UNSIGNED_CHAR, rank - 1, 5, MPI_COMM_WORLD, &req_carry_in);
		}

		// asteptam sa ajung chunkurile
		MPI_Wait(&req_chunk1, MPI_STATUS_IGNORE);
		MPI_Wait(&req_chunk2, MPI_STATUS_IGNORE);

		// asteptam carry ul 
		if (rank > 1)
		{
			MPI_Wait(&req_carry_in, MPI_STATUS_IGNORE);
		}

		unsigned char *result = new unsigned char[chunkSize + 1];
		unsigned char carry_out = carry_in;

		for (int i = 0; i < chunkSize; i++)
		{
			unsigned char sum = chunk1[i] + chunk2[i] + carry_out;
			result[i] = sum % 10;
			carry_out = sum / 10;
		}

		// trimitem carry ul la urm proces
		if (rank < size - 1)
		{
			MPI_Request req_send_carry;
			MPI_Isend(&carry_out, 1, MPI_UNSIGNED_CHAR, rank + 1, 5, MPI_COMM_WORLD, &req_send_carry);
			MPI_Wait(&req_send_carry, MPI_STATUS_IGNORE);
		}

		// atasam carry ul de la ultimul proces
		int resultSize = chunkSize;
		if (rank == size - 1 && carry_out > 0)
		{
			result[chunkSize] = carry_out;
			resultSize = chunkSize + 1;
		}

		// trimitem rez la proc 0 
		MPI_Request req_send_size, req_send_result;
		MPI_Isend(&resultSize, 1, MPI_INT, 0, 3, MPI_COMM_WORLD, &req_send_size);
		MPI_Isend(result, resultSize, MPI_UNSIGNED_CHAR, 0, 4, MPI_COMM_WORLD, &req_send_result);

		// asteptam sa se trimita rez ca dupa sa eliberam mem
		MPI_Wait(&req_send_size, MPI_STATUS_IGNORE);
		MPI_Wait(&req_send_result, MPI_STATUS_IGNORE);

		delete[] chunk1;
		delete[] chunk2;
		delete[] result;
	}

	MPI_Finalize();
	return 0;
}
