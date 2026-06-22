clc
pkg load symbolic
syms x
a = -1
b = 1
w = 1/sqrt(1-x^2)
wab = simplify((x-a)*(b-x)*w)

n = 4
pi2 = orto_poly_sym_type('Cebisev2', n-2)
nodes = [a, solve(pi2,x)', b]
coef = gauss_coefs_sym(w, a, b, nodes)

rest_fara_f = int(pi2^2 * wab, x, a, b) / factorial(2*n-2)

I = double(exp(nodes)*coef')
err = pi/23040*e

Q = quad(@(x) exp(x)./sqrt(1-x.^2), -1, 1)
