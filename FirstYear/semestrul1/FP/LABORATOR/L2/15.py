n = int(input())

def function_prim(x):
    if x < 2:
        return False
    if x == 2:
        return True
    if x % 2 == 0:
        return False
    for d in range(3, x, 1):
        if x % d == 0:
            return False
    return True

def prim_smaller(x):
    while not function_prim(x-1):
        x -= 1
    return x-1

if n <= 2: 
    print("Nu exista")
else: 
    print(prim_smaller(n))
