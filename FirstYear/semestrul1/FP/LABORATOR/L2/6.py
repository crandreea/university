n = int(input())

fib_0 = 1
fib_1 = 1

while True:
    fib_2 = fib_0 + fib_1
    if (fib_2) > n: 
        print(fib_2)
        break
    else:
        fib_0 = fib_1
        fib_1 = fib_2


