clc
num2float(single(0))
num2float(-single(0))
fprintf('%.60f\n', myminsubnorm(single(1)))
num2float(myminsubnorm(single(1)))

num2float(realmin('single'))

num2float(single(1))
num2float(single(1)+eps('single'))
num2float(single(1)+2*eps('single'))
num2float(single(1)+3*eps('single'))

num2float(single(2)-eps('single'))
num2float(single(2)+2*eps('single'))
num2float(single(2))

num2float(single(2^30))
single(2^30) + 1 == single(2^30)
single(2^30) + 64 == single(2^30)
single(2^30) + 65 == single(2^30)
single(2^30) + 100 == single(2^30)

num2float(realmax('single'))
realmax('single')

num2float(Inf('single'))
2*Inf
1+Inf
1/0
1/-0

num2float(single(Inf/Inf))
Inf/Inf
0/0
