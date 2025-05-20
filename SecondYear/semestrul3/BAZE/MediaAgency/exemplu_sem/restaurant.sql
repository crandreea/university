use Dovleci
go

create table tiprestaurante(
    id_t int identity (1, 1) primary key ,
    nume varchar(100),
    descriere varchar(100)
)

create table restaurante(
    id_r int identity (1, 1) primary key,
    nume varchar(100),
    adresa varchar(100),
    telefon varchar(100),
    id_o int foreign key references orase(id_o),
    id_t int foreign key references tiprestaurante(id_t)
)

create table orase(
    id_o int identity (1, 1) primary key,
    nume varchar(100)
)

create table recenzii(
    id_u int foreign key references utilizatori(id_u),
    id_r int foreign key references restaurante(id_r),
    primary key (id_u, id_r),
    nota float
)

create table utilizatori(
    id_u int identity (1, 1) primary key,
    nume varchar(100),
    adresa varchar(100),
    parola varchar(100)
)
go

create or alter procedure RecenziiAdd(@restaurant int, @utilizator int, @nota float)as
    begin
        if exists (SELECT * FROM recenzii WHERE id_r = @restaurant and id_u = @utilizator)
        begin
            UPDATE recenzii
            SET nota = @nota
            WHERE id_r = @restaurant and id_u = @utilizator
        end
        else
        begin
            INSERT INTO recenzii values (@utilizator, @restaurant, @nota)
        end
    end
    go

create or alter function Function2( @email varchar(100))
returns table as
return
    SELECT t.nume as Tip_restaurant, Rest.nume as Nume_rest, Rest.telefon, R.nota, o.nume as Nume_oras, U.nume as Nume_utilizator
    FROM recenzii R
    JOIN utilizatori U on U.id_u = R.id_u
    JOIN restaurante Rest on Rest.id_r = R.id_r
    JOIN orase o on o.id_o = Rest.id_o
    JOIN tiprestaurante t on Rest.id_t = t.id_t
    WHERE U.adresa = @email


--trigger example
create trigger Insertion
    on recenzii
    instead of INSERT
    as
    begin
        --pot sa fac aktceva sau sa printez un mesaj ce se executa in loc de inserare
    end
    go

create trigger Elimination
    on recenzii
    after DELETE
    as
    begin
        --insert into new_table (...) select (...) from deleted
        --salveaza inregistrariile sterse in new_table
    end
    go

create trigger Actualizari
    on recenzii FOR UPDATE
    as
    begin
--         INSERT INTO modificari (...)
--         SELECT  (...)
--         FROM inserted i
--         INNER JOIN deleted d on i... = d....
        -- SALVEAZA MODIFICARILE CE AU AVUT LOC IN RECENZII
    end