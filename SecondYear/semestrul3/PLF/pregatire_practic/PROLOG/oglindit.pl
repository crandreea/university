%	concatenare(a1..an, b1..bm) = [], n = 0 si m = 0
%				      [a1..an], n != 0 si m = 0
%				      [b1..bm], n = 0 si m != 0
%				      a1 (+) concatenare(a2..an, b1..bm), altfel
%
%			i       i       o
%	concatenare(A: list, B: list, R: list)
%	A: lista de intregi
%	B: lista de intregi
%	R: lista rezultata prin concatenarea listei A la lista B 

test_concat:-concatenare([1,2],[3,4],[1,2,3,4]).


concatenare([],L,L).

concatenare([L1|T], B, [L1|R]):-
	concatenare(T, B, R).



%	oglindit(a1..an) = [], n = 0
%			   concatenare(oglindit(a2..an), a1), altfel
%
%		    i       o
%	oglindit(A: list, R: list)
%	A: lista de intregi
%	R: lista oglindita lui A

test_ogl:-oglindit([1,2,3],[3,2,1]).


oglindit([],[]).

oglindit([L1|T], FinalR):-
	oglindit(T, R),
	concatenare(R, [L1], FinalR).
