clc
A = [10 7 8 7; 7 5 6 5; 8 6 10 9; 7 5 9 10]
b = [32 23 33 31]' # ' il pune pe coloana
y = A \ b # "\" rezolv sisteme liniare
bp = [32.1 22.9 33.1 30.9]'
yp = A \ bp

# eroarea relativa
er_rel_in = errel(b, bp)
er_rel_out = errel(y, yp)
er_rel_out/er_rel_in
cond(A) # p este default 2


# conditionarea matricei Vandermonde
for n = 10 : 15
  x = 1./(1:n); # obtinem un vector in care 1 este impartit la 1,2...n
  # punem ";" ca s anu se afiseze x urile
  cond(vander(x))
end

n = 15;
xi = 11;
condpol(poly(1:n),xi)

