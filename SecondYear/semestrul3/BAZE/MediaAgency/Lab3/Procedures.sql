use MediaAgency;

-- modifica tipul unei coloane
CREATE OR ALTER PROCEDURE procedure_1
AS
    ALTER TABLE Angajati
    ALTER COLUMN nume nvarchar(100);
GO


CREATE OR ALTER PROCEDURE undo_procedure_1
AS
    ALTER TABLE Angajati
    ALTER COLUMN nume varchar(100);
GO


-- adauga o costrângere de “valoare implicită” pentru un câmp
CREATE OR ALTER PROCEDURE procedure_2
AS
    ALTER TABLE Departamente
    ADD CONSTRAINT c_numar_persoane DEFAULT 10 FOR numar_persoane;
GO

-- sterge o constrangere de "valoare implicita"
CREATE OR ALTER PROCEDURE undo_procedure_2
AS
    ALTER TABLE Departamente
    DROP CONSTRAINT c_numar_persoane;
GO


-- creeza o tabelă
CREATE OR ALTER PROCEDURE procedure_3
AS
    CREATE TABLE TranzactiiBancare(
        id_tranzactie INT PRIMARY KEY,
        nume_banca varchar(100),
        suma int
    );
GO


-- sterge o tabela
CREATE OR ALTER PROCEDURE undo_procedure_3
AS
    DROP TABLE TranzactiiBancare;
GO


-- adăuga un câmp nou
CREATE OR ALTER PROCEDURE procedure_4
AS
    ALTER TABLE Facturare_Clienti
    ADD id_tranzactie int;
GO

-- sterge un camp
CREATE OR ALTER PROCEDURE undo_procedure_4
AS
    ALTER TABLE Facturare_Clienti
    DROP COLUMN id_tranzactie;
GO


-- creeaza o constrângere de cheie străină
CREATE OR ALTER PROCEDURE procedure_5
AS
    ALTER TABLE Facturare_Clienti
    ADD CONSTRAINT fk_tranzactie
    FOREIGN KEY (id_tranzactie) REFERENCES TranzactiiBancare(id_tranzactie)
GO


-- şterge o constrângere de cheie străină
CREATE OR ALTER PROCEDURE undo_procedure_5
AS
    ALTER TABLE Facturare_Clienti
    DROP CONSTRAINT fk_tranzactie

GO

EXEC procedure_1;
EXEC procedure_2;
EXEC procedure_3;
EXEC procedure_4;
EXEC procedure_5;

EXEC undo_procedure_5;
EXEC undo_procedure_4;
EXEC undo_procedure_3;
EXEC undo_procedure_2;
EXEC undo_procedure_1;


CREATE TABLE Version(
    cod_v INT PRIMARY KEY IDENTITY ,
    nr_versiune INT
)

INSERT INTO Version(nr_versiune) VALUES (0);

CREATE OR ALTER PROCEDURE main @versiune INT
AS
BEGIN

    IF @versiune < 0 OR @versiune > 5
    BEGIN
        PRINT 'Versiunea este invalida!'
        RETURN
    end

    DECLARE @versiune_curenta AS INT
    SET @versiune_curenta = (SELECT nr_versiune FROM Version)

    IF @versiune = @versiune_curenta
    BEGIN
        PRINT 'Suntem in versiunea ceruta!'
        RETURN
    end

    DECLARE @procedura AS VARCHAR(100)

    UPDATE Version
    SET nr_versiune = @versiune
    WHERE cod_v = 1;

    WHILE (@versiune_curenta < @versiune)
    BEGIN
            SET @versiune_curenta = @versiune_curenta + 1
            SET @procedura = 'procedure_' + CAST(@versiune_curenta AS VARCHAR(10))
            PRINT 'Se executa procedura: ' + @procedura
            EXEC @procedura
    end

    WHILE (@versiune_curenta > @versiune)
    BEGIN
            SET @procedura = 'undo_procedure_' + CAST(@versiune_curenta AS VARCHAR(10))
            PRINT 'Se executa procedura de undo: ' + @procedura
            EXEC @procedura
            SET @versiune_curenta = @versiune_curenta - 1
    end

end
GO

SELECT * FROM Version;

exec main 5;
exec main 4;
exec main 3;
exec main 2;
exec main 1;
exec main 0;