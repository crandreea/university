CREATE DATABASE Banci

CREATE TABLE Customers (
    id INT IDENTITY(1, 1) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    birth DATE NOT NULL
)

CREATE TABLE BankAccounts (
    id INT IDENTITY(1, 1) PRIMARY KEY,
    IBAN VARCHAR(100) NOT NULL,
    balance REAL NOT NULL,
    holder_id INT NOT NULL,
    FOREIGN KEY (holder_id) REFERENCES Customers(id) ON DELETE CASCADE
)

CREATE TABLE Cards (
    id INT IDENTITY(1, 1) PRIMARY KEY,
    number VARCHAR(100) NOT NULL,
    CVV VARCHAR(10) NOT NULL,
    account_id INT NOT NULL,
    FOREIGN KEY (account_id) REFERENCES BankAccounts(id) ON DELETE CASCADE
)

CREATE TABLE ATMs (
    id INT IDENTITY(1, 1) PRIMARY KEY,
    address VARCHAR(255) NOT NULL
)

CREATE TABLE Transactions (
    id INT IDENTITY(1, 1) PRIMARY KEY,
    ATM_id INT NOT NULL,
    card_id INT NOT NULL,
    sum REAL,
    FOREIGN KEY (ATM_id) REFERENCES ATMs(id),
    FOREIGN KEY (card_id) REFERENCES Cards(id)
)

INSERT INTO Customers (name, birth) VALUES
('Moghioros Eric', '2004/08/12'),
('Croitoru Andreea', '2005/01/30'),
('Moghioros Simona', '1975/11/16')

SELECT * FROM Customers

INSERT INTO BankAccounts (IBAN, balance, holder_id) VALUES
('12947928423', 4300.00, 1),
('08297323199', 1200.59, 2),
('98236482371', 578.91, 3)

SELECT * FROM BankAccounts

INSERT INTO Cards (number, CVV, account_id) VALUES
('92389573294329', '423', 1),
('72394327643493', '423', 1),
('12873629423923', '199', 2)

SELECT * FROM Cards

INSERT INTO ATMs (address) VALUES
('Str. Byron, Nr. 78'),
('Str. Lalelelor, Nr. 2-4'),
('Str. Cozii, Nr. 22')

SELECT * FROM ATMs

INSERT INTO Transactions (ATM_id, card_id, sum) VALUES
(1, 1, 200),
(1, 2, 500),
(2, 1, 30),
(3, 1, 450),
(1, 3, 1500),
(2, 3, 800)

SELECT * FROM Transactions

GO
CREATE OR ALTER PROCEDURE RemoveTransactions (
    @p_card_id INT
) AS
BEGIN
    IF EXISTS (
        SELECT 1 FROM Cards
        WHERE id = @p_card_id
    )
        BEGIN
            DELETE FROM Transactions
            WHERE card_id = @p_card_id

            PRINT '[SUCCESS] Transactions removed successfully.'
        END
    ELSE
        BEGIN
            PRINT '[ERROR] Card not found.'
        END
END
GO

EXECUTE RemoveTransactions 2

SELECT * FROM Transactions

GO
CREATE OR ALTER FUNCTION TotalTransactionsMore2000 () RETURNS TABLE AS
    RETURN SELECT C.id, C.number, C.CVV
    FROM Cards C
    JOIN Transactions T ON C.id = T.card_id
    GROUP BY C.id, C.number, C.CVV
    HAVING SUM(T.sum) > 2000
GO

SELECT * FROM TotalTransactionsMore2000 ()