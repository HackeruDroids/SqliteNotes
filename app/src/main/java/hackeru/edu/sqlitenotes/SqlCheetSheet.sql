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