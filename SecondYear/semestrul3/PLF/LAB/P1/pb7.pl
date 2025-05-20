 % a)  Sa se scrie un predicat care intoarce reuniunea a doua multimi. 
% modelul matematic:
% 
%  reuniune(a1 a2 a3 ... an, b1 b2 b3 ... bm) = vida, daca n = 0 si m = 0
%  					       [a1 a2...an], daca n!=0 si m = 0
%					       [b1 b2...bm], daca n = 0 si m!=0
%					       b1 (+) reuniune(a1 a2..an, b2..bm), daca n!=0 si m!=0 si b1 !e A
%					       reuniune(a1 a2..an, b2 b3..bm), altfel
%
%             i       i       o
%  reuniune(A:list, B:list, R:list)
%  A - o multime de n elemente
%  B - o multime de m elemente
%  R - multimea obtinuta prin reuniunea multimii A si a multimii B 
%
%  apartenenta(E, l1 l2 .. ln) = false, daca n = 0
%      			         true, daca l1 = E
%			         member(E, l2 l3...ln), altfel
%
%                 i       i
%  apartenenta(E: elem, L:list)
%	E - elementuyl de cautat in list
%	L - lista in care cautam elementul E


apartenenta(E,[E|_]).

apartenenta(E,[_|T]):-
	apartenenta(E, T).

%reuniune([], [], []).

reuniune(A, [], A).

reuniune(A, [B1|T], [B1|R]):-
	\+ apartenenta(B1, A),
	reuniune(A, T, R).

reuniune(A, [B1|T], R):-
	apartenenta(B1, A),
	reuniune(A, T, R).


% b) Sa se scrie un predicat care, primind o lista, intoarce multimea tuturor perechilor din lista. De ex, cu 
%    [a, b, c, d] va produce [[a, b], [a, c], [a, d], [b, c], [b, d], [c, d]].
%
%   perechi(l1 l2 .. ln) = vida, daca n = 0
%			   vida, daca n = 1
%		           formare_perechi(l1, [l2 l3..ln]) (+) perechi(l2,l3..ln), altfel
%
%
%   formare_perechi(l1, l2 l3..ln) = vida, daca n = 0
%                                  = [l1,l2] (+) formare_perechi(l1, l3 l4..ln), altfel
%
%
%	       i       o
%   perechi(L: list, R: list)
%	L - o lista
%       R - multimea tuturor perechilor din lista L
%
%	              i       i        o
%   formare_perechi(E:elem, L: list, R: list)
%	E - un element
%	L - o lista
%	R - multimea tuturor perechilor formate din elementul E si elementele listei L
%
%
%   concatenare(l1 l2...ln, b1 b2 ...bm) = vida, daca n = 0 si m = 0
%					   [l1 l2...ln], daca m = 0 si n != 0
%					   [b1 b2...bm], daca n = 0 si m != 0
%					   l1 (+) concatenare(l2 l3...ln, b1 b2 ... bm), altfel
%
%		   i         i         o
%   concatenare(L1: list, L2: list, R: rezultat)


%concatenare([],[],[]).

concatenare([],L,L).

%concatenare(L, [], L).

concatenare([L1|T], B, [L1|R]):-
	concatenare(T, B, R).

formare_perechi(_,[],[]).

formare_perechi(E, [L2|T], [[E,L2]|R]):-
	formare_perechi(E, T, R).

perechi([],[]).

%perechi([_],[]).

perechi([L1|T],R):-
	formare_perechi(L1, T, R1),
	perechi(T, R2),
	concatenare(R1, R2, R).
		
