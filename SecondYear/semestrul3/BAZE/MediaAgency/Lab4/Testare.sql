use MediaAgency;
--COLABORATORI, COLABORARE, CAMPANII
-- VIEW-URI ------------------------------------------------------------------------------------------------------------

CREATE VIEW view_colaboratori
    AS SELECT nume, tip, contact FROM CopyColaboratori

CREATE VIEW view_campanii
    AS
    SELECT camp.nume_campanie, camp.data_inceput, camp.data_sfarsit
    FROM CopyCampanii camp
    INNER JOIN Clienti c ON camp.id_client = c.id_client
    GROUP BY camp.nume_campanie, camp.data_inceput, camp.data_sfarsit

CREATE VIEW view_colaborare
    AS
    SELECT colab.data_inceput, colab.data_sfarsit, colab.descriere
    FROM CopyColaborare colab
    INNER JOIN CopyCampanii camp ON colab.id_campanie = camp.id_campanie
    INNER JOIN CopyColaboratori colabs ON colab.id_colaborator = colabs.id_colaborator
    GROUP BY colab.data_inceput, colab.data_sfarsit, colab.descriere


-- tabel TABLES ---------------------------------------------------------------------------------------------------------
INSERT INTO Tables(Name) VALUES
                             ('CopyColaboratori'),
                             ('CopyCampanii'),
                             ('CopyColaborare');
-- tabel VIEW ----------------------------------------------------------------------------------------------------------
INSERT INTO Views(Name) VALUES
                            ('view_colaboratori'),
                            ('view_campanii'),
                            ('view_colaborare');

--- Insert / Delete tabel Colaboratori ------------------------------------------------------------------------------------------
CREATE or ALTER PROCEDURE InsertColaboratori (@rows int)
AS
BEGIN
	DECLARE @name VARCHAR(100) = 'RandomColaborator'
	DECLARE @tip VARCHAR(100) = 'RandomTip'
	DECLARE @contact VARCHAR(100) = 'RandomEmail'
	DECLARE @i int = 1

	WHILE @i<=@rows
	BEGIN
		--SELECT TOP 1 @lastId = C.id_colaborator FROM dbo.Colaboratori C ORDER BY C.id_colaborator DESC
        SET IDENTITY_INSERT CopyColaboratori ON
		INSERT INTO CopyColaboratori(id_colaborator, nume, tip, contact) VALUES (@i, @name, @tip, @contact)
		SET IDENTITY_INSERT CopyColaboratori OFF
        SET @i=@i+1
	END

END
GO

CREATE OR ALTER PROCEDURE DeleteColaboratori(@rows int)
AS
BEGIN
	DECLARE @i int
	DECLARE @lastId int

	SET @i=@rows

	WHILE @i>0
	BEGIN
		SELECT TOP 1 @lastId = C.id_colaborator FROM dbo.CopyColaboratori C ORDER BY C.id_colaborator DESC
		DELETE FROM CopyColaboratori WHERE id_colaborator=@lastId
		SET @i=@i-1
	END
    --DELETE Colaboratori where id_colaborator<=@rows
END
GO

--- Insert / Delete tabel Campanii -------------------------------------------------------------------------------------
CREATE or ALTER PROCEDURE InsertCampanii (@rows int)
AS
BEGIN
	DECLARE @id_client INT = 1
	DECLARE @name VARCHAR(100) = 'RandomCampanie'
	DECLARE @datai DATE = GETDATE()
	DECLARE @datasf DATE = DATEADD(DAY, 30, @datai)
	DECLARE @buget INT = 1000
	DECLARE @i int = 1

	WHILE @i<=@rows
	BEGIN
		--SELECT TOP 1 @lastId = C.id_campanie FROM dbo.Campanii C ORDER BY C.id_campanie DESC
		--SET @id=@lastId+1
        SET IDENTITY_INSERT CopyCampanii ON
		INSERT INTO CopyCampanii (id_campanie, id_client, nume_campanie, data_inceput, data_sfarsit, buget_alocat)
        VALUES (@i, @id_client, @name, @datai, @datasf, @buget)
		SET IDENTITY_INSERT CopyCampanii OFF
        SET @i = @i + 1
	END

END
GO

CREATE OR ALTER PROCEDURE DeleteCampanii(@rows int)
AS
BEGIN
	DECLARE @i int
	DECLARE @lastId int

	SET @i=@rows

	WHILE @i>0
	BEGIN
        SELECT TOP 1 @lastId = id_campanie FROM CopyCampanii ORDER BY id_campanie DESC
		DELETE FROM CopyCampanii WHERE id_campanie = @lastId
		SET @i=@i-1
	END
    --DELETE Campanii where id_campanie<=@rows
