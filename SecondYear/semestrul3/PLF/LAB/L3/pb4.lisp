;; Sa se construiasca o functie care intoarce suma atomilor numerici  dintr-o lista, de la orice nivel. 

;; suma (L) =     0, n = 0
;;                L, L numar
;;                0, L atom nenumeric 
;;                suma(l1) + suma(l2) + ... + suma(ln), L = l1..ln - lista 

;; suma (L: list)
;; L - lista neliniara de atomi 

(defun suma (L)
(cond ((NULL L) 0)
      ((NUMBERP L) L)
      ((ATOM L) 0)
      (t (apply #'+ (mapcar #'suma L)))
))


(print (suma '(1 (3 1 1) 3 2)))
(print (suma '(A B (C))))