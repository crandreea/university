#include <iostream>

using namespace std;
using std::vector;
template<typename T>
class Measurement{
private:
    T elem;
public:
     Measurement() = default;
     Measurement(T elem):elem{elem}{
     }

     T value() const{
         return elem;
     }

     Measurement operator+ (T elemnou){
         elem+=elemnou;
         return *this;
     }

     bool operator<(const Measurement<T>& elemnou)const{
         return elem < elemnou.value();
     }

};
int main() {
//creaza un vector de masuratori cu valorile (10,2,3)
    std::vector<Measurement<int>> v{ 10,2,3 };
    v[2] + 3 + 2; //aduna la masuratoarea 3 valuarea 5
    std::sort(v.begin(), v.end()); //sorteaza masuratorile
//tipareste masuratorile (in acest caz: 2,8,10)
    for (const auto& m : v)
        std::cout << m.value() << ",";
    return 0;
}
