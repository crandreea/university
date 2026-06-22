function T = reptrapezium(f,a,b,n)
  h = (b-a)/n;
  % x = a : h :b;
  % T = h/2 * (2 * sum(f(x)) - f(a) - f(b));

  x = a : h : (b-h);
  T = h/2 * sum(f(x)+f(x+h));
