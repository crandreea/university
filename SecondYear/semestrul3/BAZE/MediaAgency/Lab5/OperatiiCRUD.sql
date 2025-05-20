--departamente + proiect + campanii
-- clienti, colaboratori
use MediaAgency;

--CRUD DEPARTAMENTE
alter table dovleci
add constraint ck_mentiune check (mentiune = 'DA' or mentiune = 'NU')
go


CREATE OR ALTER PROCEDURE deleteDepartament
(
    @id int
)
AS
    BEGIN
        DECLARE @valid BIT
        SET @valid = 1

        if dbo.validateIdDepartamente(@id) = 0
        begin
            SET @valid = 0
            print 'Invalid id for Departamente'
        end

        if @valid = 0
        begin
            print 'Invalid data'
            return
        end

        DELETE FROM Proiecte where id_departament = @id
        DELETE FROM Departamente WHERE id_departament = @id
        print 'Successfully deleted!'

    end
    go

CREATE OR ALTER PROCEDURE insertDepartament
(
    @nume varchar(100),
    @coordonator varchar(100),
    @persoane varchar(100)
)
as
    begin
        DECLARE @valid BIT
        SET @valid = 1

        if dbo.validateName(@nume) = 0
            begin
                SET @valid = 0
                print 'Name cannot be null'
            end

        if dbo.validateName(@coordonator) = 0
            begin
                SET @valid = 0
                print 'Coordonator cannot be null'
            end

        if dbo.validateNumber(@persoane) = 0
            begin
                SET @valid = 0
                print 'Invalid number'
            end

        if @valid = 0
        begin
            print 'Invalid data'
            return
        end

        INSERT INTO Departamente(denumire, coordonator, numar_persoane)
        values (@nume, @coordonator, CAST(@persoane AS INT))

        print 'Successfully added!'

    end
    go

CREATE OR ALTER PROCEDURE selectDepartamente
(
    @id int
)
as
    begin
         DECLARE @valid BIT
        SET @valid = 1

        if dbo.validateIdDepartamente(@id) = 0
        begin
            SET @valid = 0
            print 'Invalid id for Departamente'
        end

        if @valid = 0
        begin
            print 'Invalid data'
            return
        end

        SELECT * FROM Departamente where id_departament = @id
    end
    go

CREATE OR ALTER PROCEDURE updateDepartament
(
    @id int,
    @nume varchar(100),
    @coordonator varchar(100),
    @persoane varchar(100)
)
as
    begin
        DECLARE @valid BIT
        SET @valid = 1

        if dbo.validateIdDepartamente(@id) = 0
            begin
                SET @valid = 0
                print 'Invalid id for Departamente'
            end

        if dbo.validateName(@nume) = 0
            begin
                SET @valid = 0
                print 'Name cannot be null'
            end

        if dbo.validateName(@coordonator) = 0
            begin
                SET @valid = 0
                print 'Coordonator cannot be null'
            end

        if dbo.validateNumber(@persoane) = 0
            begin
                SET @valid = 0
                print 'Invalid number'
            end

        if @valid = 0
        begin
            print 'Invalid data'
            return
        end

        UPDATE Departamente
        SET denumire = @nume, coordonator = @coordonator, numar_persoane = CAST(@persoane AS INT)
        WHERE id_departament = @id

        print 'Successfully updated!'
    end
    go

--CRUD CLIENTI


CREATE OR ALTER PROCEDURE deleteClienti
(
    @id int
)
AS
    BEGIN
        DECLARE @valid BIT
        SET @valid = 1

        if dbo.validateIdClienti (@id) = 0
        begin
            SET @valid = 0
            print 'Invalid id for Clienti'
        end

        if @valid = 0
        begin
            print 'Invalid data'
            return
        end

        DELETE FROM Campanii where id_client = @id
        DELETE FROM Clienti WHERE id_client = @id
        print 'Successfully deleted!'

    end
    go

