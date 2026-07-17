-- =====================================================
-- Smart Expense Tracker — Database Schema
-- Database: MySQL 8
-- =====================================================

-- 1. Create the database
CREATE DATABASE IF NOT EXISTS expense_tracker_db;
USE expense_tracker_db;

-- 2. Expenses table
CREATE TABLE IF NOT EXISTS expenses (
    id       BIGINT        AUTO_INCREMENT PRIMARY KEY,
    title    VARCHAR(100)  NOT NULL,
    category VARCHAR(50)   NOT NULL,
    amount   DECIMAL(10,2) NOT NULL,
    date     DATE          NOT NULL,
    notes    TEXT
);

-- 3. Budget table
CREATE TABLE IF NOT EXISTS budget (
    id             BIGINT        AUTO_INCREMENT PRIMARY KEY,
    monthly_budget DECIMAL(10,2) DEFAULT 0.00
);

-- 4. Insert default budget
INSERT INTO budget (monthly_budget) VALUES (5000.00);

-- 5. Insert 10 sample expense records (July 2026)
INSERT INTO expenses (title, category, amount, date, notes) VALUES
('Grocery Shopping',      'Food',          1850.00, '2026-07-01', 'Weekly groceries from supermarket'),
('Uber to Office',        'Transport',      320.00, '2026-07-03', 'Cab fare during rain'),
('Netflix Subscription',  'Entertainment',  649.00, '2026-07-05', 'Monthly streaming subscription'),
('Electricity Bill',      'Bills',         2200.00, '2026-07-07', 'June electricity bill payment'),
('New Running Shoes',     'Shopping',      3499.00, '2026-07-09', 'Nike running shoes from mall'),
('General Health Checkup','Health',        1500.00, '2026-07-11', 'Annual health checkup at Apollo'),
('Udemy Course',          'Education',      499.00, '2026-07-13', 'Spring Boot masterclass course'),
('Team Lunch',            'Food',           750.00, '2026-07-15', 'Lunch with project team'),
('Metro Card Recharge',   'Transport',      500.00, '2026-07-18', 'Monthly metro pass recharge'),
('Movie Night',           'Entertainment',  450.00, '2026-07-20', 'Movie tickets and snacks');
