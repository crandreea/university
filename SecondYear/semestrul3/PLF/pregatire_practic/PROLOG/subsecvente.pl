elimina_negative([], []).

elimina_negative([H|T], R) :-
    H < 0, 
    elimina_negative_skip(T, R).

elimina_negative([H|T], [H|R]) :-
    H >= 0, 
    elimina_negative(T, R).

elimina_negative_skip([], []).

elimina_negative_skip([H|T], R) :-
    H < 0,  
    elimina_negative_skip(T, R).

elimina_negative_skip([H|T], R) :-
    H >= 0, 
    elimina_negative([H|T], R).
