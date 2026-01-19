#include <iostream>
#include "utils.h"

using namespace std;
using namespace std::chrono;

void addNumbersSequential(const unsigned char *number1, int digits1, const unsigned char *number2, int digits2,
                          unsigned char *&result, int &digitsResult)
{

    int maxDigits = max(digits1, digits2);

    // aloc un sp in plus ca poate avem carry
    result = new unsigned char[maxDigits + 1];
    memset(result, 0, maxDigits + 1);

    unsigned char carry = 0;

    for (int i = 0; i < maxDigits; ++i)
    {
        unsigned char digit1 = (i < digits1) ? number1[i] : 0;
        unsigned char digit2 = (i < digits2) ? number2[i] : 0;

        unsigned char sum = digit1 + digit2 + carry;

        result[i] = sum % 10;
        carry = sum / 10;
    }

    result[maxDigits] = carry;

    // cresc nr de digits ca poate avem un carry in plus
    digitsResult = maxDigits + 1;

    // sterg 0 urile ramase
    while (digitsResult > 1 && result[digitsResult - 1] == 0)
    {
        digitsResult--;
    }
}

int main(const int argc, char **argv)
{
    if (argc != 4)
        return 1;

    const char *filenameNumber1 = argv[1];
    const char *filenameNumber2 = argv[2];

    unsigned char *number1 = nullptr;
    unsigned char *number2 = nullptr;
    unsigned char *result = nullptr;
    int digits1, digits2, digitsResult;

    utils::readNumber(filenameNumber1, number1, digits1);
    utils::readNumber(filenameNumber2, number2, digits2);

    auto start = high_resolution_clock::now();

    addNumbersSequential(number1, digits1, number2, digits2, result, digitsResult);

    auto end = high_resolution_clock::now();

    auto duration = duration_cast<microseconds>(end - start);
    cout << duration.count() / 1000.0;

    const std::string filenameResultStr = "number_" + std::to_string(digits1) + "_" + std::to_string(digits2) + ".txt";
    const char *filenameResult = filenameResultStr.c_str();

    utils::writeNumber(filenameResult, result, digitsResult);

    delete[] number1;
    delete[] number2;
    delete[] result;

    return 0;
}
