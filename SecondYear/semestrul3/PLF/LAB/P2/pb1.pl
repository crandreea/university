% a) Suma a doua numere sub forma de lista

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

%CAZURI DE TESTARE:

test_concat1:-concatenare([],[],[]).
test_concat2:-concatenare([],[1],[1]).
test_concat3:-concatenare([1,2],[3,4],[1,2,3,4]).


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

%CAZURI DE TESTARE:
test_ogl1:-oglindit([],[]).
test_ogl2:-oglindit([1],[1]).
test_ogl3:-oglindit([1,2,3],[3,2,1]).


oglindit([],[]).

oglindit([L1|T], FinalR):-
	oglindit(T, R),
	concatenare(R, [L1], FinalR).



%	suma_transport(a1..an, b1..bm, T) = [], n = 0 si m = 0 si T = 0
%					    [T], n = 0 si m = 0 si T != 0
%					    [(a1+T)%10] (+) suma_transport(a2..an, [], (a1+T)/10), n !=0 si m = 0 
%					    [(b1+T)%10] (+) suma_transport([], b2..bm, (b1+T)/10), n = 0 si m != 0
%					    [(a1+b1+T)%10] (+) suma_transport(a2..an, b2..bm, (a1+b1+T)/10), n != 0 si m != 0
%
%			i        i        i        o
%	suma_transport(A:list, B:list, T:intreg, R:list)
%	A: lista de intregi
%	B: lista de intregi
%	T: variabila intreaga ce reprezinta transportul in urma sumei dintre eleme din A cu cele din B {0,1}
%	R: lista obtinuta prin insumarea liste A la lista B 


suma_transport([],[],0,[]).

suma_transport([],[],Tr,[Tr]):-
	Tr \= 0.

suma_transport([A1|T],[],Tr,[S|R]):-
	Suma is A1 + Tr,
	S is Suma mod 10,
	Tr_nou is Suma // 10,
	suma_transport(T,[],Tr_nou,R).

suma_transport([],[B1|T],Tr,[S|R]):-
        Suma is B1 + Tr,
        S is Suma mod 10,
        Tr_nou is Suma // 10,
        suma_transport([],T,Tr_nou,R).

suma_transport([A1|Ta],[B1|Tb],Tr,[S|R]):-
        Suma is A1 + B1 + Tr,
        S is Suma mod 10,
        Tr_nou is Suma // 10,
        suma_transport(Ta,Tb,Tr_nou,R).


%	suma_principal(a1..an, b1..bn) = oglindit( suma_transport(oglindit(a1..an), oglindit(b1..bn), 0)).
%
%			i        i        o
%	suma_principal(A: list, B: list, R: list)

%CAZURI DE TESTARE:
test_suma1:-suma_principal([1,2,3], [4,5,6], [5,7,9]).
test_suma2:-suma_principal([1,2,3], [1,2], [1,3,5]).
test_suma3:-suma_principal([1,2,3], [7], [1,3,0]).
test_suma4:-suma_principal([9,9], [1], [1,0,0]).


suma_principal(A, B, Final):-
	oglindit(A, A_r),
	oglindit(B, B_r),
	suma_transport(A_r, B_r, 0, R),
	oglindit(R, Final).



%b) Se da o lista eterogena, formata din numere intregi si liste de cifre. 
%	Sa se calculeze suma tuturor numerelor reprezentate de subliste. 
%	De ex: [1, [2, 3], 4, 5, [6, 7, 9], 10, 11, [1, 2, 0], 6] => [8, 2, 2]. 

%	suma_liste(l1..ln, sum) = sum, n = 0
%				  suma_liste(l2..ln, suma_principal(l1, sum)), n !=0 si l1 este lista
%			          suma_liste(l2..ln, sum), altfel
%
%	suma_liste(l1..ln) = suma_liste(l1..ln, [])
%
%		     i         i
% 	suma_liste(L: list, sum: list)
%	L: lista eterogena de numere intregi si liste de numere intregi
%	sum: lista obtinuta prin adunarea a cate doua subliste din L


%CAZURI DE TESTARE:
test_sum_list1:-suma_liste([],[]).
test_sum_list2:-suma_liste([[1,2]],[1,2]).
test_sum_list3:-suma_liste([[1,2],[2,3],[9]],[4, 4]).
test_sum_list4:-suma_liste([1,[3],9,4,[9,9]],[1,0,2]).

suma_liste(L, R):-
	suma_liste(L, [], R).

suma_liste([], Sum, Sum).

suma_liste([L1|T],Sum,R):-
	is_list(L1),!,
	suma_principal(L1, Sum, RezSum),
	suma_liste(T, RezSum, R).

suma_liste([_|T], Suma, R):-
	suma_liste(T, Suma, R).


