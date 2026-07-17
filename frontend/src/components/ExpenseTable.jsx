import React from 'react';
import { FaEdit, FaTrash, FaInbox } from 'react-icons/fa';

const categoryBadgeClass = {
  Food: 'badge-food',
  Transport: 'badge-transport',
  Shopping: 'badge-shopping',
  Entertainment: 'badge-entertainment',
  Bills: 'badge-bills',
  Health: 'badge-health',
  Education: 'badge-education',
  Other: 'badge-other',
};

function ExpenseTable({ expenses, onEdit, onDelete }) {
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

  if (!expenses || expenses.length === 0) {
    return (
      <div className="card expense-table mt-3">
        <div className="card-body empty-state">
          <FaInbox />
          <p className="mb-0">No expenses found.</p>
        </div>
      </div>
    );
  }

  return (
    <article className="expense-table">
      <div className="table-responsive">
        <table className="expense-table__body">
          <thead>
            <tr>
              <th>#</th>
              <th>Title</th>
              <th>Category</th>
              <th>Amount</th>
              <th>Date</th>
              <th>Notes</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {expenses.map((expense, index) => (
              <tr key={expense.id || index}>
                <td>{index + 1}</td>
                <td className="cell-title">{expense.title}</td>
                <td>
                  <span className={`badge ${categoryBadgeClass[expense.category] || 'badge-other'}`}>
                    {expense.category}
                  </span>
                </td>
                <td className="cell-amount">{formatCurrency(expense.amount)}</td>
                <td>{formatDate(expense.date)}</td>
                <td className="notes-cell" title={expense.notes || ''}>
                  {expense.notes || '—'}
                </td>
                <td>
                  <div className="table-actions">
                    <button
                      className="icon-button icon-button--edit"
                      onClick={() => onEdit(expense)}
                      title="Edit"
                    >
                      <FaEdit />
                    </button>
                    <button
                      className="icon-button icon-button--delete"
                      onClick={() => onDelete(expense.id)}
                      title="Delete"
                    >
                      <FaTrash />
                    </button>
                  </div>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </article>
  );
}

export default ExpenseTable;
