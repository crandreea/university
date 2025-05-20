%		    i          o
% minim_principal(L: list, Min: intreg)
%	L: lista de intregi
%	Min: valoarea minima

%	      i         i             o
% minim_aux(L:list, MinAux: intreg, Min: intreg)
%	L: lista de intregi
%	MinAux: valoarea minima pe parcursul listei
%	Min: valoarea minima finala

minim_principal(L, Min):-
	minim_aux(L, 1000, Min).

minim_aux([], MinAux, MinAux).
minim_aux([H|L], MinAux, Min):-
	H =< MinAux,
	minim_aux(L, H, Min).

minim_aux([H|L], MinAux, Min):-
	H > MinAux,
	minim_aux(L, MinAux, Min).

%			i     o
% pozitii_principal(L: list, R: list)
%	L: lista de intregi
%	R: lista pozitiilor unde se afla minimul din L

%		i           i           i          o
% pozitii_aux(L: list, Lung: intreg, Min: intreg, R: list)
%	L: liata de intregi
%	Lung: pozitia pe care ne aflam in lista
%	Min: valoarea minimului din L
%	R: lista cu pozitiile unde se afla miinimul in L

test:- pozitii_principal([1, 2, 1, 1], [1, 3, 4]).

pozitii_principal(L, R):-
	minim_principal(L, Min),
	pozitii_aux(L, 1, Min, R).

pozitii_aux([],_,_, []).

pozitii_aux([H|L], Lung, Min, [Lung|R]):-
	H = Min,
	Lung1 is Lung + 1,
	pozitii_aux(L, Lung1,Min,  R).

pozitii_aux([H|L], Lung, Min, R):-
        H \= Min, 
	Lung1 is Lung + 1,
	pozitii_aux(L, Lung1,Min, R).

