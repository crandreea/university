use Dovleci
go

--13:40
create table producatori(
    id_p int identity (1, 1) primary key,
    nume varchar(100),
    site varchar(100)
)

insert into producatori values
                            ('prod1', 'hjshjvb'),
                            ('prod2', 'hjshjvb'),
                            ('prod3', 'hjshjvb'),
                            ('prod4', 'hjshjvb'),
                            ('prod5', 'hjshjvb')

create table tipuri(
    id_t int identity (1, 1) primary key ,
    nume varchar(100)
)

insert into tipuri values ('tip1'), ('tip2'),('tip3'),('tip4'),('tip5')

--one to many desert-producator
--one to many desert-tip
create table deserturi(
    id_d int identity (1, 1) primary key,
    nume varchar(100),
    mod varchar(100),
    pret float,
    calorii int,
    id_p int foreign key references producatori(id_p),
    id_t int foreign key references tipuri(id_t)
)

insert into deserturi values
                          ('amandine', 'fnfne', 12, 1234, 1, 1),
                          ('aman', 'fnfne', 12, 12634, 1, 2),
                        ('amaine', 'fnfne', 12, 127, 2, 1),
                      ('amine', 'fnfne', 12, 12384, 3, 3),
                      ('aandine', 'fnfne', 12, 123400, 4, 5),
                      ('amaine', 'fnfne', 12, 10234, 5, 1)


create table achizitii(
    id_c int foreign key references clienti(id_c),
    id_d int foreign key references deserturi(id_d),
    primary key (id_c, id_d),
    cantitate int
)

insert into achizitii values
                          (1, 1, 10),
                          (1, 2, 1),
                          (2,2, 3),
                          (3, 4, 90),
                          (4, 1, 23),
                          (6, 1, 23)

create table clienti(
    id_c int identity (1, 1) primary key,
    nume varchar(100),
    adresa varchar(100),
    data date
)

insert into clienti values ('ion', 'vjbvjkrt', '2023-01-01'),
                        ('Aon', 'vjbvjkrt', '2023-01-01'),
('ANaon', 'vjbvjkrt', '2023-01-01'),
                    ('mon', 'vjbvjkrt', '2023-01-01'),
                        ('son', 'vjbvjkrt', '2023-01-01'),
                        ('AAAAon', 'vjbvjkrt', '2023-01-01')
go

create or alter procedure Achizitionare(
    @id_desert int,
    @id_client int,
    @cantitate int
)as
    begin
        if exists(SELECT * FROM achizitii WHERE id_d = @id_desert AND id_c = @id_client)
        begin
            UPDATE achizitii
            SET cantitate = @cantitate
            WHERE id_d = @id_desert AND id_c = @id_client
        end
        else
        begin
            INSERT INTO achizitii values (@id_client, @id_desert, @cantitate)
        end
    end

go

create or alter function Function1()
returns table as return
    SELECT p.nume as prod_nume, d.nume as desert_nume, d.calorii, A.cantitate, c.nume as client_nume
    FROM achizitii A
    JOIN deserturi d on A.id_d = d.id_d
    JOIN producatori p on p.id_p = d.id_p
    JOIN clienti c on A.id_c = c.id_c
    WHERE c.nume LIKE 'A%'

select * from Function1()