CREATE OR ALTER PROCEDURE insertClient
(
    @nume varchar(100),
    @contact varchar(100),
    @email varchar(100)
)
as
    begin
        DECLARE @valid BIT
        SET @valid = 1

        if dbo.validateName(@nume) = 0
            begin
                SET @valid = 0
                print 'Name cannot be null'
            end

        if dbo.validateName(@contact) = 0
            begin
                SET @valid = 0
                print 'Coordonator cannot be null'
            end

        if @valid = 0
        begin
            print 'Invalid data'
            return
        end

        INSERT INTO Clienti(nume_firma, persoana_contact, email)
        values (@nume, @contact, @email)

        print 'Successfully added!'

    end
    go

CREATE OR ALTER PROCEDURE selectClient
(
    @id int
)
as
    begin
         DECLARE @valid BIT
        SET @valid = 1

        if dbo.validateIdClienti (@id) = 0
        begin
            SET @valid = 0
            print 'Invalid id for Clienti'
        end

        if @valid = 0
        begin
            print 'Invalid data'
            return
        end

        SELECT * FROM Clienti where id_client = @id
    end
    go

CREATE OR ALTER PROCEDURE updateClient
(
    @id int,
    @nume varchar(100),
    @contact varchar(100),
    @email varchar(100)
)
as
    begin
        DECLARE @valid BIT
        SET @valid = 1

        if dbo.validateIdClienti (@id) = 0
            begin
                SET @valid = 0
                print 'Invalid id for Clienti'
            end

        if dbo.validateName(@nume) = 0
            begin
                SET @valid = 0
                print 'Name cannot be null'
            end

        if dbo.validateName(@contact) = 0
            begin
                SET @valid = 0
                print 'Coordonator cannot be null'
            end

        if @valid = 0
        begin
            print 'Invalid data'
            return
        end

        UPDATE Clienti
        SET nume_firma = @nume, persoana_contact = @contact, email = @email
        WHERE id_client= @id

        print 'Successfully updated!'
    end
    go


-- CRUD Proiect

CREATE OR ALTER PROCEDURE deleteProiect
(
    @id_departament int,
    @id_campanie int
)
AS
    BEGIN
        DECLARE @valid BIT
        SET @valid = 1

        if dbo.validateIdProiecte (@id_campanie, @id_departament) = 0
        begin
            SET @valid = 0
            print 'Invalid id for Proiecte'
        end

        if @valid = 0
        begin
            print 'Invalid data'
            return
        end

        DELETE FROM Proiecte where id_campanie = @id_campanie AND id_departament = @id_departament
        print 'Successfully deleted!'

    end
    go

CREATE OR ALTER PROCEDURE insertProiect
(
    @id_campanie int,
    @id_departament int,
    @deadline datetime,
    @descriere varchar(100)
)
as
    begin
        DECLARE @valid BIT
        SET @valid = 1

        if dbo.validateIdProiecte(@id_campanie, @id_departament) = 1
            begin
                SET @valid = 0
                print 'Proiect already exists'
            end

        if dbo.validateIdCampanii (@id_campanie) = 0
            begin
                set @valid = 0
                print 'Invalid id for Campanie'
            end

        if dbo.validateIdDepartamente (@id_departament) = 0
            begin
                set @valid = 0
                print 'invalid id for Departament'
            end

        if dbo.validateName(@descriere) = 0
            begin
                SET @valid = 0
                print 'Invalid description'
            end

        if @valid = 0
        begin
            print 'Invalid data'
            return
        end

        INSERT INTO Proiecte(id_departament, id_campanie, deadline, descriere)
        values (@id_departament, @id_campanie, @deadline, @descriere)

        print 'Successfully added!'

    end
    go

