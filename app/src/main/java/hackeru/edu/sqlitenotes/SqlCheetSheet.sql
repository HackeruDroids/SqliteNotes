-- This is a comment in SQL
-- SQL Is NOT case Sensitive, unless -> mentioned otherwise.

--Select: get data from a table

--Select *     :Select all the columns
SELECT *
FROM Products

-- Select someColumns
-- FROM TableName
SELECT ProductId, ProductName, Price, CategoryId
FROM Products


-- SELECT colName1, colName2, colName...
-- FROM TableName


-- WHERE   -> Filter the results with condintions:

-- Basic syntax:
SELECT column1, column2, ...
FROM table_name
WHERE condition;

SELECT *
FROM Products
WHERE Price > 25

SELECT *
FROM Products
WHERE Price = 30


-- NOT:   <> or !=
SELECT *
FROM Products
WHERE Price <> 30

SELECT *
FROM Products
WHERE Price != 30

-- BETWEEN:
SELECT *
FROM Products
WHERE Price BETWEEN 30 AND 40


-- IN:
SELECT *
FROM Products
WHERE CategoryID IN (3, 5, 7)


-- LIKE:
-- Starts With:
SELECT *
FROM Products
WHERE ProductName LIKE 'Ch%'

-- LIKE, Contains
SELECT *
FROM Products
WHERE ProductName LIKE '%x%'

-- LIKE EndsWith:
SELECT *
FROM Products
WHERE ProductName LIKE '%x'

-- AND
SELECT *
FROM Products
WHERE ProductName LIKE '%x' AND Price > 20

-- OR... IN
--Find customers that reside in Canada OR Germany.
--Once with OR And Once with IN

SELECT *
FROM Customers
WHERE Country = 'Germany' OR Country = 'Canada'

SELECT *
FROM Customers
WHERE Country IN ('Germany', 'Canada')

-- ORDER BY
SELECT *
FROM Customers
WHERE CustomerName LIKE 'A%'
ORDER BY ContactName -- ASC


SELECT *
FROM Customers
-- WHERE CustomerName LIKE 'A%'
ORDER BY ContactName DESC-- ASC


SELECT *
FROM Customers
-- WHERE CustomerName LIKE 'A%'
ORDER BY Country ASC, ContactName ASC


-- JOIN:
-- Orders that ordered Chais
SELECT *
FROM Products JOIN OrderDetails
ON Products.ProductID = OrderDetails.ProductID
WHERE Products.ProductId = 1

-- JOIN JOIN JOIN JOIN...
SELECT CustomerName, Quantity
FROM Products JOIN OrderDetails
ON Products.ProductID = OrderDetails.ProductID
JOIN Orders
ON orders.OrderID = OrderDetails.OrderID
JOIN Customers
ON Customers.CustomerID = Orders.CustomerID
WHERE Products.ProductId = 1
ORDER BY Quantity DESC


-- CREATE TABLE:
CREATE TABLE table_name (
    column1 datatype,
    column2 datatype,
    column3 datatype,
   ....
);

-- 1:1
CREATE TABLE Students(
	StudentID INTEGER,
    firstName TEXT,
    lastName TEXT,
	socialID TEXT,
	phone TEXT
)

-- One To Many 1:*
CREATE TABLE Students(
	StudentID INTEGER,
    firstName TEXT,
    lastName TEXT,
	socialID TEXT,
)

CREATE TABLE Phones(
	PhoneID INTEGER,
    number TEXT,
    studentID INTEGER
)

--Exercise:
-----------------------------------
--Create 3 Tables:
--Courses, Student, StudentCourses
--
--Course:
--id, courseName, Syllabus
--
--Student:
--id, firstName, lastName, socialID
--
--StudentCourses:
--id, studentID, courseID
-------------------------------------

INSERT INTO Students(studentID, firstName, lastName, socialID)
VALUES(1, 'Moshe', 'Doe', '06740684680')

INSERT INTO Students(studentID, firstName, lastName, socialID)
VALUES(2, 'Vice', 'Hagev', '0654068089')

--  A Little problematic with the id
-- AUTO_INCREMENT:
CREATE TABLE People (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    lastName TEXT NOT NULL,
    firstName TEXT,
    age INTEGER
);

-- DELETE:
DELETE FROM Customers -- Danger!

DELETE FROM Customers
WHERE Country = 'Mexico'

UPDATE Customers
SET Country = 'Israel' -- Dangerous

-- better:
UPDATE Customers
SET Country = 'ISRAEL'
WHERE Country = 'Sweden'

--Constraints:
-- NOT NULL
-- UNIQUE
-- PRIMARY KEY
CREATE TABLE Students(
	id INTEGER PRIMARY KEY NOT NULL, -- it's now more efficient to look up by id... Supports JOINS
    firstName TEXT NOT NULL,
    lastName TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE
)

-- DEFAULT:
CREATE TABLE Order(
	id INTEGER PRIMARY KEY NOT NULL, -- it's now more efficient to look up by id... Supports JOINS
    customerID INTEGER NOT NULL,
    orderDate DATE DEFAULT GETDATE()
)

-- CHECK
CREATE TABLE Persons (
    ID INTEGER NOT NULL,
    LastName TEXT NOT NULL,
    FirstName TEXT,
    Age INTEGER CHECK (Age>=18)
);


-- DROP TABLE:
DROP TABLE Students;

CREATE TABLE Notes(_ID INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL,content TEXT);