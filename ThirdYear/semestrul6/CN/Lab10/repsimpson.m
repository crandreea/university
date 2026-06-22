function S = repsimpson(f,a,b,n)
  h = (b - a) / n;
  x = a : h : (b-h);
  m = (a+h/2) : h : (b-h/2);
  S = h/6 * (sum(f(x) + f(x+h)) + 4 * sum(f(m)));
