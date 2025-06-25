CREATE TABLE laptops (
    id INT AUTO_INCREMENT PRIMARY KEY,
    manufacturer VARCHAR(100),
    cpu VARCHAR(100),
    ram VARCHAR(50),
    gpu VARCHAR(100),
    persistent_memory VARCHAR(100)
);

INSERT INTO laptops (manufacturer, cpu, ram, gpu, persistent_memory) VALUES
('Dell', 'Intel i5-1135G7', '8GB', 'Intel Iris Xe', '256GB SSD'),
('Dell', 'Intel i7-1165G7', '16GB', 'Intel Iris Xe', '512GB SSD'),
('HP', 'AMD Ryzen 5 5500U', '8GB', 'AMD Radeon Graphics', '256GB SSD'),
('Lenovo', 'Intel i5-1135G7', '8GB', 'Intel Iris Xe', '512GB SSD'),
('Asus', 'AMD Ryzen 7 5700U', '16GB', 'NVIDIA GeForce MX350', '1TB SSD'),
('Apple', 'Apple M1', '8GB', 'Apple GPU', '256GB SSD'),
('Apple', 'Apple M2', '16GB', 'Apple GPU', '512GB SSD');
