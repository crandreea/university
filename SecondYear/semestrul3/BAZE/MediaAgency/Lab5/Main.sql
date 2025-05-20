use MediaAgency;
--view colaboratori
--view clientii
--view proiect cu campanii si departamente
CREATE OR ALTER VIEW VClienti
AS
    SELECT id_client, nume_firma, persoana_contact, email FROM Clienti
    WHERE len(persoana_contact) > 12
go

CREATE OR ALTER VIEW VColaboratori
AS
    SELECT id_colaborator, nume, tip, contact FROM Colaboratori
go

CREATE OR ALTER VIEW VProiecte
AS
    SELECT P.id_campanie, C.nume_campanie,C.data_inceput, P.id_departament, D.denumire, D.coordonator, P.deadline, P.descriere
    FROM Proiecte P
    INNER JOIN dbo.Campanii C on C.id_campanie = P.id_campanie
    INNER JOIN dbo.Departamente D on D.id_departament = P.id_departament
go

if exists(SELECT name FROM sys.indexes WHERE name = 'N_idx_Clienti_contact')
    drop index N_idx_Clienti_contact on Clienti
GO
create nonclustered index N_idx_Clienti_contact on Clienti(persoana_contact)
GO

if exists(SELECT name FROM sys.indexes WHERE name = 'N_idx_Colaboratori_tip')
    drop index N_idx_Colaboratori_tip on Colaboratori
GO
create nonclustered index N_idx_Colaboratori_tip on Colaboratori(tip)
GO

if exists(SELECT name FROM sys.indexes WHERE name = 'N_idx_Proiecte_deadline')
    drop index N_idx_Proiecte_deadline on Proiecte
GO
create nonclustered index N_idx_Proiecte_deadline on Proiecte(deadline)
GO


select * from VProiecte ORDER BY VProiecte.deadline
exec insertProiect 2, 2, '2025-10-10', 'descriereCamp'
exec updateProiect 2, 2, '2026-10-10', 'updated'
exec selectProiect 2, 2
exec deleteProiect 2, 2

select * from Campanii
exec insertCampanie 2, 'numeCampanie' , '2024-10-10' , '2025-10-10', 13000
exec updateCampanie 2012, 3, 'numeUpdated', '2024-10-10', '2025-10-10', 13000
exec selectCampanie 2012
exec deleteCampanie 2012

select * from Departamente
exec insertDepartament 'Departament1', 'Popoescu', 34
exec updateDepartament 12, 'Departament1', 'Popoescu', 30
exec selectDepartamente 12
exec deleteDepartament 12

select * from VClienti ORDER BY persoana_contact
exec insertClient 'Ionescu', 'PopaAAAAAAAAAAAAAAAAAAA', 'aaa@gmail.com'
exec updateClient 13, 'Ionescu', 'updatefffffffffffffffffff', 'ionescu.popa@gmail.com'
exec selectClient 13
exec deleteClienti 13

select * from VColaboratori ORDER BY tip
exec insertColaborator 'Colaborator15', 'tip5', 26473229
exec updateColaborator 2085, 'Colaborator1', 'tip9', 1234
exec selectColaborator 2085
exec deleteColaborator 2085








