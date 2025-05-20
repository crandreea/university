n = int(input())

def function_pb(n):
    if n == 1: return 1
    cnt = 1
    curr = 2

    while cnt != n :
        d = 2
        aux = curr

        while aux > 1:
            if d > aux: d = aux
            if aux % d == 0:
                while aux % d == 0: 
                    aux //= d

                if d == curr: cnt += 1
                else: cnt += d
            if cnt >= n : return d
            if d == 2: d = 3
            else: d += 2
            
        curr = curr + 1

print(function_pb(n))