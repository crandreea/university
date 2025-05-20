n = int(input())

def function_prim(x):
    if x < 2: return False
    if x == 2 or x == 3 : return True
    if x % 2 == 0 or x % 3 == 0 : return False
    for i in range(5, x, 2):
        if x%i == 0: return False
    return True

def function_rezolvare(n):
    if n % 2 == 0 and n > 2 :
        for i in range(2, n//2+1):
            if function_prim(i) == True and function_prim(n-i)==True: 
                return "p1 = {} si p2 = {}".format(i, n-i)
    return "Nu exista"

print(function_rezolvare(n))



