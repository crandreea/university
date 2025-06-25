CREATE TABLE contacts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    last_name VARCHAR(100) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(100)
);

INSERT INTO contacts (last_name, first_name, phone, email) VALUES
('Popescu', 'Andrei', '0721234567', 'andrei.popescu@example.com'),
('Ionescu', 'Maria', '0727654321', 'maria.ionescu@example.com'),
('Georgescu', 'Radu', '0744123456', 'radu.georgescu@example.com'),
('Dumitru', 'Elena', '0733111222', 'elena.dumitru@example.com'),
('Stan', 'Ioana', '0722456677', 'ioana.stan@example.com'),
('Enache', 'Paul', '0755123456', 'paul.enache@example.com'),
('Lazar', 'Cristina', '0766987654', 'cristina.lazar@example.com'),
('Dragomir', 'Alex', '0723000999', 'alex.dragomir@example.com'),
('Barbu', 'Diana', '0733444555', 'diana.barbu@example.com'),
('Tudor', 'Vlad', '0744000888', 'vlad.tudor@example.com');


SELECT * FROM contacts