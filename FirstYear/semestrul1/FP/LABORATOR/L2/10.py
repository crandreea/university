n = int(input())

counter_digits = dict()

rez = 0

while(n):
    if n % 10 in counter_digits: counter_digits[n % 10] += 1
    else: counter_digits[n % 10] = 1
    n //= 10

for i in range(1, 10):
    if i in counter_digits: 
        rez = i
        counter_digits[i] -= 1
        break

if 0 in counter_digits:
    while(counter_digits[0]): 
        rez *= 10
        counter_digits[0] -= 1

for i in range(1, 10):
    if i in counter_digits:
        while(counter_digits[i]):
            rez = rez * 10 + i
            counter_digits[i] -= 1

print(rez)


    
