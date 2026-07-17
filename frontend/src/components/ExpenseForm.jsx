import React, { useState, useEffect } from 'react';

const CATEGORIES = ['Food', 'Transport', 'Shopping', 'Entertainment', 'Bills', 'Health', 'Education', 'Other'];

const initialFormData = {
  title: '',
  category: '',
  amount: '',
  date: '',
  notes: '',
};

function ExpenseForm({ onSubmit, editingExpense, onCancel }) {
  const [formData, setFormData] = useState(initialFormData);

  useEffect(() => {
    if (editingExpense) {
      setFormData({
        title: editingExpense.title || '',
        category: editingExpense.category || '',
        amount: editingExpense.amount || '',
        date: editingExpense.date || '',
        notes: editingExpense.notes || '',
      });
    } else {
      setFormData(initialFormData);
    }
  }, [editingExpense]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    if (!formData.title.trim()) {
      alert('Title is required.');
      return;
    }
    if (!formData.category) {
      alert('Category is required.');
      return;
    }
    if (!formData.amount || parseFloat(formData.amount) <= 0) {
      alert('Please enter a valid amount.');
      return;
    }
    if (!formData.date) {
      alert('Date is required.');
      return;
    }

    const expenseData = {
      title: formData.title.trim(),
      category: formData.category,
      amount: parseFloat(formData.amount),
      date: formData.date,
      notes: formData.notes.trim(),
    };

    if (editingExpense) {
      onSubmit(editingExpense.id, expenseData);
    } else {
      onSubmit(expenseData);
    }

    setFormData(initialFormData);
  };

  return (
    <article className="form-card">
      <div className="panel-header panel-header--compact">
        <div>
          <p className="eyebrow">Entry</p>
          <h2>{editingExpense ? 'Edit Expense' : 'Add Expense'}</h2>
        </div>
      </div>

      <form className="expense-form" onSubmit={handleSubmit}>
        <div className="form-grid">
          <div className="field-group">
            <label htmlFor="title" className="field-label">
              Title <span className="required-mark">*</span>
            </label>
            <input
              type="text"
              className="field-input"
              id="title"
              name="title"
              placeholder="Enter expense title"
              value={formData.title}
              onChange={handleChange}
              required
            />
          </div>

          <div className="field-group">
            <label htmlFor="category" className="field-label">
              Category <span className="required-mark">*</span>
            </label>
            <select
              className="field-select"
              id="category"
              name="category"
              value={formData.category}
              onChange={handleChange}
              required
            >
              <option value="">Select Category</option>
              {CATEGORIES.map((cat) => (
                <option key={cat} value={cat}>
                  {cat}
                </option>
              ))}
            </select>
          </div>

          <div className="field-group">
            <label htmlFor="amount" className="field-label">
              Amount (₹) <span className="required-mark">*</span>
            </label>
            <input
              type="number"
              className="field-input"
              id="amount"
              name="amount"
              placeholder="0.00"
              min="0.01"
              step="0.01"
              value={formData.amount}
              onChange={handleChange}
              required
            />
          </div>

          <div className="field-group">
            <label htmlFor="date" className="field-label">
              Date <span className="required-mark">*</span>
            </label>
            <input
              type="date"
              className="field-input"
              id="date"
              name="date"
              value={formData.date}
              onChange={handleChange}
              required
            />
          </div>

          <div className="field-group field-group--full">
            <label htmlFor="notes" className="field-label">
              Notes
            </label>
            <textarea
              className="field-textarea"
              id="notes"
              name="notes"
              rows="3"
              placeholder="Optional notes"
              value={formData.notes}
              onChange={handleChange}
            ></textarea>
          </div>
        </div>

        <div className="form-actions">
          <button type="submit" className="btn btn-primary flex-grow-1">
            {editingExpense ? 'Update Expense' : 'Add Expense'}
          </button>
          {editingExpense && (
            <button type="button" className="btn btn-secondary flex-grow-1" onClick={onCancel}>
              Cancel
            </button>
          )}
        </div>
      </form>
    </article>
  );
}

export default ExpenseForm;
