CREATE DATABASE Filme

CREATE TABLE Regizori (
    id INT IDENTITY(1, 1) PRIMARY KEY,
    nume VARCHAR(100),
    data_nastere DATE NOT NULL
)

CREATE TABLE Tari (
    id INT IDENTITY(1, 1) PRIMARY KEY,
    nume VARCHAR(100) NOT NULL
)

CREATE TABLE Actori (
    nume VARCHAR(100) PRIMARY KEY,
    tara_id INT NOT NULL,
    FOREIGN KEY (tara_id) REFERENCES Tari(id)
)

CREATE TABLE Tipuri (
    id INT IDENTITY(1, 1) PRIMARY KEY,
    descriere VARCHAR(255) NOT NULL
)

CREATE TABLE Filme (
    id INT IDENTITY(1, 1) PRIMARY KEY,
    titlu VARCHAR(100) NOT NULL,
    durata INT NOT NULL,
    an SMALLINT NOT NULL,
    pret REAL NOT NULL,
    regizor_id INT NOT NULL,
    tip_id INT NOT NULL,
    FOREIGN KEY (regizor_id) REFERENCES Regizori(id),
    FOREIGN KEY (tip_id) REFERENCES Tipuri(id)
)

CREATE TABLE ActoriFilme (
    actor_nume VARCHAR(100) NOT NULL,
    film_id INT NOT NULL,
    FOREIGN KEY (actor_nume) REFERENCES Actori(nume),
    FOREIGN KEY (film_id) REFERENCES Filme(id),
    PRIMARY KEY (actor_nume, film_id)
)

CREATE TABLE Clienti (
    id INT IDENTITY(1, 1) PRIMARY KEY,
    nume VARCHAR(100) NOT NULL
)

CREATE TABLE Inchirieri (
    client_id INT NOT NULL,
    film_id INT NOT NULL,
    data_inceput DATE NOT NULL,
    data_final DATE NOT NULL,
    FOREIGN KEY (client_id) REFERENCES Clienti(id),
    FOREIGN KEY (film_id) REFERENCES Filme(id),
    PRIMARY KEY (client_id, film_id)
)

INSERT INTO Regizori (nume, data_nastere) VALUES
('Popescu Adrian', '1975/02/13'),
('Dascalu Marin', '1956/04/12'),
('Ioan Dacian', '1967/01/01')

SELECT * FROM Regizori

INSERT INTO Tari (nume) VALUES
('Italia'),
('Romania'),
('Spania')

SELECT * FROM Tari

INSERT INTO Actori (nume, tara_id) VALUES
('Croitoru Andreea', 1),
('Mot Patircia', 2),
('Moghioros Eric', 3),
('Marinescu Dragos', 2)

SELECT * FROM Actori

INSERT INTO Tipuri (descriere) VALUES
('drama'),
('actiune'),
('horror'),
('romanta')

SELECT * FROM Tipuri

INSERT INTO Filme (titlu, durata, an, pret, regizor_id, tip_id) VALUES
('O poveste de mai', 129, 2003, 30, 1, 1),
('Poveste ca-n rai', 145, 2017, 19, 1, 2),
('Cineva e acasa', 91, 2015, 39, 2, 3),
('Moartea-n vacanta', 83, 2024, 32, 3, 4)

SELECT * FROM Filme

INSERT INTO ActoriFilme (actor_nume, film_id) VALUES
('Croitoru Andreea', 1),
('Croitoru Andreea', 2),
('Croitoru Andreea', 3),
('Croitoru Andreea', 4),
('Moghioros Eric', 3),
('Mot Patircia', 4)

SELECT * FROM ActoriFilme

INSERT INTO Clienti (nume) VALUES
('Tomoni Marius'),
('Berari Adrian'),
('Viorel Marcel'),
('Ioan Alexandru')

SELECT * FROM Clienti

INSERT INTO Inchirieri (client_id, film_id, data_inceput, data_final) VALUES
(2, 4, '2021/01/03', '2021/01/10'),
(1, 3, '2024/02/05', '2025/02/21'),
(4, 1, '2024/12/01', '2024/12/13'),
(3, 2, '2008/10/04', '2008/10/08')

SELECT * FROM Inchirieri

SELECT * FROM Filme
WHERE titlu LIKE '%poveste%'

GO
CREATE OR ALTER VIEW VW_ActoriCuPeste3Filme AS
    SELECT A.nume, A.tara_id
    FROM Actori A
    JOIN ActoriFilme AF ON A.nume = AF.actor_nume
    GROUP BY A.nume, A.tara_id
    HAVING COUNT(AF.film_id) > 3
GO

SELECT * FROM VW_ActoriCuPeste3Filme

GO
CREATE OR ALTER PROCEDURE FilmeFaraInchirieri AS
BEGIN
    SELECT *
    FROM Filme F
    WHERE F.id NOT IN (SELECT film_id FROM Inchirieri)
END
GO

EXECUTE FilmeFaraInchirieri

GO
CREATE OR ALTER FUNCTION NrClienti () RETURNS INT AS
BEGIN
    RETURN (
        SELECT COUNT(I.client_id)
        FROM Inchirieri I
        JOIN Filme F ON I.film_id = F.id
        WHERE F.pret > 30 AND (YEAR(I.data_inceput) = 2021 OR YEAR(I.data_final) = 2021)
    )
END
GO

SELECT dbo.NrClienti ()
