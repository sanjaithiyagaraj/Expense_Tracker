import React, { useState, useEffect } from 'react';
import './App.css';
import Navbar from './components/Navbar';
import Dashboard from './components/Dashboard';
import BudgetCard from './components/BudgetCard';
import ExpenseForm from './components/ExpenseForm';
import ExpenseTable from './components/ExpenseTable';
import SearchBar from './components/SearchBar';
import FilterBar from './components/FilterBar';
import {
  getAllExpenses,
  createExpense,
  updateExpense,
  deleteExpense,
  searchExpenses,
  filterExpenses,
  exportCsv,
  getBudget,
  updateBudget,
  getDashboard,
} from './services/api';

function App() {
  const [expenses, setExpenses] = useState([]);
  const [filteredExpenses, setFilteredExpenses] = useState([]);
  const [budget, setBudget] = useState(0);
  const [dashboardData, setDashboardData] = useState({});
  const [editingExpense, setEditingExpense] = useState(null);
  const [sortBy, setSortBy] = useState('date-desc');
  const [activeMonth, setActiveMonth] = useState(null);
  const [activeYear, setActiveYear] = useState(null);

  useEffect(() => {
    refreshAllData();
  }, []);

  useEffect(() => {
    fetchExpenses(sortBy);
  }, [sortBy]);

  const fetchExpenses = async (sort) => {
    try {
      const response = await getAllExpenses(sort || sortBy);
      const data = response.data;
      setExpenses(data);
      setFilteredExpenses(data);
    } catch (error) {
      console.error('Error fetching expenses:', error);
    }
  };

  const fetchDashboard = async (month, year) => {
    try {
      const m = month || activeMonth || 0;
      const y = year || activeYear || 0;
      const response = await getDashboard(m, y);
      setDashboardData(response.data);
    } catch (error) {
      console.error('Error fetching dashboard:', error);
    }
  };

  const fetchBudget = async () => {
    try {
      const response = await getBudget();
      setBudget(response.data);
    } catch (error) {
      console.error('Error fetching budget:', error);
    }
  };

  const refreshAllData = () => {
    fetchExpenses(sortBy);
    fetchDashboard();
    fetchBudget();
  };

  const handleAddExpense = async (expenseData) => {
    try {
      await createExpense(expenseData);
      refreshAllData();
    } catch (error) {
      console.error('Error adding expense:', error);
      alert('Failed to add expense. Please try again.');
    }
  };

  const handleUpdateExpense = async (id, expenseData) => {
    try {
      await updateExpense(id, expenseData);
      setEditingExpense(null);
      refreshAllData();
    } catch (error) {
      console.error('Error updating expense:', error);
      alert('Failed to update expense. Please try again.');
    }
  };

  const handleDeleteExpense = async (id) => {
    if (!window.confirm('Are you sure you want to delete this expense?')) {
      return;
    }
    try {
      await deleteExpense(id);
      refreshAllData();
    } catch (error) {
      console.error('Error deleting expense:', error);
      alert('Failed to delete expense. Please try again.');
    }
  };

  const handleEditClick = (expense) => {
    setEditingExpense(expense);
  };

  const handleCancelEdit = () => {
    setEditingExpense(null);
  };

  const handleBudgetUpdate = async (budgetData) => {
    try {
      await updateBudget(budgetData);
      refreshAllData();
    } catch (error) {
      console.error('Error updating budget:', error);
      alert('Failed to update budget. Please try again.');
    }
  };

  const handleSearch = async (term) => {
    if (!term || term.trim() === '') {
      setFilteredExpenses(expenses);
      return;
    }
    try {
      const response = await searchExpenses(term);
      setFilteredExpenses(response.data);
    } catch (error) {
      console.error('Error searching expenses:', error);
    }
  };

  const handleFilter = async (category, monthStr) => {
    let filterMonth = null;
    let filterYear = null;

    if (monthStr) {
      const [y, m] = monthStr.split('-');
      filterMonth = parseInt(m, 10);
      filterYear = parseInt(y, 10);
    }

    setActiveMonth(filterMonth);
    setActiveYear(filterYear);

    if (filterMonth && filterYear) {
      fetchDashboard(filterMonth, filterYear);
    } else {
      fetchDashboard(0, 0);
    }

    if (!category && !monthStr) {
      setFilteredExpenses(expenses);
      return;
    }

    try {
      const params = {};
      if (category) params.category = category;
      if (filterMonth && filterYear) {
        params.month = filterMonth;
        params.year = filterYear;
      }
      const response = await filterExpenses(params);
      setFilteredExpenses(response.data);
    } catch (error) {
      console.error('Error filtering expenses:', error);
    }
  };

  const handleSortChange = (sort) => {
    setSortBy(sort);
  };

  const handleExportCsv = async () => {
    try {
      const response = await exportCsv();
      const url = window.URL.createObjectURL(new Blob([response.data]));
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', 'expenses.csv');
      document.body.appendChild(link);
      link.click();
      link.remove();
      window.URL.revokeObjectURL(url);
    } catch (error) {
      console.error('Error exporting CSV:', error);
      alert('Failed to export CSV. Please try again.');
    }
  };

  return (
    <div className="app-shell">
      <Navbar />
      <main className="app-main">
        <div className="dashboard-layout">
          <Dashboard dashboardData={dashboardData} />
          <BudgetCard budget={budget} onUpdateBudget={handleBudgetUpdate} />
          
          <div className="content-grid">
            <ExpenseForm
              onSubmit={editingExpense ? handleUpdateExpense : handleAddExpense}
              editingExpense={editingExpense}
              onCancel={handleCancelEdit}
            />
            
            <div className="dashboard-section">
              <SearchBar onSearch={handleSearch} />
              <FilterBar
                onFilter={handleFilter}
                onSortChange={handleSortChange}
                sortBy={sortBy}
                onExport={handleExportCsv}
              />
              <ExpenseTable
                expenses={filteredExpenses}
                onEdit={handleEditClick}
                onDelete={handleDeleteExpense}
              />
            </div>
          </div>
        </div>
      </main>
    </div>
  );
}

export default App;
