import React, { useState, useEffect } from 'react';

function BudgetCard({ budget, onUpdateBudget }) {
  const [budgetValue, setBudgetValue] = useState('');

  useEffect(() => {
    if (budget !== undefined && budget !== null) {
      setBudgetValue(budget.monthlyBudget || budget.amount || budget || 0);
    }
  }, [budget]);

  const handleSubmit = (e) => {
    e.preventDefault();
    const value = parseFloat(budgetValue);
    if (isNaN(value) || value < 0) {
      alert('Please enter a valid budget amount.');
      return;
    }
    onUpdateBudget({ monthlyBudget: value });
  };

  return (
    <article className="budget-card">
      <div className="panel-header panel-header--compact">
        <div>
          <p className="eyebrow">Budget</p>
          <h2>Set Monthly Budget</h2>
        </div>
      </div>
      <form onSubmit={handleSubmit} className="budget-form">
        <div className="budget-form__field">
          <label htmlFor="budgetInput" className="field-label">
            Budget Amount (₹)
          </label>
          <input
            type="number"
            id="budgetInput"
            className="field-input"
            min="0"
            step="0.01"
            placeholder="Enter monthly budget"
            value={budgetValue}
            onChange={(e) => setBudgetValue(e.target.value)}
          />
        </div>
        <button type="submit" className="btn btn-primary budget-btn">
          Update Budget
        </button>
      </form>
    </article>
  );
}

export default BudgetCard;
