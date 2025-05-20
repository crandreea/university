n = int(input())

def function_prim(x):
    if x < 2 : return False
    if x == 2 or x == 3: return True
    if x % 2 == 0 or x % 3 == 0 : return False
    for i in range(5, n+1, 2):
        if n % i == 0 : return False
    return True

while True:
    if function_prim(n+1) and function_prim(n+3):
        print([n+1, n+3])
        break
    else: n += 1
    


# q -  p = 2    p = q - 2    p < q 