USE laborator_ajax;

CREATE TABLE train_stations (
id INT AUTO_INCREMENT PRIMARY KEY,
start_city VARCHAR(100) NOT NULL,
end_city VARCHAR(100) NOT NULL
);

INSERT INTO train_stations (start_city, end_city) VALUES
('Bucuresti', 'Cluj'),
('Bucuresti', 'Iasi'),
('Cluj', 'Timisoara'),
('Cluj', 'Oradea'),
('Iasi', 'Bacau');