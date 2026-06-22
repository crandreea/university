function x=GaussElimPivot(A,b)
  A=[A b];
  for k=1:length(b)-1
    [maxval,maxpos]=max(abs(A(k:end,k)));
    pivpos=maxpos+k-1;
    if maxval>0&&pivpos~=k
      A([k pivpos],k:end)=A([pivpos k],k:end);
    elseif maxval==0
      disp('No unique solution!');
    endif
    for i=k+1:length(b)
      A(i,k:end)=A(i,k:end)-(A(i,k)/A(k,k))*A(k,k:end);
    endfor
  endfor
  x=bkwsubs(A(:,1:end-1),A(:,end));
endfunction
