# 💰 Smart Expense Tracker

![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![React](https://img.shields.io/badge/React-18-61DAFB?style=for-the-badge&logo=react&logoColor=black)
![MySQL](https://img.shields.io/badge/MySQL-8-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Vite](https://img.shields.io/badge/Vite-5-646CFF?style=for-the-badge&logo=vite&logoColor=white)
![Bootstrap](https://img.shields.io/badge/Bootstrap-5-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white)

A full-stack expense tracking application built with **Spring Boot 3** and **React 18**. Track daily expenses, set monthly budgets, visualize spending patterns, and export data — all from a clean, responsive interface.

---

## 🛠️ Tech Stack

| Layer        | Technologies                                                  |
|-------------|---------------------------------------------------------------|
| **Backend**  | Java 17, Spring Boot 3, Spring Data JPA, Hibernate, Maven, Lombok, Bean Validation |
| **Frontend** | React 18, Vite, Axios, Bootstrap 5, React Icons               |
| **Database** | MySQL 8                                                        |
| **Tools**    | IntelliJ IDEA, VS Code, Postman, Git                          |

---

## ✨ Features

- 📊 **Dashboard** — Monthly spending summary with category-wise breakdown
- ➕ **CRUD Operations** — Add, view, edit, and delete expenses
- 💵 **Budget Management** — Set and update monthly budgets with remaining balance tracking
- 🔍 **Search** — Search expenses by title keyword
- 🏷️ **Filter** — Filter expenses by category, month, and year
- ⬆️⬇️ **Sort** — Sort expenses by date or amount (ascending/descending)
- 📁 **CSV Export** — Export all expenses as a downloadable CSV file
- 📱 **Responsive Design** — Fully responsive UI powered by Bootstrap 5

---

## 🏗️ Project Architecture

### Layered Architecture

```
┌──────────────────────────────────────────────────────┐
│                   React Frontend                     │
│              (Components + Pages + Axios)             │
└──────────────────────┬───────────────────────────────┘
                       │  HTTP (REST API)
                       ▼
┌──────────────────────────────────────────────────────┐
│                 Spring Boot Backend                   │
│  ┌──────────┐  ┌──────────┐  ┌────────────────────┐  │
│  │Controller│→ │ Service  │→ │   Repository (JPA) │  │
│  └──────────┘  └──────────┘  └────────────────────┘  │
└──────────────────────┬───────────────────────────────┘
                       │  JDBC / Hibernate
                       ▼
┌──────────────────────────────────────────────────────┐
│                    MySQL Database                     │
│          expenses  |  budget                         │
└──────────────────────────────────────────────────────┘
```

### Folder Structure

```
smart-expense-tracker/
├── backend/
│   ├── src/main/java/com/sanjai/smartexpensetracker/
│   │   ├── controller/
│   │   │   ├── ExpenseController.java
│   │   │   ├── BudgetController.java
│   │   │   └── DashboardController.java
│   │   ├── service/
│   │   │   ├── ExpenseService.java
│   │   │   └── BudgetService.java
│   │   ├── repository/
│   │   │   ├── ExpenseRepository.java
│   │   │   └── BudgetRepository.java
│   │   ├── model/
│   │   │   ├── Expense.java
│   │   │   └── Budget.java
│   │   ├── dto/
│   │   │   └── DashboardDTO.java
│   │   ├── config/
│   │   │   └── CorsConfig.java
│   │   └── SmartExpenseTrackerApplication.java
│   ├── src/main/resources/
│   │   └── application.properties
│   └── pom.xml
│
├── frontend/
│   ├── src/
│   │   ├── components/
│   │   │   ├── Dashboard.jsx
│   │   │   ├── ExpenseList.jsx
│   │   │   ├── ExpenseForm.jsx
│   │   │   ├── BudgetCard.jsx
│   │   │   └── Navbar.jsx
│   │   ├── services/
│   │   │   └── api.js
│   │   ├── App.jsx
│   │   ├── App.css
│   │   └── main.jsx
│   ├── index.html
│   ├── package.json
│   └── vite.config.js
│
├── database/
│   └── schema.sql
│
├── postman/
│   └── SmartExpenseTracker.postman_collection.json
│
└── README.md
```

---

## 🗄️ Database Schema

### `expenses` Table

| Column   | Type           | Constraints              |
|----------|----------------|--------------------------|
| id       | BIGINT         | AUTO_INCREMENT, PRIMARY KEY |
| title    | VARCHAR(100)   | NOT NULL                 |
| category | VARCHAR(50)    | NOT NULL                 |
| amount   | DECIMAL(10,2)  | NOT NULL                 |
| date     | DATE           | NOT NULL                 |
| notes    | TEXT           | —                        |

### `budget` Table

| Column         | Type           | Constraints              |
|----------------|----------------|--------------------------|
| id             | BIGINT         | AUTO_INCREMENT, PRIMARY KEY |
| monthly_budget | DECIMAL(10,2)  | DEFAULT 0.00             |

---

## 🔌 REST API Endpoints

### Expense APIs

| Method   | URL                                  | Description                       |
|----------|--------------------------------------|-----------------------------------|
| `GET`    | `/api/expenses?sort=date-desc`       | Get all expenses (sorted)         |
| `GET`    | `/api/expenses/{id}`                 | Get expense by ID                 |
| `POST`   | `/api/expenses`                      | Create a new expense              |
| `PUT`    | `/api/expenses/{id}`                 | Update an existing expense        |
| `DELETE` | `/api/expenses/{id}`                 | Delete an expense                 |
| `GET`    | `/api/expenses/search?title=keyword` | Search expenses by title          |
| `GET`    | `/api/expenses/filter?category=Food&month=7&year=2026` | Filter by category/month/year |
| `GET`    | `/api/expenses/export/csv`           | Export all expenses as CSV        |

### Budget APIs

| Method | URL            | Description                 |
|--------|----------------|-----------------------------|
| `GET`  | `/api/budget`  | Get current monthly budget  |
| `PUT`  | `/api/budget`  | Update monthly budget       |

### Dashboard API

| Method | URL                                  | Description                          |
|--------|--------------------------------------|--------------------------------------|
| `GET`  | `/api/dashboard?month=7&year=2026`   | Get dashboard summary for the month  |

---

## ✅ Prerequisites

Ensure the following are installed on your machine:

- ☕ **Java 17** — [Download](https://adoptium.net/)
- 📦 **Node.js 18+** — [Download](https://nodejs.org/)
- 🐬 **MySQL 8** — [Download](https://dev.mysql.com/downloads/)
- 🔧 **Maven** — [Download](https://maven.apache.org/download.cgi)

---

## 🚀 Getting Started

### 1. Database Setup

```bash
# Log into MySQL
mysql -u root -p

# Run the schema script
source database/schema.sql;
```

This creates the `expense_tracker_db` database, tables, a default budget of ₹5,000, and 10 sample expense records.

---

### 2. Backend Setup

```bash
# Navigate to the backend directory
cd backend/

# Update MySQL credentials in src/main/resources/application.properties
# spring.datasource.url=jdbc:mysql://localhost:3306/expense_tracker_db
# spring.datasource.username=root
# spring.datasource.password=your_password

# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

> 🟢 Backend server starts at **http://localhost:8080**

---

### 3. Frontend Setup

```bash
# Navigate to the frontend directory
cd frontend/

# Install dependencies
npm install

# Start the development server
npm run dev
```

> 🟢 Frontend app opens at **http://localhost:5173**

---

### 4. Test with Postman

1. Open **Postman**
2. Click **Import** → Select `postman/SmartExpenseTracker.postman_collection.json`
3. All 11 API requests are organized in folders — ready to test

---

## 📸 Screenshots

> _Screenshots will be added here after the application is fully deployed._

| Dashboard | Expense List | Add Expense |
|-----------|-------------|-------------|
| _coming soon_ | _coming soon_ | _coming soon_ |

---

## 📄 License

This project is open source and available for educational purposes.

---

## 👤 Author

Built for **CTS Digital Nurture Java Full Stack Developer Interview Preparation**.

---

<p align="center">
  Made using Spring Boot & React
</p>
