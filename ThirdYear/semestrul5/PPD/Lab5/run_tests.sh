#!/bin/bash

# Script pentru testarea automată a programului paralel
# Rezultatele vor fi scrise în rezultate_timpi.txt

OUTPUT="rezultate_timpi.txt"
PROGRAM="./program"

echo "=== Rulare teste paralelizare ===" > $OUTPUT
echo "" >> $OUTPUT

# Compileaza in Release mode
echo "Compiling in Release mode..."
g++ -std=c++20 -O3 -march=native -pthread -lsqlite3 LinkedList.cpp SortedList.cpp Queue.cpp ThreadPool.cpp paralel.cpp -o program
# g++ -std=c++20 -O3 -march=native paralel.cpp -o program
if [ $? -ne 0 ]; then
    echo "Eroare la compilare!"
    exit 1
fi

# Functie pentru a rula un test
run_test() {
    local p=$1
    local pr=$2

    echo "Test p=$p, p_r=$pr"

    # ruleaza programul cu argumente
    RESULT=$($PROGRAM $p $pr | grep "Timpul")
    
    # scrie in fisier
    echo "p = $p,  p_r = $pr  -->  $RESULT" >> $OUTPUT
}

# Cazurile cerute
for p in 6 8 12; do
    run_test $p 4
done

echo "" >> $OUTPUT

echo "Testele au fost rulate cu succes! Vezi rezultate_timpi.txt"
