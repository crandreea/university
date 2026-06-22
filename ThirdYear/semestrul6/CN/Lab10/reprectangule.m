function R = reprectangule(f,a,b,n)
  h = (b-a)/n; %lungimea unui subinterval
  m = (a+h/2) : h : (b-h/2); %mijloacele
  %f(m) %inaltimile dreptunghiurilor
  R = h * sum(f(m));
