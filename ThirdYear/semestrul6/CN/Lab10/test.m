clc
f = @(x) 4./(1+x.^2)

pkg load symbolic
syms x
int(f(x),x,0,1)
format long
R = reprectangule(f,0,1,100)
T = reptrapezium(f,0,1,100)
S = repsimpson(f,0,1,100)
Q = adquad2(f,0,1)
P = pi

##syms a b fa fb fm
##m = (a+b)/2
##L = (x - m) * (x - b) / ((a - m) * (a - b))*fa...
##    + (x - a) * (x - b) / ((m - a) * (m - b))*fm...
##    + (x - m) * (x - a) / ((b - m) * (b - a))*fb
##
##Simpson = factor(simplify(int(L, x, a, b)))

clf
fplot(f,[0,1])
axis([0 1 0 4])
T = romberg(f,0,1,4)

