%
%   concatenare(l1 l2...ln, b1 b2 ...bm) = vida, daca n = 0 si m = 0
%					   [l1 l2...ln], daca m = 0 si n != 0
%					   [b1 b2...bm], daca n = 0 si m != 0
%					   l1 (+) concatenare(l2 l3...ln, b1 b2 ... bm), altfel
%
%		   i         i         o
%   concatenare(L1: list, L2: list, R: rezultat)

concatenare([],L,L).

concatenare([L1|T], B, [L1|R]):-
	concatenare(T, B, R).
