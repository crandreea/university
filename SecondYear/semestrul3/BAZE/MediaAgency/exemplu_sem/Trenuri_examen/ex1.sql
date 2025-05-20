use MediaAgency
go

create table Tipuri
(
    cod_t     int identity (1, 1) primary key,
    descriere varchar(100)
)

create table Trenuri
(
    cod_tr int identity (1, 1) primary key,
    nume   varchar(100),
    cod_t  int foreign key references Tipuri (cod_t) on update cascade on delete cascade
)

create table Rute
(
    cod_r  int identity (1, 1) primary key,
    cod_tr int foreign key references Trenuri (cod_tr) on update cascade on delete cascade,
    nume   varchar(100)
)

create table Statii
(
    cod_s int identity (1, 1) primary key,
    nume  varchar(100)
)

create table StatiiRute
(
    cod_r       int foreign key references Rute (cod_r) on update cascade on delete cascade,
    cod_s       int foreign key references Statii (cod_s) on update cascade on delete cascade,
    constraint pk_StatiiRute primary key (cod_r, cod_s),
    ora_sosire  datetime,
    ora_plecare datetime
)

-- Insert records into Tip table

INSERT INTO Tipuri (descriere) VALUES ('Rapid');

INSERT INTO Tipuri (descriere) VALUES ('Intercity');

INSERT INTO Tipuri (descriere) VALUES ('Personal');

INSERT INTO Tipuri (descriere) VALUES ('International');

INSERT INTO Tipuri (descriere) VALUES ('Expres');

-- Insert records into Trenuri table

INSERT INTO Trenuri (nume, cod_t) VALUES ('Train Alpha', 1);

INSERT INTO Trenuri (nume, cod_t) VALUES ('Train Beta', 2);

INSERT INTO Trenuri (nume, cod_t) VALUES ('Train Gamma', 3);

INSERT INTO Trenuri (nume, cod_t) VALUES ('Train Delta', 4);

INSERT INTO Trenuri (nume, cod_t) VALUES ('Train Epsilon', 5);

-- Insert records into Rute table

INSERT INTO Rute (nume, cod_tr) VALUES ('Route 101', 1);

INSERT INTO Rute (nume, cod_tr) VALUES ('Route 202', 2);

INSERT INTO Rute (nume, cod_tr) VALUES ('Route 303', 3);

INSERT INTO Rute (nume, cod_tr) VALUES ('Route 404', 4);

INSERT INTO Rute (nume, cod_tr) VALUES ('Route 505', 5);

-- Insert records into Statii table

INSERT INTO Statii (nume) VALUES ('Station A');

INSERT INTO Statii (nume) VALUES ('Station B');

INSERT INTO Statii (nume) VALUES ('Station C');

INSERT INTO Statii (nume) VALUES ('Station D');

INSERT INTO Statii (nume) VALUES ('Station E');

-- Insert records into StatiiRute table

INSERT INTO StatiiRute (cod_r, cod_s, ora_sosire, ora_plecare) VALUES (1, 1, '08:00:00', '08:15:00');

INSERT INTO StatiiRute (cod_r, cod_s, ora_sosire, ora_plecare) VALUES (1, 2, '08:45:00', '09:00:00');

INSERT INTO StatiiRute (cod_r, cod_s, ora_sosire, ora_plecare) VALUES (2, 3, '10:00:00', '10:15:00');

INSERT INTO StatiiRute (cod_r, cod_s, ora_sosire, ora_plecare) VALUES (2, 4, '10:45:00', '11:00:00');

INSERT INTO StatiiRute (cod_r, cod_s, ora_sosire, ora_plecare) VALUES (3, 5, '12:00:00', '12:15:00');



create or alter procedure ex2(
    @ruta int ,
    @statie int,
    @ora_sosire datetime,
    @ora_plecarii datetime
)as
     if exists (SELECT * FROM StatiiRute WHERE StatiiRute.cod_s = @statie AND StatiiRute.cod_r = @ruta)
        begin
            UPDATE StatiiRute
            SET ora_plecare = @ora_plecarii , ora_sosire = @ora_sosire
            WHERE cod_s = @statie AND cod_r = @ruta
        end

    else
        begin
            INSERT INTO StatiiRute(cod_r, cod_s, ora_sosire, ora_plecare)
            VALUES (@ruta, @statie, @ora_sosire, @ora_plecarii)
        end

go

exec ex2 3, 3, '1900-01-01 12:00:00.000', '1900-01-01 9:00:00.000'



create or alter view ex3
as
        select R.nume
        FROM Rute R
        WHERE ( SELECT COUNT(DISTINCT cod_s)  from Statii) = (SELECT COUNT(*) FROM StatiiRute SR WHERE SR.cod_r = R.cod_r)

go

select * from ex3



go
create view ex3_2
as
    select R.nume From Rute R
    INNER JOIN StatiiRute SR ON SR.cod_r = R.cod_r
    GROUP BY R.cod_r, R.nume HAVING COUNT(*) = (SELECT COUNT(*) from Statii)

select * from ex3_2


