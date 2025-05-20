sterge_max(L, R) :-
    max_in_list(L, Max),  
    elimina_element(L, Max, R).

max_in_list([H|T], Max) :-
    max_in_list_aux(T, H, Max).

max_in_list_aux([], Max, Max).

max_in_list_aux([H|T], MaxCurent, Max) :-
    H > MaxCurent, 
    max_in_list_aux(T, H, Max).

max_in_list_aux([H|T], MaxCurent, Max) :-
    H =< MaxCurent, 
    max_in_list_aux(T, MaxCurent, Max).

elimina_element([], _, []).

elimina_element([Elem|T], Elem, R) :- 
    elimina_element(T, Elem, R).

elimina_element([H|T], Elem, [H|R]) :-
    H \= Elem, 
    elimina_element(T, Elem, R).
