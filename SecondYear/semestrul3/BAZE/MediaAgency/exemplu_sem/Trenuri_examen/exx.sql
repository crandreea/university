
--ac raspuns
select distinct a, b from R
select a, b from R group by a, b;

--ac rasp
SELECT DISTINCT R.B FROM R LEFT OUTER JOIN S1 ON R.B=S1.e
SELECT DISTINCT S1.e FROM R RIGHT OUTER JOIN S1 ON R.C=S1.d;

--ac raspuns
select distinct a from R where b > 0 ;
select a from R where b > 0 group by a;

--ac raspuns
select a from R r1 where exists (select * from R where a = r1.b);
select a from R where b = ANY (select a from R);
select a from R where b IN (select a from R);




--rasp diferite sau ac raspuns
select * from R2
select a, sum(b) as suma from R2 group by a;
select a, sum(b) as suma from R2 group by a having count(*)>1;
go

--rasp diferite
select * from R
select * from R order by b

--rasp diferite
select count(distinct b) from R;
select count(b) from R;

--rasp diferite
select * from R RIGHT join S on R.b = S.b;
select * from R LEFT JOIN S on R.b = S.b;

--rasp diferite
select * from R where b >= ALL(select d from S1 where e = 4);
select * from R where b >= ANY(select d from S1 where e = 4);

--rasp diferite
update A Set b = 10 where a = 20;
select * from A

delete from A where a = 20;
insert into A values(10, 20);




--q1 inclus in q2
(select * from R) union (select * from R);
select * from R;
go

--q1 inclus in q2
select distinct R3.a from R3,S2 WHERE R3.b = S2.b;
select R3.a from R3 where R3.b in (SELECT S2.b From S2);

--q1 inclus in q2
select * from R INNER JOIN S on R.b = S.b;
select * from R LEFT JOIN S on R.b = S.b;

--q1 inclus in q2
select * from R INNER JOIN S on R.b = S.b;
select * from R RIGHT JOIN S on R.b = S.b;

--q1 inclus in q2
select * from R INNER JOIN S on R.b = S.b;
select * from R FULL JOIN S on R.b = S.b;

--q1 inclus in q2
select a, max(b) as b from R group by a;
select a, b from R r1 where b>= all(select b from R r2 where r1.a = r2.a);




--q2 inclus in q1
select R.a from R, S where R.b = S.b;
select R.a from R where R.b in (SELECT S.b From S);

--q2 inclus in q1
select * from R FULL JOIN S on R.b = S.b;
select * from R LEFT JOIN S on R.b = S.b;



