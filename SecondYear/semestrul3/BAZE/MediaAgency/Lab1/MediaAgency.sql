use MediaAgency;

create table Departamente(
    id_departament int primary key identity ,
    denumire varchar(100),
    coordonator varchar(100),
    numar_persoane integer
)

--one to many
create table Angajati(
    id_angajat INT PRIMARY KEY IDENTITY ,
    nume varchar(100),
    prenume varchar(100),
    email varchar(100),
    id_departament int foreign key references Departamente(id_departament) on update cascade on delete no action ,
    pozitie varchar(100)
)

--many to many
create table Proiecte(
    id_departament int foreign key references Departamente(id_departament),
    id_campanie int foreign key references Campanii(id_campanie),
    constraint pk_Proiecte primary key (id_departament, id_campanie),
    deadline datetime,
    descriere varchar(100)
)

create table Clienti(
    id_client int primary key identity ,
    nume_firma varchar(100),
    persoana_contact varchar(100),
    email varchar(100)
)

--one to one
create table Contracte_Clienti(
    id_contract_clienti int primary key,
    id_client int foreign key references Clienti(id_client) on update cascade on delete cascade ,
    data_semnare date,
    data_expirare date,
    valoare int
)

--one to many
create table Facturare_Clienti(
    id_facturare int primary key ,
    id_contract int foreign key references Contracte_Clienti(id_contract_clienti),
    data_emitere date,
    data_scadenta date,
    suma int,
    status varchar(100)
)

--one to one
create table Campanii(
    id_campanie int primary key identity ,
    id_client int foreign key references Clienti(id_client) on update cascade on delete cascade ,
    nume_campanie varchar(100),
    data_inceput date,
    data_sfarsit date,
    buget_alocat int
)

create table Colaboratori(
    id_colaborator int primary key identity ,
    nume varchar(100),
    tip varchar(100),
    contact varchar(100)
)

--one to many
create table Contracte_Colaboratori(
    id_contract_colaboratori int primary key,
    id_colaborator int foreign key references Colaboratori(id_colaborator) on update cascade on delete cascade ,
    data_semnare date,
    data_expirare date,
    valoare int
)

--one to many
create table Facturare_Colaborator(
    id_facturare int primary key ,
    id_contract int foreign key references Contracte_Colaboratori(id_contract_colaboratori) on update cascade on delete cascade ,
    data_emitere date,
    data_scadenta date,
    suma int,
    status varchar(100)
)

--many to many
create table Colaborare(
    id_campanie int foreign key REFERENCES Campanii(id_campanie),
    id_colaborator int foreign key references Colaboratori(id_colaborator),
    data_inceput date,
    data_sfarsit date,
    descriere varchar(100),
    constraint pk_Colaborare primary key (id_campanie, id_colaborator)
)

create table CopyColaborare(
    id_campanie int,
    id_colaborator int,
    data_inceput date,
    data_sfarsit date,
    descriere varchar(100),
    constraint pk_CopyColaborare primary key (id_campanie, id_colaborator)
)

create table CopyColaboratori(
    id_colaborator int primary key identity ,
    nume varchar(100),
    tip varchar(100),
    contact varchar(100)
)

create table CopyCampanii(
    id_campanie int primary key identity ,
    id_client int foreign key references Clienti(id_client) on update cascade on delete cascade ,
    nume_campanie varchar(100),
    data_inceput date,
    data_sfarsit date,
    buget_alocat int
)