clc
format Long

f = @(x) cos(x)
w = @(x) exp(-x.^2)
n = 10
fw = @(x) f(x).*w(x)

I = gauss_quad_num('Hermite',f,n)
Q = quad(fw, -Inf, Inf) % calculeaza integrale in octave
