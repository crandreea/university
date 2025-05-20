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
