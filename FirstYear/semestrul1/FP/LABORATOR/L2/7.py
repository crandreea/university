x = int(input())

def fact_proprii(x):
    p = 1
    if x == 0: return 0
    for d in range(2, x, 1):
        if x % d == 0:
            p *= d

    return p

print(fact_proprii(x))
