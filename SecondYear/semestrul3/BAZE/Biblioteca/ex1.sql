use Biblioteca

create table Autori(
    id_autor int identity (1,1) primary key not null,
    nume varchar(100),
    prenume varchar(100)
)


create table scrieri(
    id_autor int foreign key references Autori(id_autor),
    id_carte int foreign key references Carti(id_carte),
    constraint pk_scrieri primary key (id_autor, id_carte),
    descriere varchar(100)
)

create table Carti(
    id_carte int identity (1,1) primary key not null,
    titlu varchar(100),
    id_dom int,
    foreign key (id_dom) references domenii(id_dom)
)

create table librarii(
    id_lib int identity (1,1) primary key not null,
    nume varchar(100),
    adresa varchar(100)
)

create table domenii(
    id_dom int identity (1,1) primary key not null ,
    descriere varchar(50)
)

create or alter procedure BookToAuthor
    @nume varchar(100),
    @prenume varchar(100),
    @id_carte int
as
    begin
        if dbo.ValidateAuthor(@nume, @prenume) = 0
        begin
            INSERT INTO Autori VALUES (@nume, @prenume)
        end
        if exists(SELECT * FROM scrieri S
                  Where S.id_carte = @id_carte AND
                        S.id_autor IN (SELECT A.id_autor  FROM Autori A WHERE A.nume = @nume and A.prenume = @prenume))
        begin
            print 'Exista deja aceasta crate scrisa de acest autor'
        end

    end
go


create or alter function ValidateAuthor(@nume varchar(100), @prenume varchar(100))
returns bit
as
begin
    if not exists (SELECT * FROM Autori  Where Autori.nume = @nume and Autori.prenume = @prenume)
    begin
        return 0
    end
        return 1
end


create view VWCarti
as
    SELECT *
    FROM Carti C


go