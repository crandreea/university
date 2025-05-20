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
def function_rez(x):
    while not function_prim(x):
        x += 1
    return x 
x = int(input())
print (function_rez(x+1))