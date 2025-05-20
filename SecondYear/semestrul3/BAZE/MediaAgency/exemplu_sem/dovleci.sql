use Dovleci
go

create table participanti (
    id_p int identity (1, 1) primary key ,
    nume varchar(100),
    telefon varchar(100),
    adresa varchar(100)
)

insert into participanti values
                             ('ion', '073848594', 'cluj'),
                             ('pop', '3u913910', 'dej'),
                             ('maria', '0189982', 'adrs'),
                             ('adreea', '073632829', 'adrs2'),
                             ('eric', '01281389', 'adrs3')

-- many to many participanti-dovleci

create table decorari(
    id_p int,
    id_d int,
    foreign key (id_p) references participanti(id_p),
    foreign key (id_d) references dovleci(id_d),
    primary key (id_p, id_d),
    descriere varchar(100)
)


insert into decorari values
                         (1, 1, 'descriere'),
                         (1, 2, 'descriere2'),
                         (1, 3, 'decrie3'),
                         (2, 1, 'dee'),
                         (2, 2, 'dess'),
                         (2, 13, 'vvrrer'),
                         (3, 13, 'jbjsbvbk')

create table dovleci(
    id_d int identity (1, 1) primary key ,
    descriere varchar(100),
    data date,
    timp int,
    mentiune varchar(100)
)

alter table dovleci
add constraint ck_mentiune check (mentiune = 'DA' or mentiune = 'NU')
go


insert into dovleci values
                        ('descriere1', '2023-10-01', 10, 'DA'),
                        ('descriere2', '2023-10-05', 12, 'DA'),
                        ('descriere23', '2023-10-05', 12, 'DA'),
                        ('descriere3', '2023-10-05', 12, 'DA'),
                        ('descriere4', '2023-10-05', 12, 'DA'),
                        ('descriere6', '2023-10-05', 12, 'DA'),
                        ('descriere7', '2023-10-05', 12, 'DA'),
                        ('descriere8', '2023-10-05', 12, 'DA'),
                        ('descriere9', '2023-10-05', 12, 'DA'),
                        ('descriere10', '2023-10-05', 12, 'DA'),
                        ('descriere20', '2023-10-05', 12, 'NU'),
                        ('descriere21', '2023-10-05', 12, 'NU'),
                        ('descriere25', '2023-10-05', 12, 'NU'),
                        ('descriere22', '2023-10-05', 12, 'NU')

--one to many evaluator-dovleci

create table evaluatori(
    id_e int identity (1,1) primary key ,
    nume varchar(100),
    adresa varchar(100),
    data_nastere date,
    id_d int,
    foreign key (id_d) references dovleci(id_d),
    nota int check (nota >=1 and nota <=10)
)
go

create or alter procedure DescriereDecorare(
    @id_participant int,
    @id_dovleac int,
    @descriere varchar(100)
)as
    begin
        if exists (SELECT * FROM decorari WHERE id_d = @id_dovleac and id_p = @id_participant)
            begin
                UPDATE decorari
                SET descriere = @descriere
                WHERE id_d = @id_dovleac and id_p = @id_participant
            end
        else
            begin
                INSERT INTO decorari VALUES (@id_participant, @id_dovleac, @descriere)
            end
    end

    go

select * from decorari
exec DescriereDecorare 1, 1, 'descriere1'
exec DescriereDecorare 4, 13, 'descriere1'

create or alter view VW_Participanti
AS
    SELECT TOP 1 P.nume
    FROM participanti P
    JOIN decorari Dec on Dec.id_p = P.id_p
    JOIN dovleci Dov on Dov.id_d = Dec.id_d
    WHERE mentiune = 'DA'
    GROUP BY P.nume
    ORDER BY COUNT(Dov.id_d) DESC


select * from VW_Participanti