CREATE OR ALTER PROCEDURE selectProiect
(
    @id_campanie int,
    @id_departament int
)
as
    begin
         DECLARE @valid BIT
        SET @valid = 1

        if dbo.validateIdProiecte(@id_campanie, @id_departament) = 0
            begin
                SET @valid = 0
                print 'Proiect already exists'
            end

        if @valid = 0
        begin
            print 'Invalid data'
            return
        end

        SELECT * FROM Proiecte WHERE id_campanie = @id_campanie AND id_departament = @id_departament
    end
    go

CREATE OR ALTER PROCEDURE updateProiect
(
    @id_campanie int,
    @id_departament int,
    @deadline datetime,
    @descriere varchar(100)
)
as
    begin
        DECLARE @valid BIT
        SET @valid = 1

         if dbo.validateIdProiecte(@id_campanie, @id_departament) = 0
            begin
                SET @valid = 0
                print 'Invalid id for Proiect'
            end

        if dbo.validateIdCampanii (@id_campanie) = 0
            begin
                set @valid = 0
                print 'Invalid id for Campanie'
            end

        if dbo.validateIdDepartamente (@id_departament) = 0
            begin
                set @valid = 0
                print 'invalid id for Departament'
            end

        if dbo.validateName(@descriere) = 0
            begin
                SET @valid = 0
                print 'Invalid description'
            end

        if @valid = 0
        begin
            print 'Invalid data'
            return
        end

        UPDATE Proiecte
        SET deadline = @deadline, descriere = @descriere
        WHERE id_campanie = @id_campanie AND id_departament = @id_departament

        print 'Successfully updated!'
    end
    go

--CRUD CAMPANII


CREATE OR ALTER PROCEDURE deleteCampanie
(
    @id int
)
AS
    BEGIN
        DECLARE @valid BIT
        SET @valid = 1

        if dbo.validateIdCampanii (@id) = 0
        begin
            SET @valid = 0
            print 'Invalid id for Campanii'
        end

        if @valid = 0
        begin
            print 'Invalid data'
            return
        end

        DELETE FROM Proiecte where id_campanie = @id
        DELETE FROM Campanii where id_campanie = @id

        print 'Successfully deleted!'

    end
    go

CREATE OR ALTER PROCEDURE insertCampanie
(
    @id_client int,
    @nume varchar(100),
    @data_inceput date,
    @data_sfarsit date,
    @buget varchar(100)
)
as
    begin
        DECLARE @valid BIT
        SET @valid = 1

        if dbo.validateName(@nume) = 0
            begin
                SET @valid = 0
                print 'Name cannot be null'
            end

       if dbo.validateDate (@data_inceput) = 0
            begin
                SET @valid = 0
                print 'Invalid date'
            end

       if dbo.validateDate (@data_sfarsit) = 0
            begin
                SET @valid = 0
                print 'Invalid date'
            end

       if dbo.validateNumber (@buget) = 0
           begin
                SET @valid = 0
                print 'Buget is not a number'
            end

       if dbo.validateIdClienti (@id_client) = 0
            begin
                SET @valid = 0
                print 'Invalid id for Clienti'
            end

        if @valid = 0
        begin
            print 'Invalid data'
            return
        end

        INSERT INTO Campanii(id_client, nume_campanie, data_inceput, data_sfarsit, buget_alocat)
        values (@id_client, @nume, @data_inceput, @data_sfarsit, CAST(@buget AS INT))

        print 'Successfully added!'

    end
    go

CREATE OR ALTER PROCEDURE selectCampanie
(
    @id int
)
as
    begin
         DECLARE @valid BIT
        SET @valid = 1

        if dbo.validateIdCampanii (@id) = 0
        begin
            SET @valid = 0
            print 'Invalid id for Campanii'
        end

        if @valid = 0
        begin
            print 'Invalid data'
            return
        end

        SELECT * FROM Campanii WHERE id_campanie = @id
    end
    go

