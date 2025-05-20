% sterge_pe_pozitii(L, R)
% L - Lista initiala
% R - Lista rezultata
sterge_pe_pozitii(L, R) :-
    sterge_pozitii(L, 1, 1, R).

% sterge_pozitii(L, PozCurenta, NextPow, R)
% Sterge elementele de pe pozițiile 2^n - 1 în mod incremental
% L - Lista de intrare
% PozCurenta - Pozitia curenta în lista
% NextPow - Urmatoarea pozitie de forma 2^n - 1
% R - Lista rezultata


sterge_pozitii([], _, _, []). 

sterge_pozitii([_|T], PozCurenta, PozCurenta, R) :- 
    !,
    NextPow is PozCurenta * 2 + 1, 
    PozCurenta1 is PozCurenta + 1, 
    sterge_pozitii(T, PozCurenta1, NextPow, R).

sterge_pozitii([H|T], PozCurenta, NextPow, [H|R]) :-
    PozCurenta \= NextPow, 
    PozCurenta1 is PozCurenta + 1, 
    sterge_pozitii(T, PozCurenta1, NextPow, R).

