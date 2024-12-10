-- Step 1: Create the database
CREATE DATABASE librarydb;

-- Step 2: Use the database
USE librarydb;

-- Step 3: Create the 'books' table
CREATE TABLE books (
    BookID INT PRIMARY KEY AUTO_INCREMENT,
    Title VARCHAR(255) NOT NULL,
    Author VARCHAR(255) NOT NULL,
    Genre VARCHAR(50) NOT NULL,
    Year INT NOT NULL
);

-- Step 4: Create the 'bookissues' table
CREATE TABLE bookissues (
    IssueID INT PRIMARY KEY AUTO_INCREMENT,
    BookID INT NOT NULL,
    Name VARCHAR(255) NOT NULL,
    Contact VARCHAR(15) NOT NULL,
    IssueDate DATETIME NOT NULL,
    ReturnDate DATETIME,
    FOREIGN KEY (BookID) REFERENCES books(BookID)
);

-- Step 5: Insert sample data into 'books' table
INSERT INTO books (Title, Author, Genre, Year) VALUES
('Journey to the unknown', 'Emily Harper', 'Adventure', 2015),
('Rising of The Sun', 'James Cameron', 'Drama', 2017),
('Science of Tomorrow', 'Alan Turner', 'Fiction', 2021),
('The Enchanted Forest', 'Alice Greene', 'Fantasy', 2021),
('Mystery at the Mansion', 'Robert Black', 'Mystery', 2019),
('Cooking with Spices', 'Chef Tony Brown', 'Cooking', 2020),
('Adventures in Coding', 'Jane Doe', 'Technology', 2022),
('The Lost Treasure', 'Michael Stone', 'Thriller', 2018),
('Gardening for Beginners', 'Sarah Greenfield', 'Gardening', 2016),
('The Art of Painting', 'Anna Lee', 'Art', 2019);

-- Step 6: Insert sample data into 'bookissues' table
INSERT INTO bookissues (BookID, Name, Contact, IssueDate, ReturnDate) VALUES
(1, 'Pranav Kumar', '9123456789', '2024-12-01 09:15:23', '2024-12-02 10:45:30'),
(2, 'Ananya Verma', '9234567890', '2024-12-03 11:25:33', NULL),
(3, 'Aditya Singh', '9345678901', '2024-12-04 14:50:45', '2024-12-05 16:35:20'),
(4, 'Himanshu Kumawat', '9456789012', '2024-12-06 12:40:50', NULL),
(5, 'Kavya Gahlot', '9567890123', '2024-12-07 13:55:10', '2024-12-08 15:30:25'),
(6, 'Vivaan Kapoor', '9678901234', '2024-12-08 08:45:00', NULL),
(7, 'Aditi Desai', '9789012345', '2024-12-09 10:35:20', '2024-12-10 12:25:35'),
(8, 'Rajesh Gupta', '9890123456', '2024-12-10 11:50:30', NULL),
(9, 'Neha Kapoor', '9001234567', '2024-12-11 14:10:40', '2024-12-12 16:00:00'),
(10, 'Aryan Joshi', '9112345678', '2024-12-12 15:55:50', NULL);

-- Verify the structure
SHOW TABLES;

-- Verify data
SELECT * FROM books;
SELECT * FROM bookissues;