END
GO


--- Insert / Delete tabel Colaborare -----------------------------------------------------------------------------------
CREATE OR ALTER PROCEDURE InsertColaborare (@rows INT)
AS
BEGIN
    --exec InsertCampanii @rows

    DECLARE @id_camp INT
    DECLARE  @id_colaborator INT = 1
    DECLARE @data_inceput DATE = GETDATE()
    DECLARE @data_sfarsit DATE = DATEADD(DAY, 30, @data_inceput)
    DECLARE @descriere NVARCHAR(100) = 'ColaborareDescriere'
    DECLARE @i INT = 1

    DECLARE cursorPass CURSOR SCROLL FOR
	SELECT id_campanie FROM Campanii;
	OPEN cursorPass;
	FETCH LAST FROM cursorPass INTO @id_camp;


    WHILE @i <= @rows AND @@FETCH_STATUS = 0
    BEGIN
        --SELECT TOP 1 @id_campanie = id_campanie FROM Campanii ORDER BY id_campanie DESC
        --IF @id_campanie IS NOT NULL
        --BEGIN
        INSERT INTO CopyColaborare (id_campanie, id_colaborator, data_inceput, data_sfarsit, descriere)
        VALUES (@id_camp, @id_colaborator, @data_inceput, @data_sfarsit, @descriere)
        FETCH PRIOR FROM cursorPass INTO @id_camp;
        SET @i = @i + 1
    END

    CLOSE cursorPass;
	DEALLOCATE cursorPass;
END
GO

CREATE OR ALTER PROCEDURE DeleteColaborare (@rows INT)
AS
BEGIN
    DECLARE @i INT = @rows
    DECLARE @id_campanie INT
    DECLARE @id_colaborator INT = 1

    WHILE @i > 0
    BEGIN
        SELECT TOP 1 @id_campanie = id_campanie FROM CopyColaborare ORDER BY id_campanie DESC
        DELETE FROM CopyColaborare WHERE id_campanie = @id_campanie AND id_colaborator = @id_colaborator
        SET @i = @i - 1
    END

--     DELETE Colaborare where id_colaborator = 1
--     DELETE Campanii where id_campanie>=@rows
END
GO

--- TESTE --------------------------------------------------------------------------------------------------------------
CREATE OR ALTER PROCEDURE Insert10 (@Table VARCHAR(20))
AS
BEGIN
	IF @Table='CopyColaboratori'
	exec InsertColaboratori 10
	ELSE IF @Table='CopyCampanii'
	exec InsertCampanii 10
	ELSE IF @Table='CopyColaborare'
	exec InsertColaborare 10
	else PRINT 'Invalid name'
END
GO


CREATE OR ALTER PROCEDURE Delete10 (@Table VARCHAR(20))
AS
BEGIN
	IF @Table='CopyColaboratori'
	exec DeleteColaboratori 10
	ELSE IF @Table='CopyCampanii'
	exec DeleteCampanii 10
	ELSE IF @Table='CopyColaborare'
	exec DeleteColaborare 10
	else PRINT @Table
END
GO


CREATE OR ALTER PROCEDURE Evaluare (@View VARCHAR(20))
AS
BEGIN
	IF @View='CopyColaboratori'
	select * from view_colaboratori
	ELSE IF @View='CopyCampanii'
	select * from view_campanii
	ELSE IF @View='CopyColaborare'
	select * from view_colaborare
	else PRINT 'Invalid name'
END
GO

--- tabel TESTS --------------------------------------------------------------------------------------------------------
INSERT INTO Tests(Name) VALUES
('Delete10'),
('Insert10'),
('Evaluare')
GO

--- tabel TESTTABLES ---------------------------------------------------------------------------------------------------
INSERT INTO TestTables VALUES
(6, 4, 10, 3),
(6, 5, 10, 2),
(6, 6, 10, 1),
(7, 4, 10, 1),
(7, 5, 10, 2),
(7, 6, 10, 3)



