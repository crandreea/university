clf;axis equal;axis([0 2 0 1]);
xticks(0:0.2:2);yticks(0:0.2:1);
grid on;hold on;
 [x,y]=ginput(1);
 X=x;Y=y;
 i=1;
 while ~isempty([x,y])
   plot(x,y,'*k','MarkerSize',10);
   text(x+0.02,y+0.02,num2str(i),'fontsize',25);
   [x,y]=ginput(1);
   i=i+1;
   X=[X,x];Y=[Y,y];
 endwhile
X
Y
timpi = linspace(0,1,length(X));
t = linspace(0,1,1000);
sX = evalsplinecubic(timpi, X, 'deBoor',[],t);
sY = evalsplinecubic(timpi, Y, 'deBoor',[],t);

plot(sX, sY, '-b', 'Linewidth', 1.5)
