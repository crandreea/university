create table Tipuri(
    cod_tip int identity(1,1) primary key,
    nume varchar(200),
    descriere varchar(200)
);

create table Restaurante(
    cod_r int identity(1,1) primary key,
    nume varchar(200),
    adresa varchar(200),
    nr_tel varchar(100),
    cod_t int foreign key references Tipuri(cod_t)
)