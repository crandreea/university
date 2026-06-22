n = 10
ones(n,1)
ones(n)
ones(1, n)
A = diag(5 * ones(n, 1), 0) + diag(-1 * ones(n-1, 1), 1) + diag(-1 * ones(n-1, 1), -1)

b = [4; 3*ones(n - 2, 1); 4]

NrIt = 10
xJ=Jacobi_it(A,b,NrIt)

xG=GS_it(A,b,NrIt)

omega=1.039
XSOR=SOR_it(A,b,omega,NrIt)

tril(A)
tril(A,-1)
diag(A)
diag(diag(A))


[x,ni,rho_J]=Jacobi(A,b,err=1e-15,p=Inf)
[x,ni,rho]=GS(A,b,err=1e-15,p=Inf)
omegaSOR = 2 / (1 + sqrt(1 - rho_J^2))
[x,ni,rho]=SOR(A,b,omegaSOR,err=1e-15,p=Inf)
