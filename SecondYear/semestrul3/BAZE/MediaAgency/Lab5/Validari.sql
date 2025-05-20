use MediaAgency
go
CREATE OR ALTER FUNCTION validateName
(
    @name VARCHAR(100)
)
RETURNS BIT
AS
    BEGIN
        if @name is null
            return 0
        if LTRIM(@name) = ''
            return 0
        if LEN(@name) < 3
            return 0

        DECLARE @len INT
        SET @len = LEN(@name)

        DECLARE @index INT
        SET @index = 1

        WHILE @index < @len
        BEGIN
            IF NOT SUBSTRING(@name, @index, 1) LIKE '%[\-a-zA-z]'
                return 0
            SET @index = @index + 1
        END

        return 1
    END
GO


CREATE OR ALTER FUNCTION validateNumber
(
    @number varchar(100)
)
RETURNS BIT
AS
    BEGIN
        if @number is null
            return 0
        if LTRIM(@number) = ''
            return 0

        DECLARE @len INT
        SET @len = LEN(@number)

        DECLARE @index INT
        SET @index = 1

        DECLARE @car char

        WHILE @index < @len
        BEGIN
            select @car = SUBSTRING(@number, @index, 1)
		    if ISNUMERIC(@car) != 1
		        return 0
            SET @index = @index + 1
        END

        return 1
    END
GO

CREATE OR ALTER FUNCTION validateDate
(
    @date varchar(40)
)
RETURNS BIT
AS
    BEGIN
        if @date is null
		    RETURN 1
        if LEN(@date) < 9
            RETURN 0
        if not SUBSTRING(@date,1,4) LIKE '%20[0-9][0-9]'
           RETURN 0
        if not SUBSTRING(@date,6,1) LIKE '%[0-1]'
           RETURN 0

        if SUBSTRING(@date,6,1) = '0'
        BEGIN
            if not SUBSTRING(@date,7,1) LIKE '%[0-9]'
            BEGIN
                RETURN 0
            END
        END
        ELSE
            if not SUBSTRING(@date,7,1) LIKE '%[0-2]'
            BEGIN
                RETURN 0
            END

        if not SUBSTRING(@date,9,1) LIKE '%[0-3]'
           RETURN 0

        if not SUBSTRING(@date,9,1) LIKE '3'
        begin
            if not SUBSTRING(@date,9,2) LIKE '%[0-2][0-9]'
                RETURN 0
        end
        else
        begin
            if not SUBSTRING(@date,10,1) LIKE '%[0-1]'
                return 0
        end
        RETURN 1

    END

CREATE OR ALTER FUNCTION validateIdDepartamente
(
    @id int
)
RETURNS BIT
AS
    BEGIN
        if not exists (SELECT * FROM Departamente WHERE Departamente.id_departament = @id)
        begin
            return 0
        end
        return 1
    end
go

CREATE OR ALTER FUNCTION validateIdClienti
(
    @id int
)
RETURNS BIT
AS
    BEGIN
        if not exists (SELECT * FROM Clienti WHERE Clienti.id_client= @id)
        begin
            return 0
        end
        return 1
    end
go

CREATE OR ALTER FUNCTION validateIdColaboratori
(
    @id int
)
RETURNS BIT
AS
    BEGIN
        if not exists (SELECT * FROM Colaboratori WHERE Colaboratori.id_colaborator = @id)
        begin
            return 0
        end
        return 1
    end
go

CREATE OR ALTER FUNCTION validateIdCampanii
(
    @id int
)
RETURNS BIT
AS
    BEGIN
        if not exists (SELECT * FROM Campanii WHERE Campanii.id_campanie = @id)
        begin
            return 0
        end
        return 1
    end
go

CREATE OR ALTER FUNCTION validateIdProiecte
(
    @id_campanie int,
    @id_departament int
)
RETURNS BIT
AS
    BEGIN
        if not exists (SELECT * FROM Proiecte WHERE Proiecte.id_campanie= @id_campanie AND Proiecte.id_departament=@id_departament)
        begin
            return 0
        end
        return 1
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
