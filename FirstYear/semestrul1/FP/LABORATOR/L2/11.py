num1 = int(input())
num2 = int(input())

seen1 = set()
seen2 = set()

while(num1):
    seen1.add(num1 % 10)
    num1 //= 10

while(num2):
    seen2.add(num2 % 10)
    num2 //= 10

print(seen1 == seen2)