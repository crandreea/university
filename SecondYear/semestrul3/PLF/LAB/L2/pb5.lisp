; Sa se intoarca adancimea la care se afla un nod intr-un arbore de tipul (1).

; stang(l1..ln) = parcurgere_stanga(l3..ln, 0, 0)
;; parcurgere_stanga (l1..ln, nrN, nrM) =  vida, n = 0
;;                                         vida, nrN = 1 + nrM
;;                                         l1 (+) l2 (+) parcurgere_stanga(l3..ln, nrN + 1, nrM + l2), altfel

; drept(l1..ln) = parcurgere_dreapta(l3..ln, 0, 0)
;; parcurgere_dreapta (l1..ln, nrN, nrM) = vida, n = 0
;;                                         l1..ln, nrN = 1 + nrM
;;                                         parcurgere_dreapta(l3..ln, nrN + 1, nrM + l2), altfel


;; adancime(l1..ln, nod, nivel) = -1, n = 0
;;                                nivel, nod = l1
;;                                -1, nodul nu exista in arbore (adancime(stang(l1..ln, nod, nivel+1) = -1 si adancime(drept(l1..ln, nod, nivel+1)) = -1 )
;;                                adancime(stang(l1..ln, nod, nivel+1)), adancime(drept(l1..ln, nod, nivel+1)0 = -1 (nodul nu se gaseste in subarborele drept)
;;                                adancime(drept(l1..ln, nod, nivel+1)), altfel 

;;final(L, nod) = adancime(L, nod, 0)
(defun parcurgere_stanga (L nrN nrM)
(cond ((NULL L) '())
      ((= nrN (+ 1 nrM)) '())
      (t (cons (CAR L)(cons (CADR L) (parcurgere_stanga (CDDR L) (+ 1 nrN) (+ (CADR L) nrM) ))))
))

(defun stang (L) (parcurgere_stanga (CDDR L) 0 0))

(defun parcurgere_dreapta (L nrN nrM)
  (cond ((null L) '()) 
        ((= nrN (+ 1 nrM)) L) 
        (t (parcurgere_dreapta (cddr L) (+ 1 nrN) (+ (cadr L) nrM))))) 

(defun drept (L)(parcurgere_dreapta (cddr L) 0 0)) 


(defun adancime (L nod nivel)
(cond ((NULL L) -1)
      ((equal (CAR L) nod) nivel)
      ((AND (= -1 (adancime (drept L) nod (+ 1 nivel))) (= -1 (adancime (stang L) nod (+ 1 nivel)))) -1)
      ((= -1 (adancime (drept L) nod (+ 1 nivel)))(adancime (stang L) nod (+ 1 nivel)))
      (t (adancime (drept L) nod (+ 1 nivel)))
))

(defun final (L nod) (adancime L nod 0))


(print (final '(A 2 B 2 D 1 G 0 E 2 H 1 L 2 M 0 N 0 I 0 C 1 F 2 J 0 K 1 Q 1 P 2 R 0 S 0) 'O))
