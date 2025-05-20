--use MediaAgency;

INSERT INTO Departamente (denumire, coordonator, numar_persoane)
VALUES
('Marketing', 'Andrei Popescu', 10),
('Design', 'Ioana Georgescu', 8),
('Sales', 'Daniel Ionescu', 5),
('Development', 'Mihai Popa', 12),
('HR', 'Elena Marinescu', 3),
('Content Creation', 'Ana Petrescu', 6),
('Customer Support', 'Vlad Radu', 7),
('Data Analytics', 'Carmen Stan', 4),
('Public Relations', 'Maria Vasile', 5),
('IT Support', 'George Dumitrescu', 2);

INSERT INTO Angajati (nume, prenume, email, id_departament, pozitie)
VALUES
('Popescu', 'Andrei', 'andrei.popescu@agency.com', 1, 'Marketing Manager'),
('Georgescu', 'Ioana', 'ioana.georgescu@agency.com', 2, 'Senior Designer'),
('Ionescu', 'Daniel', 'daniel.ionescu@agency.com', 3, 'Sales Executive'),
('Popa', 'Mihai', 'mihai.popa@agency.com', 4, 'Lead Developer'),
('Marinescu', 'Elena', 'elena.marinescu@agency.com', 5, 'HR Manager'),
('Petrescu', 'Ana', 'ana.petrescu@agency.com', 6, 'Content Specialist'),
('Radu', 'Vlad', 'vlad.radu@agency.com', 7, 'Customer Support Representative'),
('Stan', 'Carmen', 'carmen.stan@agency.com', 8, 'Data Analyst'),
('Vasile', 'Maria', 'maria.vasile@agency.com', 9, 'PR Specialist'),
('Dumitrescu', 'George', 'george.dumitrescu@agency.com', 10, 'IT Support Technician'),
('Petrescu', 'Gabriel', 'gabriel.petrescu@agency.com', 1, 'Social Media Specialist'),
('Ilie', 'Andreea', 'andreea.ilie@agency.com', 2, 'Graphic Designer'),
('Dobre', 'Adrian', 'adrian.dobre@agency.com', 3, 'Sales Manager'),
('Constantinescu', 'Sorina', 'sorina.constantinescu@agency.com', 6, 'Content Writer'),
('Nicolae', 'Marius', 'marius.nicolae@agency.com', 4, 'Junior Developer');


INSERT INTO Clienti (nume_firma, persoana_contact, email)
VALUES
('Global Corp', 'Adrian Toma', 'adrian.toma@globalcorp.com'),
('Tech Solutions', 'Monica Radu', 'monica.radu@techsolutions.com'),
('MediaWave', 'Alina Stoica', 'alina.stoica@mediawave.com'),
('Digital Minds', 'Paul Marin', 'paul.marin@digitalminds.com'),
('NextGen Marketing', 'Silvia Dragan', 'silvia.dragan@nextgen.com'),
('Skyline Creatives', 'Cristian Ene', 'cristian.ene@skyline.com'),
('Bright Ideas', 'Daniela Matei', 'daniela.matei@brightideas.com'),
('AdVantage', 'Florin Iliescu', 'florin.iliescu@advantage.com'),
('NetFocus', 'Roxana Lungu', 'roxana.lungu@netfocus.com'),
('Innovatech', 'Alexandra Tudor', 'alexandra.tudor@innovatech.com');


INSERT INTO Contracte_Clienti (id_contract_clienti, id_client, data_semnare, data_expirare, valoare)
VALUES
(1, 1, '2023-01-15', '2024-01-15', 50000),
(2, 2, '2023-03-10', '2024-03-10', 45000),
(3, 3, '2022-12-01', '2023-12-01', 60000),
(4, 4, '2023-02-20', '2024-02-20', 55000),
(5, 5, '2023-04-05', '2024-04-05', 70000),
(6, 6, '2022-11-15', '2023-11-15', 65000),
(7, 7, '2023-05-20', '2024-05-20', 30000),
(8, 8, '2022-10-25', '2023-10-25', 40000),
(9, 9, '2023-06-01', '2024-06-01', 35000),
(10, 10,'2023-07-10', '2024-07-10', 48000);
INSERT INTO Facturare_Clienti (id_facturare, id_contract, data_emitere, data_scadenta, suma, status)
VALUES
(1, 1, '2023-01-20', '2023-02-20', 5000, 'Paid'),
(2, 2, '2023-03-15', '2023-04-15', 4500, 'Pending'),
(3, 3, '2023-12-05', '2024-01-05', 6000, 'Paid'),
(4, 4, '2023-02-25', '2023-03-25', 5500, 'Pending'),
(5, 5, '2023-04-10', '2023-05-10', 7000, 'Overdue'),
(6, 6, '2023-11-20', '2023-12-20', 6500, 'Paid'),
(7, 7, '2023-05-25', '2023-06-25', 3000, 'Paid'),
(8, 8, '2023-10-30', '2023-11-30', 4000, 'Pending'),
(9, 9, '2023-06-05', '2023-07-05', 3500, 'Paid'),
(10, 10,'2023-07-15', '2023-08-15', 4800, 'Overdue');


