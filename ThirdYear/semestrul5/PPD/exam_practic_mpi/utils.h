#ifndef UTILS_H
#define UTILS_H

#include <vector>
#include <string>

void read_numbers(const std::string& filename, int& N, std::vector<string>& numbers, int &n);
void write_numbers(const std::string& filename, const std::vector<string>& numbers);

#endif
