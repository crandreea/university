;;;Definiti o functie care sorteaza fara pastrarea dublurilor o lista liniara

;;; CAZURI DE TESTARE

;; (defun test1 ()
;; (assert(equal (sortare_finala '(1 2 3 4)) '(1 2 3 4)) 
;; ))

;; (defun test2 ()
;; (assert(equal (sortare_finala '(1 4 3 3 2)) '(1 2 3 4)) 
;; ))

;; (defun test3 ()
;; (assert(equal (sortare_finala '(1 1 1)) '(1)) 
;; ))

;;; IMPLEMENTARE

; apartine(a1..an, e) = fals, n = 0
;                       true, n != 0 si a1 == e
;                       apartine(a2..an) altfel

(defun apartine (A e)
(cond ((NULL A) nil)
      ((equal (CAR A) e) t)
      (t (apartine (CDR A) e))
)
)

; eliminare_duplicate(l1..ln) = vide, n = 0
;                               l1 (+) eliminare_duplicate(l2..ln), l1 nu apartine de l2..ln
;                               eliminare_duplicate(l2..ln), altfel

(defun eliminare_duplicate (L)
(cond ((NULL L) NIL)
      ((apartine (CDR L) (CAR L)) (eliminare_duplicate (CDR L)))
      (t (cons (CAR L) (eliminare_duplicate (CDR L))))
)
)

; insereaza(el, l1..ln) = el, n = 0
;                         el (+) l1..ln, el < l1 
;                         l1 (+) insereaza(el, l2..ln), altfel 

(defun insereaza (el L)
(cond ((NULL L) (list el))
      ((< el (CAR L)) (cons el L))
      (t (cons (CAR L) (insereaza el (CDR L))))
)
)

; sortare (l1..ln) = vida, n = 0
;                    insereaza(l1, sortare(l2..ln)), altfel

(defun sortare (L)
(cond ((NULL L) NIL)
      (t (insereaza (CAR L) (sortare (CDR L))))
)
)

; sortare_finala(L) = sortare(eliminare_duplicate(L))

(defun sortare_finala (L) (sortare (eliminare_duplicate L)))

(print (sortare_finala '(1 4 3 4)))

;; (test1)
;; (test2)
;; (test3)