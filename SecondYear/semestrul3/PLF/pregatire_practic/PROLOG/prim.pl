este_prim(N) :-
    N > 1,  
    nu_are_divizori(N, 2).

nu_are_divizori(N, I) :-
    I * I > N.

nu_are_divizori(N, I) :-
    N mod I =\= 0,
    I1 is I + 1,
    nu_are_divizori(N, I1). 

dubla_prime([], []).

dubla_prime([H|T], [H, H|R]) :-
    este_prim(H),!,
    dubla_prime(T, R).

dubla_prime([H|T], [H|R]) :-
     dubla_prime(T, R).
