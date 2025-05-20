#include <iostream>
#include <vector>
using namespace std;
int main() {
    vector<int> v;
    v.push_back(5); // v[0] = 5
    v.push_back(7); // v[1] = 7
    v[0] = 6; //v = [6, 7]
    v.push_back(8); //v = [6, 7, 8]
    auto it = v.begin(); //it = 6
    it = it + 1; //it = 7
    while (it != v.end()) {
        cout << *it << endl; //7 8
        it++;
    return 0;
}

//7 8