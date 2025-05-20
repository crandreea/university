;;;Sa se construiasca o functie care intoarce adancimea unei liste


;;; CAZURI DE TESTARE
;; (defun test1 ()
;; (assert(equal (adancime_finala '(1 2 3)) 1 ) 
;; ))

;; (defun test2 ()
;; (assert(equal (adancime_finala '(1 (2 3))) 2 ) 
;; ))

;;; IMPEMENTARE

; maxim (a b) = a, a > b
;                b, b >= a

(defun maxim (a b)
(cond ((> a b) a)
      ( t b)
))
; adancime (l1...ln) = 0, n = 0
;                      max(adancime(l1) + 1, adancime(l2..ln)) , daca l1 este lista 
;                      adancime (l2..ln), altfel 

(defun adancime (L)
(cond ((NULL L) 0)
      ((LISTP (CAR L)) (maxim (+ 1 (adancime (CAR L))) (adancime (CDR L))))
      (t (adancime (CDR L)))
))

; adancime_finala(L) = 0, L vida
;                      1 + adancime(L), altfel

(defun adancime_finala (L)
(cond ((NULL L) 0) 
      (t (+ 1 (adancime L)))
))      

(print (adancime_finala '(1 (3 (3)))))
                    