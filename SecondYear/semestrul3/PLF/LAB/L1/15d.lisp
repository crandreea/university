(defun max-lista (lst)
  (cond 
    ((null lst) nil) 
    ((atom (car lst)) (max (car lst) (max-lista (cdr lst))) )
    (t  (max (max-lista (car lst)) (max-lista (cdr lst))) )
    )
)
       
