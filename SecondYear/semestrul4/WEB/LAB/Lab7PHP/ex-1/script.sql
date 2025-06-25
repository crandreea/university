CREATE TABLE trenuri (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nr_tren VARCHAR(10) NOT NULL,
    tip_tren VARCHAR(50) NOT NULL,
    localitate_plecare VARCHAR(100) NOT NULL,
    localitate_sosire VARCHAR(100) NOT NULL,
    ora_plecare TIME NOT NULL,
    ora_sosire TIME NOT NULL
);
