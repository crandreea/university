use MediaAgency;
/*
 - 5 interogări ce folosesc where
 - 3 interogări ce folosesc group by
 - 2 interogări ce folosesc distinct
 - 2 interogări cu having
 - 7 interogări ce extrag informaţii din mai mult de 2 tabele
 - 2 interogări pe tabele alfate în relaţie m-n.
 */

--angajati care lucreaza in departamentele ce gestioneaza campanii in val de peste 50000 lei
--WHERE(1) TABELE MULTIPLE(1) M-N(2)
SELECT a.nume, a.prenume, d.denumire, c.nume_campanie, p.deadline
FROM Angajati a
JOIN Departamente d ON a.id_departament = d.id_departament
JOIN Proiecte p ON d.id_departament = p.id_departament
JOIN Campanii c on p.id_campanie = c.id_campanie
WHERE c.buget_alocat > 50000;

--clienti care au avut mai multe campanii
--DISTINCT(1) GROUP BY(1) HAVING(1)
SELECT c.nume_firma, COUNT(DISTINCT ca.nume_campanie) as numar_campanii
FROM Clienti c
JOIN Campanii ca ON c.id_client = ca.id_client
GROUP BY c.nume_firma
HAVING COUNT( DISTINCT ca.nume_campanie) > 1


--proiectele care au deadline dupa 1 ian 2023 si au camapnii de peste 50000 lei
--WHERE(2)
SELECT p.descriere, c.nume_campanie, c.buget_alocat, p.deadline
FROM Proiecte p
JOIN Campanii c on p.id_campanie = c.id_campanie
WHERE p.deadline > '2023.01.01' and c.buget_alocat > 50000


--facturi emise pentru fiecare colaborator
--GROUP BY(2) TABELE MULTIPLE(2)
SELECT c.id_colaborator, c.nume, SUM(fact.suma) as valoare_facturi
FROM Colaboratori c
JOIN Contracte_Colaboratori con on c.id_colaborator = con.id_colaborator
JOIN Facturare_Colaborator fact on con.id_contract_colaboratori = fact.id_contract
GROUP BY c.id_colaborator, c.nume


--lista clienti care au avut contracte semnate in anul 2023
--WHERE(3)  DISTINCT(2)
SELECT DISTINCT cl.nume_firma, cl.persoana_contact, cont.valoare
FROM Clienti cl
JOIN Contracte_Clienti cont on cl.id_client = cont.id_client
WHERE data_semnare BETWEEN '2023-01-01' and '2023-12-31'


--sumele facturate pentru fiecare client si statusul lor
--GROUP BY(3) HAVING(2) TABELE MULTIPLE(3)
SELECT cl.nume_firma, SUM(fact.suma), fact.status
FROM Clienti cl
JOIN Contracte_Clienti con on cl.id_client = con.id_client
JOIN Facturare_Clienti fact on con.id_contract_clienti = fact.id_contract
GROUP BY cl.nume_firma, fact.status
HAVING SUM(fact.suma) > 2000


--clienti a caror facturi nu au fost platite
--WHERE(4) TABELE MULTIPLE(4)
SELECT cl.nume_firma, fact.data_scadenta, fact.suma
FROM Clienti cl
JOIN Contracte_Clienti cont on cl.id_client = cont.id_client
JOIN Facturare_Clienti fact on cont.id_contract_clienti = fact.id_contract
WHERE fact.status = 'Overdue'


--campaniile cu un anumit colaborator
--WHERE(5) TABELE MULTIPLE(5) M-N(1)
SELECT cam.nume_campanie, colab.data_inceput, colab.data_sfarsit, colab.descriere
FROM Campanii cam
JOIN Colaborare colab on cam.id_campanie = colab.id_campanie
JOIN Colaboratori colabs on colab.id_colaborator = colabs.id_colaborator
WHERE colabs.nume = 'Tom White' and colabs.id_colaborator = 5

--campaniile si colaboratorii lor
--TABELE MULTIPLE(6)
SELECT camp.nume_campanie, colabs.nume, colab.descriere
FROM Colaborare colab
JOIN Campanii camp on colab.id_campanie = camp.id_campanie
JOIN Colaboratori colabs on colab.id_colaborator = colabs.id_colaborator

--fiecare angajat si campania la care lucreaza
--TABELE MULTIPLE(7)
SELECT a.id_angajat, a.nume, a.prenume, d.denumire ,cam.nume_campanie, p.deadline
FROM Angajati a
JOIN Departamente d ON a.id_departament = d.id_departament
JOIN Proiecte p ON d.id_departament = p.id_departament
JOIN Campanii cam ON p.id_campanie = cam.id_campanie



