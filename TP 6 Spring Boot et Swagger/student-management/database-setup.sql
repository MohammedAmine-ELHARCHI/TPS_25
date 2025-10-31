-- SQL Script for Student Management Application
-- This script sets up the database for the Spring Boot application

-- Create the database
CREATE DATABASE IF NOT EXISTS studentdb;

-- Use the database
USE studentdb;

-- The Student table will be auto-created by Hibernate
-- But here's the schema for reference:
/*
CREATE TABLE student (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(255),
    prenom VARCHAR(255),
    date_naissance DATE
);
*/

-- Sample data for testing (optional)
-- Uncomment these lines if you want to insert test data manually:

/*
INSERT INTO student (nom, prenom, date_naissance) VALUES
('LACHGAR', 'Mohamed', '1985-09-01'),
('ALAMI', 'Ahmed', '1990-05-15'),
('BENNANI', 'Fatima', '1992-03-20'),
('IDRISSI', 'Karim', '1988-11-10');
*/

-- Query to verify data
-- SELECT * FROM student;

-- Query to count students by year
-- SELECT YEAR(date_naissance) as year, COUNT(*) as count 
-- FROM student 
-- GROUP BY YEAR(date_naissance);
