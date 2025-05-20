
def function_pali(x):
    ogl = 0

    while x:    
        ogl = ogl * 10 + x % 10
        x //= 10

    return ogl

x = int(input())
print(function_pali(x))