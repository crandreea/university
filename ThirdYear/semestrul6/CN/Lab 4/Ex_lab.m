clc

A = randi(10,5)
det(A)
tril(A) % mat triunghiulara
triu(A)

b = triu(A) * [1;1;1;1;1]
b = triu(A) * ones(5,1)
x = bkwsubs(triu(A), b)

b = tril(A) * ones(5,1)
x = fwdsubs(tril(A), b)

b = A * ones(5,1)
x = GaussElimPivot(A, b)

[L,U,P] = lup(A)
P*A
L*U

x = sol_sys_lup(A, b)
A = A'*A
x = randi(5,5,1)
x'*A*x
R = Cholesky(A)
R'*R
x = sol_sys_chol(A, b)
A*x
b
