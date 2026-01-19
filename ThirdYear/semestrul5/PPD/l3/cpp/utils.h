#include <string>
#include <fstream>
#include <filesystem>

namespace utils
{

    using namespace std;
    namespace fs = std::filesystem;

    const string DEFAULT_FOLDER = "output/";

    void readDigits(const string &filename, int &digits)
    {
        ifstream fin(filename);
        if (!fin.is_open())
        {
            cerr << "Cannot open " << filename << endl;
            abort();
        }

        fin >> digits;
        fin.close();
    }

    void readNumber(const string &filename, unsigned char *&number, int &digits)
    {
        std::ifstream fin(filename);
        if (!fin.is_open())
        {
            std::cerr << "Cannot open " << filename << std::endl;
            abort();
        }

        fin >> digits;
        number = new unsigned char[digits];

        char chr;
        for (int i = digits - 1; i >= 0; --i)
        {
            fin.get(chr);
            while (chr == '\n' || chr == '\r')
            {
                fin.get(chr);
            }
            number[i] = chr - '0';
        }

        fin.close();
    }

    void readChunk(const string &filename, unsigned char *&chunk,
                   int startPos, int count)
    {
        std::ifstream fin(filename);
        if (!fin.is_open())
        {
            std::cerr << "Cannot open " << filename << std::endl;
            abort();
        }

        int totalDigits;
        fin >> totalDigits;

        // IMPORTANT: Dacă startPos >= totalDigits, returnează chunk plin cu 0
        if (startPos >= totalDigits)
        {
            chunk = new unsigned char[count];
            memset(chunk, 0, count); // Toate cifrele sunt 0
            fin.close();
            return;
        }

        // Calculează câte cifre putem citi efectiv din fișier
        int availableDigits = totalDigits - startPos;
        int digitsToRead = min(availableDigits, count);

        // Alocă chunk-ul și inițializează cu 0
        chunk = new unsigned char[count];
        memset(chunk, 0, count);

        // Citește TOATE cifrele în memorie (temporar)
        vector<unsigned char> allDigits(totalDigits);
        char chr;
        for (int i = totalDigits - 1; i >= 0; --i)
        {
            fin.get(chr);
            while (chr == '\n' || chr == '\r' || chr == ' ')
            {
                fin.get(chr);
            }
            allDigits[i] = chr - '0';
        }

        fin.close();

        // Copiază doar chunk-ul dorit
        for (int i = 0; i < digitsToRead; i++)
        {
            chunk[i] = allDigits[startPos + i];
        }

        // Restul rămân 0 (pentru padding când fișierul e mai mic)
    }

    void writeNumber(const string &filename, unsigned char *number, int &digits)
    {
        fs::create_directories(DEFAULT_FOLDER);

        string fullPath = DEFAULT_FOLDER + filename;
        ofstream fout(fullPath);
        if (!fout.is_open())
        {
            cerr << "Cannot open " << fullPath << endl;
            abort();
        }

        fout << digits << "\n";
        for (int i = digits - 1; i >= 0; --i)
        {
            fout << static_cast<int>(number[i]);
        }

        fout.close();
    }

    void printNumber(unsigned char *&number, int digits)
    {
        cout << endl;
        for (int i = digits - 1; i >= 0; --i)
        {
            cout << static_cast<int>(number[i]);
        }
        cout << endl;
    }

    void writeResult(const string &filename, unsigned char *number, int digits)
    {
        ofstream fout(filename);
        if (!fout.is_open())
        {
            cerr << "Cannot open " << filename << " for writing" << endl;
            abort();
        }

        fout << digits << "\n";
        for (int i = digits - 1; i >= 0; --i)
        {
            fout << static_cast<int>(number[i]);
            // if (i > 0) fout << " ";
        }
        fout << "\n";

        fout.close();
    }

    bool isResultValid(const string &expectedFilename,
                       unsigned char *&actual, int &digitsActual)
    {
        int digitsExpected;
        unsigned char *expected = nullptr;
        string fullPath = DEFAULT_FOLDER + expectedFilename;
        readNumber(fullPath, expected, digitsExpected);

        if (digitsActual != digitsExpected)
        {
            delete[] expected;
            return false;
        }

        for (int i = 0; i < digitsExpected; ++i)
        {
            if (actual[i] != expected[i])
            {
                delete[] expected;
                return false;
            }
        }

        delete[] expected;
        return true;
    }
}