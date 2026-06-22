  function mcmmp_app(k=3)
    clf; axis equal;axis([0 2 0 1]);
    xticks(0:0.2:2);yticks(0:0.2:1);
    grid on;hold on; set(gca,"fontsize", 15)

    [x,y]=ginput(1);
    X = []; Y = [];
    while ~isempty([x,y])
     X = [X x]; Y = [Y y];
     plot(x,y,'*k','MarkerSize',10);
     [x,y]=ginput(1);
    endwhile
    X
    Y
    [coefs,err]=least_sq(X,Y,k)
    fplot(@(x) polyval(coefs,x),[0,2])
    legend("off")
  endfunction
