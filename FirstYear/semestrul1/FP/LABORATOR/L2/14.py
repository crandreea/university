n = int(input())

def perfect_number(x):
    sum = 1
    for i in range(2, x):
        if x % i == 0: 
            sum +=  i
    if sum == x: return True
    else: return False

while not perfect_number(n+1):
    n += 1

print(n+1)


