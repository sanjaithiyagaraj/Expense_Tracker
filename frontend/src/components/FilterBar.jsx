import React, { useState } from 'react';
import { FaDownload, FaTimes } from 'react-icons/fa';

const CATEGORIES = ['Food', 'Transport', 'Shopping', 'Entertainment', 'Bills', 'Health', 'Education', 'Other'];

const MONTHS = [
  { value: '01', label: 'January' },
  { value: '02', label: 'February' },
  { value: '03', label: 'March' },
  { value: '04', label: 'April' },
  { value: '05', label: 'May' },
  { value: '06', label: 'June' },
  { value: '07', label: 'July' },
  { value: '08', label: 'August' },
  { value: '09', label: 'September' },
  { value: '10', label: 'October' },
  { value: '11', label: 'November' },
  { value: '12', label: 'December' },
];

const SORT_OPTIONS = [
  { value: 'date-desc', label: 'Latest First' },
  { value: 'date-asc', label: 'Oldest First' },
  { value: 'amount-desc', label: 'Highest Amount' },
  { value: 'amount-asc', label: 'Lowest Amount' },
];

const generateYears = () => {
  const currentYear = new Date().getFullYear();
  const years = [];
  for (let y = currentYear + 1; y >= 2020; y--) {
    years.push(y);
  }
  return years;
};

const YEARS = generateYears();

function FilterBar({ onFilter, onSortChange, sortBy, onExport }) {
  const [category, setCategory] = useState('');
  const [selectedMonth, setSelectedMonth] = useState('');
  const [selectedYear, setSelectedYear] = useState('');

  const buildMonthString = (month, year) => {
    if (month && year) {
      return `${year}-${month}`;
    }
    return '';
  };

  const handleCategoryChange = (e) => {
    const value = e.target.value;
    setCategory(value);
    onFilter(value, buildMonthString(selectedMonth, selectedYear));
  };

  const handleMonthSelectChange = (e) => {
    const value = e.target.value;
    setSelectedMonth(value);
    onFilter(category, buildMonthString(value, selectedYear));
  };

  const handleYearChange = (e) => {
    const value = e.target.value;
    setSelectedYear(value);
    onFilter(category, buildMonthString(selectedMonth, value));
  };

  const handleSortChange = (e) => {
    onSortChange(e.target.value);
  };

  const handleClearFilters = () => {
    setCategory('');
    setSelectedMonth('');
    setSelectedYear('');
    onFilter('', '');
  };

  const hasFilters = category || selectedMonth || selectedYear;

  return (
    <div className="filter-bar">
      <div className="filter-field">
        <label htmlFor="filterCategory" className="field-label">
          Category
        </label>
        <select
          className="field-select"
          id="filterCategory"
          value={category}
          onChange={handleCategoryChange}
        >
          <option value="">All Categories</option>
          {CATEGORIES.map((cat) => (
            <option key={cat} value={cat}>
              {cat}
            </option>
          ))}
        </select>
      </div>

      <div className="filter-field">
        <label htmlFor="filterMonth" className="field-label">
          Month
        </label>
        <select
          className="field-select"
          id="filterMonth"
          value={selectedMonth}
          onChange={handleMonthSelectChange}
        >
          <option value="">All Months</option>
          {MONTHS.map((m) => (
            <option key={m.value} value={m.value}>
              {m.label}
            </option>
          ))}
        </select>
      </div>

      <div className="filter-field">
        <label htmlFor="filterYear" className="field-label">
          Year
        </label>
        <select
          className="field-select"
          id="filterYear"
          value={selectedYear}
          onChange={handleYearChange}
        >
          <option value="">All Years</option>
          {YEARS.map((y) => (
            <option key={y} value={y}>
              {y}
            </option>
          ))}
        </select>
      </div>

      <div className="filter-field">
        <label htmlFor="sortSelect" className="field-label">
          Sort By
        </label>
        <select
          className="field-select"
          id="sortSelect"
          value={sortBy}
          onChange={handleSortChange}
        >
          {SORT_OPTIONS.map((opt) => (
            <option key={opt.value} value={opt.value}>
              {opt.label}
            </option>
          ))}
        </select>
      </div>

      <div className="filter-actions">
        {hasFilters && (
          <button
            className="btn btn-secondary btn-sm"
            onClick={handleClearFilters}
            title="Clear Filters"
          >
            <FaTimes /> Clear
          </button>
        )}
        <button className="btn btn-success btn-sm" onClick={onExport} title="Export CSV">
          <FaDownload /> Export CSV
        </button>
      </div>
    </div>
  );
}

export default FilterBar;
