;;;Sa se scrie o functie care intoarce intersectia a doua multimi

;;; CAZURI DE TESTARE
;; (defun test1 ()
;; (assert(equal (intersectie '(1 2 3 4) '(1 2 3 4)) '(1 2 3 4)) 
;; ))

;; (defun test2 ()
;; (assert(equal (intersectie '(1 2 3 4) '(1 1 1 1)) '(1)) 
;; ))


;;; IMPLEMENTARE

; apartine(a1..an, e) = fals, n = 0
;                       true, n != 0 si a1 == e
;                       apartine(a2..an) altfel

(defun apartine (A e)
(cond ((NULL A) NIL)
      ((equal (CAR A) e) t)
      (t (apartine (CDR A) e))
)
)

; intersectie(a1..an, b1..bm) = vida, n = 0 sau m = 0
;                               a1 (+) intersectie(a2..an, b1..bm), a1 apartine de B 
;                               intersectie(a2..an, b1..bm), altfel 

(defun intersectie (A B)
    (cond
        ((NULL A) '())
        ((apartine B (CAR A)) (cons (CAR A) (intersectie (CDR A) B)))
        (t (intersectie (CDR A) B))
    )
)

(print (intersectie '(2) '(2 6 5)))

