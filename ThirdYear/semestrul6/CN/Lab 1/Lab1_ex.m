octave:1> pkg load symbolic
octave:2> sym x
Symbolic pkg v3.2.1: Python communication link active, SymPy v1.13.3.
ans = (sym) x
octave:3> taylor(exp(X),x,0,'order',10)
error: 'X' undefined near line 1, column 12
octave:4> taylor(exp(x),x,0,'order',10)
error: 'x' undefined near line 1, column 12
octave:5> syms x
octave:6> taylor(exp(x),x,0,'order',10)
ans = (sym)

     9       8       7     6     5     4    3    2        
    x       x       x     x     x     x    x    x         
  ────── + ───── + ──── + ─── + ─── + ── + ── + ── + x + 1
  362880   40320   5040   720   120   24   6    2         

octave:7> taylor(exp(x),x,'expansionpoint',1,'order',10)
ans = (sym)

           9            8            7            6            5            4    ↪
  ℯ⋅(x - 1)    ℯ⋅(x - 1)    ℯ⋅(x - 1)    ℯ⋅(x - 1)    ℯ⋅(x - 1)    ℯ⋅(x - 1)     ↪
  ────────── + ────────── + ────────── + ────────── + ────────── + ────────── +  ↪
    362880       40320         5040         720          120           24        ↪
  
  ↪          3            2                
  ↪ ℯ⋅(x - 1)    ℯ⋅(x - 1)                 
  ↪ ────────── + ────────── + ℯ⋅(x - 1) + ℯ
  ↪     6            2                     

octave:8> T_sin_0=taylor(sin(x))
T_sin_0 = (sym)

   5     3    
  x     x     
  ─── - ── + x
  120   6     

octave:9> T_sin_0=taylor(sin(x),x,'order',10)
T_sin_0 = (sym)

     9       7     5     3    
    x       x     x     x     
  ────── - ──── + ─── - ── + x
  362880   5040   120   6     

octave:10> T_sin_0=taylor(sin(x),x,'order',11)
T_sin_0 = (sym)

     9       7     5     3    
    x       x     x     x     
  ────── - ──── + ─── - ── + x
  362880   5040   120   6     

octave:11> diff(sin(x))
ans = (sym) cos(x)
octave:12> diff(sin(x),2)
ans = (sym) -sin(x)
octave:13> subs(diff(sin(x),2),0)
ans = (sym) 0
octave:14> D_sin_0(x)=diff(sin(x),2)
D_sin_0(x) = (symfun) -sin(x)
octave:15> D_sin_0(0)
ans = (sym) 0
octave:16> taylor(log(x+1),x,0,'order',10)
ans = (sym)

   9    8    7    6    5    4    3    2    
  x    x    x    x    x    x    x    x     
  ── - ── + ── - ── + ── - ── + ── - ── + x
  9    8    7    6    5    4    3    2     

octave:17> taylor(1/(x+1),x,0,'order',10)
ans = (sym)

     9    8    7    6    5    4    3    2        
  - x  + x  - x  + x  - x  + x  - x  + x  - x + 1

octave:18> taylor(1/(1-x),x,0,'order',10)
ans = (sym)

   9    8    7    6    5    4    3    2        
  x  + x  + x  + x  + x  + x  + x  + x  + x + 1

octave:19> taylor((1+x)^10,x,0,'order',10)
ans = (sym)

      9       8        7        6        5        4        3       2           
  10⋅x  + 45⋅x  + 120⋅x  + 210⋅x  + 252⋅x  + 210⋅x  + 120⋅x  + 45⋅x  + 10⋅x + 1

octave:20> expand((1+x)^10)
ans = (sym)

   10       9       8        7        6        5        4        3       2       ↪
  x   + 10⋅x  + 45⋅x  + 120⋅x  + 210⋅x  + 252⋅x  + 210⋅x  + 120⋅x  + 45⋅x  + 10⋅ ↪
  
  ↪      
  ↪ x + 1

octave:21> taylor((1+x)^(7/4),x,0,'order',10)
warning: passing floating-point values to sym is dangerous, see "help sym"
warning: called from
    double_to_sym_heuristic at line 50 column 7
    sym at line 384 column 13
    mpower at line 70 column 5

ans = (sym)

           9          8        7        6       5       4      3       2          
    38675⋅x    13923⋅x    663⋅x    273⋅x    63⋅x    35⋅x    7⋅x    21⋅x    7⋅x    
  - ──────── + ──────── - ────── + ────── - ───── + ───── - ──── + ───── + ─── + 1
    33554432   8388608    262144   65536    8192    2048    128     32      4     

octave:22> taylor((1+x)^(sym(7)/4),x,0,'order',10)
ans = (sym)

           9          8        7        6       5       4      3       2          
    38675⋅x    13923⋅x    663⋅x    273⋅x    63⋅x    35⋅x    7⋅x    21⋅x    7⋅x    
  - ──────── + ──────── - ────── + ────── - ───── + ───── - ──── + ───── + ─── + 1
    33554432   8388608    262144   65536    8192    2048    128     32      4     

octave:23> taylor(sqrt(1+x),x,0,'order',10)
ans = (sym)

       9        8       7       6      5      4    3    2        
  715⋅x    429⋅x    33⋅x    21⋅x    7⋅x    5⋅x    x    x    x    
  ────── - ────── + ───── - ───── + ──── - ──── + ── - ── + ─ + 1
  65536    32768    2048    1024    256    128    16   8    2    

octave:24> syms x a
octave:25> syms a real positive
octave:26> f = sqrt(a+x)
f = (sym)

    _______
  ╲╱ a + x 

octave:27> taylor(f,x,0,'order',10)
ans = (sym)

                 2        3           4          5           6            7      ↪
        x       x        x         5⋅x        7⋅x        21⋅x         33⋅x       ↪
  √a + ──── - ────── + ─────── - ──────── + ──────── - ────────── + ────────── - ↪
       2⋅√a      3/2       5/2        7/2        9/2         11/2         13/2   ↪
              8⋅a      16⋅a      128⋅a      256⋅a      1024⋅a       2048⋅a       ↪
  
  ↪         8             9   
  ↪    429⋅x         715⋅x    
  ↪  ─────────── + ───────────
  ↪         15/2          17/2
  ↪  32768⋅a       65536⋅a