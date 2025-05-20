% Sa se genereze lista submultimilor cu N elemente, cu elementele unei liste date.
% Ex: [2,3,4] N=2 => [[2,3],[2,4],[3,4]]
%

%	comb(l1..ln, N) = 1. [l1], N = 1
%			  2. concatenare(l1, comb(l2..ln, N-1)), N > 1
%			  3. comb(l2..ln, N), N > 1
%
%		i        i         o        - nedeterminist
%	comb(L: list, N: intreg, R:list)
%	L: lista de intregi
%	N: numar intreg pozitiv <= n (nr de elemente din L)
%	R: lista de subliste a cate N elemente din L 


comb([L1|_], 1, [L1]).

comb([L1|T], N, [L1|R]):-
        N > 1,
        Nnou is N - 1,
        comb(T, Nnou, R).

comb([_|T], N, R):-
	N > 0 ,
        comb(T, N, R).

submultimi_findall(L, N, R):-
    findall(C, comb(L, N, C), R). 

%	           
%	submultimi_final(l1..ln, N) = submultimi(l1..ln, N, []).
%	
%     			      i      i         o      - determinist
%	submultimi_final(L: list, N: intreg, R: list)
%	L: lista de intregi
%       N: numar intreg pozitiv <= n (nr de elemente din L)
%       R: lista de subliste a cate N elemente din L
%
%	submultimi(l1...ln, N, Rez) = Rez , n = 0
%				      comb(l1..ln, N) (+) submultimi(l1..ln, N, Rez) , n != 0 si rezultatul comb(l1..ln, N) nu a mai fost adaugat in Rez
%				      submultimi(l2..ln, N, Rez) altfel
%
%
%			i       i          i         o     - determinist 
%	submultimi(L: list, N: intreg, Rez: list, R: list)
%	L: lista de numere intregi
%	N: numar intreg pozitiv <=n (nr de elem din L)
%	Rez: lista ce stocheaza rezultate intermediare
%	R: lista finala de subliste a cate n elem din L

%	apartenenta(E, l1 l2 .. ln) = false, daca n = 0
%      			              true, daca l1 = E
%	       		              member(E, l2 l3...ln), altfel
%
%                    i       i
%       apartenenta(E: elem, L:list)
%	 E - elementul de cautat in list
%	 L - lista in care cautam elementul E


apartenenta(E,[E|_]).

apartenenta(E,[_|T]):-
	apartenenta(E, T).

%CAZURI DE TESTARE:
test1:-submultimi_final([1,2,3],2,[[2,3],[1,3],[1,2]]).
test2:-submultimi_final([1, 2, 3, 4], 2, [[3, 4], [2, 4], [2, 3], [1, 4], [1, 3], [1, 2]]).
test3:-submultimi_final([1,2,3],1,[[3],[2],[1]]).

submultimi_final(L, N, R):-
	submultimi(L, N, [], R).

submultimi([], _, Ra, Ra).

submultimi([L|T], N, Ra, R):-
    comb([L|T], N, C),
    \+ apartenenta(C, Ra),!,
    submultimi([L|T], N, [C|Ra], R).

submultimi([_|T], N, Ra, R):-
    submultimi(T, N, Ra, R). 

