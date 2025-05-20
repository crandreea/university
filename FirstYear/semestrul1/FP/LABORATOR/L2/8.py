n = int(input())
rez = 0
counter_digits = dict()

while n:
    if n % 10 in counter_digits:
        counter_digits[n % 10] += 1
    else: counter_digits[n % 10] = 1
    n //= 10

for i in range(9, -1, -1):
    if i in counter_digits:
        while counter_digits[i]:
            rez = rez * 10 + i
            counter_digits[i] -= 1

print(rez)