--- tabel TESTVIEWS ----------------------------------------------------------------------------------------------------
INSERT INTO TestViews VALUES
(8, 4),
(8, 5),
(8, 6)
------------------------------------------------- MAIN -----------------------------------------------------------------
CREATE OR ALTER PROCEDURE Main (@Table VARCHAR(20))
AS
BEGIN
	DECLARE @t1 datetime, @t2 datetime, @t3 datetime
	DECLARE @desc NVARCHAR(2000)

	DECLARE @nrRows INT = 10
    DECLARE @testInsert NVARCHAR(50) = N'Insert' + CAST(@nrRows AS NVARCHAR(5))
    DECLARE @testDelete NVARCHAR(50) = N'Delete' + CAST(@nrRows AS NVARCHAR(5))

	DECLARE @idTest int

	if @Table='CopyColaboratori'
		BEGIN
			SET @t1 =GETDATE()
			--DELETE Colaboratori
			exec @testDelete CopyColaboratori
			exec @testInsert CopyColaboratori
			--exec @testDelete Colaboratori
			SET @t2 =GETDATE()
			exec Evaluare CopyColaboratori
			SET @t3 =GETDATE()

			SET @desc=N'The tests '+@testInsert+', '+@testDelete+', and view on '+@Table
			INSERT INTO TestRuns(description, startat, endat) VALUES ( @desc,@t1, @t3)
			SELECT TOP 1 @idTest=T.TestRunID FROM dbo.TestRuns T ORDER BY T.TestRunID DESC

			INSERT INTO TestRunTables VALUES (@idTest, 4,@t1,@t2)
			INSERT INTO TestRunViews VALUES (@idTest, 4,@t2,@t3)
		END

	ELSE if @Table='CopyCampanii'
		BEGIN
			SET @t1 =GETDATE()
			--DELETE Campanii
			exec @testDelete CopyCampanii
			exec @testInsert CopyCampanii
			--exec @testDelete Campanii
			SET @t2 =GETDATE()
			exec Evaluare CopyCampanii
			SET @t3 =GETDATE()

			SET @desc=N'The tests '+@testInsert+', '+@testDelete+', and view on '+@Table
			INSERT INTO TestRuns(description, startat, endat) VALUES (@desc,@t1,@t3)
			SELECT TOP 1 @idTest=T.TestRunID FROM dbo.TestRuns T ORDER BY T.TestRunID DESC

			INSERT INTO TestRunTables VALUES (@idTest,5,@t1,@t2)
			INSERT INTO TestRunViews VALUES (@idTest,5,@t2,@t3)
		END

	ELSE if @Table='CopyColaborare'
		BEGIN
			SET @t1 =GETDATE()
			--DELETE Colaborare
			exec @testDelete CopyColaborare
			exec @testInsert CopyColaborare
			--exec @testDelete Colaborare
			SET @t2 =GETDATE()
			exec Evaluare CopyColaborare
			SET @t3 =GETDATE()

			SET @desc=N'The tests '+@testInsert+', '+@testDelete+', and view on '+@Table
			INSERT INTO TestRuns(description, startat, endat) VALUES (@desc,@t1,@t3)
			SELECT TOP 1 @idTest=T.TestRunID FROM dbo.TestRuns T ORDER BY T.TestRunID DESC

			INSERT INTO TestRunTables VALUES (@idTest,6,@t1,@t2)
			INSERT INTO TestRunViews VALUES (@idTest,6,@t2,@t3)
		END

	ELSE PRINT 'Invalid Table'
END

CREATE OR ALTER PROCEDURE main1 AS
    BEGIN
        DECLARE @i INT
        DECLARE @t1 datetime, @t2 datetime
        SELECT @i = COUNT(*) FROM Tables
        DECLARE @tableName varchar(50)

        DECLARE cursorT CURSOR SCROLL FOR
	    SELECT name FROM Tables;

        OPEN cursorT;
	    FETCH LAST FROM cursorT INTO @tableName

        SET @t1 =GETDATE()
        WHILE @i>0 AND @@FETCH_STATUS=0
        BEGIN
            EXEC main @tableName
            print @tableName
            FETCH PRIOR FROM cursorT INTO @tableName;
            SET @i=@i-1
        END

        SET @t2 = GETDATE()
        DECLARE @desc VARCHAR(50) = 'tests'
        INSERT INTO TestRuns(description, startat, endat) VALUES (@desc,@t1,@t2)
        CLOSE cursorT;
        DEALLOCATE cursorT;
    END


EXEC main1

select * from TestRuns
select * from TestRunTables
select * from TestRunViews

DELETE TestRuns
DELETE TestRunTables
DELETE TestRunViews

exec Insert10 CopyCampanii
select * from CopyCampanii

exec Insert10 CopyColaboratori
select * from CopyColaboratori

exec Insert10 CopyColaborare
select * from CopyColaborare

exec DeleteColaborare 10

