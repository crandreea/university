;;;Definiti o functie care intoarce produsul a doi vectori

;;; CAZURI DE TESTARE
;; (defun test1 ()
;; (assert(equal (produs_vectori_scalar '(1 2 3) '(1 2 3)) 14 ) 
;; ))

;; (defun test2 ()
;; (assert(equal (produs_vectori_pozitii '(1 2 3) '(1 2 3)) '(1 4 9)) 
;; ))

;;; IMPLEMENTARE

; produs_vectori_scalar(v1..vn, z1..zm) = 0, n = 0 si m = 0
;                                  (v1 * z1) + produs_vectori_scalar(v2..vn, z2..zm), altfel

(defun produs_vectori_scalar (V1 V2)
(cond ((OR (NULL V1) (NULL V2)) 0)
      ((NUMBERP (CAR V1)) (+ (* (CAR V1) (CAR V2)) (produs_vectori_scalar (CDR V1) (CDR V2))))
      (t (produs_vectori_scalar (CDR V1) (CDR V2)))
))

(print (produs_vectori_scalar '(5 b 3) '(2 d 3)))

; produs_vectori_pozitii(v1..vn, z1..zm) = '(), n = 0 si m = 0
;                                          (v1 * z1) (+) produs_vectori_pozitii(v2..vn, z2..zm)

(defun produs_vectori_pozitii (V1 V2)
(cond ((OR (NULL V1) (NULL V2)) '())
      (t (cons (* (CAR V1) (CAR V2)) (produs_vectori_pozitii (CDR V1) (CDR V2))))
))

;(print (produs_vectori_pozitii '(1 5 3) '(1 2 3)))

;; (test1)
;; (test2)