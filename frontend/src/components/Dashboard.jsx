import React from 'react';
import { FaWallet, FaReceipt, FaPiggyBank, FaExchangeAlt } from 'react-icons/fa';

function Dashboard({ dashboardData }) {
  const data = dashboardData || {};
  const monthlyBudget = data.monthlyBudget || 0;
  const totalExpenses = data.totalExpenses || 0;
  const remainingBudget = data.remainingBudget || 0;
  const totalTransactions = data.totalTransactions || 0;
  const budgetExceeded = data.budgetExceeded || false;
  const recentExpenses = data.recentExpenses || [];

  const formatCurrency = (amount) => {
    return new Intl.NumberFormat('en-IN', {
      style: 'currency',
      currency: 'INR',
      minimumFractionDigits: 2,
    }).format(amount);
  };

  const formatDate = (dateStr) => {
    if (!dateStr) return '';
    const date = new Date(dateStr);
    return date.toLocaleDateString('en-US', {
      month: 'short',
      day: 'numeric',
      year: 'numeric',
    });
  };

  const summaryCards = [
    {
      icon: <FaWallet />,
      value: formatCurrency(monthlyBudget),
      label: 'Monthly Budget',
      tone: 'metric-card--blue',
    },
    {
      icon: <FaReceipt />,
      value: formatCurrency(totalExpenses),
      label: 'Total Expenses',
      tone: 'metric-card--red',
    },
    {
      icon: <FaPiggyBank />,
      value: formatCurrency(remainingBudget),
      label: 'Remaining Budget',
      tone: remainingBudget < 0 ? 'metric-card--red' : 'metric-card--green',
    },
    {
      icon: <FaExchangeAlt />,
      value: totalTransactions,
      label: 'Total Transactions',
      tone: 'metric-card--violet',
    },
  ];

  return (
    <section className="dashboard-section">
      <div className="metrics-grid">
        {summaryCards.map((card) => (
          <article key={card.label} className={`metric-card ${card.tone}`}>
            <div className="metric-card__icon">{card.icon}</div>
            <div className="metric-card__value">{card.value}</div>
            <div className="metric-card__label">{card.label}</div>
          </article>
        ))}
      </div>

      {budgetExceeded && (
        <div className="budget-warning" role="alert">
          <strong>Warning:</strong> You have exceeded your monthly budget.
        </div>
      )}

      <article className="recent-expenses-card">
        <div className="panel-header">
          <div>
            <p className="eyebrow">Overview</p>
            <h2>Recent Expenses</h2>
          </div>
        </div>

        {recentExpenses.length === 0 ? (
          <p className="empty-inline">No recent expenses.</p>
        ) : (
          <div className="recent-list">
            {recentExpenses.slice(0, 5).map((expense, index) => (
              <div key={expense.id || index} className="recent-expense-item">
                <div>
                  <div className="recent-expense__title">{expense.title}</div>
                  <div className="recent-expense__date">{formatDate(expense.date)}</div>
                </div>
                <div className="recent-expense__amount">{formatCurrency(expense.amount)}</div>
              </div>
            ))}
          </div>
        )}
      </article>
    </section>
  );
}

export default Dashboard;