CREATE OR ALTER PROCEDURE updateCampanie
(
    @id int,
    @id_client int,
    @nume varchar(100),
    @data_inceput date,
    @data_sfarsit date,
    @buget varchar(100)
)
as
    begin
        DECLARE @valid BIT
        SET @valid = 1

        if dbo.validateName(@nume) = 0
            begin
                SET @valid = 0
                print 'Name cannot be null'
            end

       if dbo.validateDate (@data_inceput) = 0
            begin
                SET @valid = 0
                print 'Invalid date'
            end

       if dbo.validateDate (@data_sfarsit) = 0
            begin
                SET @valid = 0
                print 'Invalid date'
            end

       if dbo.validateNumber (@buget) = 0
           begin
                SET @valid = 0
                print 'Buget is not a number'
            end

       if dbo.validateIdClienti (@id_client) = 0
            begin
                SET @valid = 0
                print 'Invalid id for Clienti'
            end

        if @valid = 0
        begin
            print 'Invalid data'
            return
        end

        UPDATE Campanii
        SET id_client = @id_client, nume_campanie = @nume, data_inceput = @data_inceput, data_sfarsit = @data_sfarsit, buget_alocat = @buget
        WHERE id_campanie = @id

        print 'Successfully updated!'
    end
    go


--CRUD COLABORATORI

CREATE OR ALTER PROCEDURE deleteColaborator
(
    @id int
)
AS
    BEGIN
        DECLARE @valid BIT
        SET @valid = 1

        if dbo.validateIdColaboratori (@id) = 0
        begin
            SET @valid = 0
            print 'Invalid id for Colaboratori'
        end

        if @valid = 0
        begin
            print 'Invalid data'
            return
        end

        DELETE FROM Colaboratori WHERE id_colaborator = @id
        print 'Successfully deleted!'

    end
go

CREATE OR ALTER PROCEDURE insertColaborator
(
    @nume varchar(100),
    @tip varchar(100),
    @contact varchar(100)
)
as
    begin
        DECLARE @valid BIT
        SET @valid = 1

        if dbo.validateName(@nume) = 0
            begin
                SET @valid = 0
                print 'Name cannot be null'
            end

        if dbo.validateName(@tip) = 0
            begin
                SET @valid = 0
                print 'Coordonator cannot be null'
            end

       if dbo.validateNumber (@contact) = 0
           begin
               set @valid = 0
               print 'Invalid number'
           end

        if @valid = 0
        begin
            print 'Invalid data'
            return
        end

        INSERT INTO Colaboratori(nume, tip, contact)
        values (@nume, @tip, @contact)

        print 'Successfully added!'

    end
go

CREATE OR ALTER PROCEDURE updateColaborator
(
    @id int,
    @nume varchar(100),
    @tip varchar(100),
    @contact varchar(100)
)
as
    begin
        DECLARE @valid BIT
        SET @valid = 1

        if dbo.validateIdColaboratori (@id) = 0
            begin
                SET @valid = 0
                print 'Invalid id for Colaboratori'
            end

        if dbo.validateName(@nume) = 0
            begin
                SET @valid = 0
                print 'Name cannot be null'
            end

        if dbo.validateName(@tip) = 0
            begin
                SET @valid = 0
                print 'Coordonator cannot be null'
            end

        if dbo.validateNumber (@contact) = 0
           begin
               set @valid = 0
               print 'Invalid number'
           end

        if @valid = 0
        begin
            print 'Invalid data'
            return
        end

        UPDATE Colaboratori
        SET nume = @nume, tip = @tip, contact = @contact
        WHERE id_colaborator= @id

        print 'Successfully updated!'
    end
go

CREATE OR ALTER PROCEDURE selectColaborator
(
    @id int
)
as
    begin
         DECLARE @valid BIT
        SET @valid = 1

        if dbo.validateIdColaboratori (@id) = 0
        begin
            SET @valid = 0
            print 'Invalid id for Colaboratori'
        end

        if @valid = 0
        begin
            print 'Invalid data'
            return
        end

        SELECT * FROM Colaboratori WHERE id_colaborator = @id
    end
go