INSERT INTO Campanii (id_client, nume_campanie, data_inceput, data_sfarsit, buget_alocat)
VALUES
(1, 'Social Media Boost', '2023-02-01', '2023-05-01', 30000),
(2, 'Tech Launch 2023', '2023-04-10', '2023-06-10', 40000),
(3, 'Brand Awareness 2023', '2023-01-01', '2023-03-31', 50000),
(4, 'New Market Expansion', '2023-03-15', '2023-05-15', 35000),
(5, 'Digital Revolution', '2023-05-01', '2023-08-01', 60000),
(6, 'Skyline Campaign', '2023-06-10', '2023-09-10', 55000),
(7, 'Innovative Ads', '2023-07-01', '2023-09-30', 25000),
(8, 'AdVantage Summer', '2023-08-01', '2023-10-01', 32000),
(9, 'NetFocus Growth', '2023-02-15', '2023-05-15', 30000),
(10, 'Tech Solutions Update', '2023-01-15', '2023-04-15', 45000),
(10, 'AImedicine', '2023-10-20', '2023-12-23', 53000);


INSERT INTO Colaboratori (nume, tip, contact)
VALUES
('John Doe', 'Freelancer', 'john.doe@example.com'),
('Jane Smith', 'Agency', 'jane.smith@agency.com'),
('Michael Brown', 'Freelancer', 'michael.brown@example.com'),
('Sarah Johnson', 'Agency', 'sarah.johnson@agency.com'),
('Tom White', 'Consultant', 'tom.white@example.com'),
('Emily Davis', 'Freelancer', 'emily.davis@example.com'),
('Alex Green', 'Consultant', 'alex.green@example.com'),
('Laura Blue', 'Freelancer', 'laura.blue@example.com'),
('Chris Black', 'Agency', 'chris.black@agency.com'),
('Linda Grey', 'Consultant', 'linda.grey@example.com');


INSERT INTO Contracte_Colaboratori (id_contract_colaboratori, id_colaborator, data_semnare, data_expirare, valoare)
VALUES
(1, 1, '2023-02-01', '2023-12-01', 20000),
(2, 2, '2023-03-10', '2023-12-10', 15000),
(3, 3, '2023-01-15', '2023-11-15', 18000),
(4, 4, '2023-02-20', '2023-10-20', 22000),
(5, 5, '2023-04-05', '2023-12-05', 24000),
(6, 6, '2023-05-10', '2024-05-10', 19000),
(7, 7, '2023-06-01', '2023-12-01', 25000),
(8, 8, '2023-07-15', '2024-07-15', 17000),
(9, 9, '2023-08-10', '2024-08-10', 30000),
(10, 10, '2023-09-05', '2024-09-05', 21000);


INSERT INTO Facturare_Colaborator (id_facturare, id_contract, data_emitere, data_scadenta, suma, status)
VALUES
(1,1,  '2023-02-05', '2023-03-05', 5000, 'Paid'),
(2, 2, '2023-03-15', '2023-04-15', 4500, 'Pending'),
(3, 3, '2023-01-20', '2023-02-20', 6000, 'Paid'),
(4, 4, '2023-02-25', '2023-03-25', 5500, 'Overdue'),
(5, 5, '2023-04-10', '2023-05-10', 7000, 'Pending'),
(6, 6, '2023-05-15', '2023-06-15', 6500, 'Paid'),
(7, 7, '2023-06-05', '2023-07-05', 3000, 'Paid'),
(8, 8, '2023-07-20', '2023-08-20', 4000, 'Pending'),
(9, 9, '2023-08-15', '2023-09-15', 3500, 'Paid'),
(10, 10, '2023-09-10', '2023-10-10', 4800, 'Overdue');


INSERT INTO Proiecte (id_departament, id_campanie, deadline, descriere)
VALUES
(1, 1, '2023-05-01', 'Social media campaign to increase engagement.'),
(2, 2, '2023-06-10', 'Launch of a new tech product.'),
(3, 3, '2023-03-31', 'Building brand awareness.'),
(4, 4, '2023-05-15', 'Expanding into new markets.'),
(5, 5, '2023-08-01', 'Revolutionizing digital marketing.'),
(6, 6, '2023-09-10', 'Creative campaign for Skyline.'),
(7, 7, '2023-09-30', 'Ads for innovative products.'),
(8, 8, '2023-10-01', 'Summer advertising for AdVantage.'),
(9, 9, '2023-05-15', 'Growth-focused strategy for NetFocus.'),
(10, 10, '2024-04-15', 'Update campaign for Tech Solutions.');


INSERT INTO Colaborare (id_campanie, id_colaborator, data_inceput, data_sfarsit, descriere)
VALUES
(2, 5, '2023-03-01', '2023-07-01', 'Tech launch collaboration.'),
(1, 1, '2023-02-01', '2023-05-01', 'Collaboration for social media boost.'),
(2, 2, '2023-04-10', '2023-06-10', 'Tech launch collaboration.'),
(3, 3, '2023-01-01', '2023-03-31', 'Freelancer work for brand awareness.'),
(4, 4, '2023-03-15', '2023-05-15', 'Agency support for market expansion.'),
(5, 5, '2023-05-01', '2023-08-01', 'Consultant for digital revolution.'),
(6, 6, '2023-06-10', '2023-09-10', 'Freelancer for Skyline campaign.'),
(7, 7, '2023-07-01', '2023-09-30', 'Collaboration for innovative ads.'),
(8, 8, '2023-08-01', '2023-10-01', 'Consulting for AdVantage summer.'),
(9, 9, '2023-02-15', '2023-05-15', 'Growth strategy collaboration.'),
(10, 10, '2023-01-15', '2023-04-15', 'Campaign collaboration for Tech Solutions.');
