import React, { useState } from 'react';
import { FaSearch } from 'react-icons/fa';

function SearchBar({ onSearch }) {
  const [term, setTerm] = useState('');

  const handleChange = (e) => {
    const value = e.target.value;
    setTerm(value);
    if (value === '') {
      onSearch('');
    }
  };

  const handleKeyDown = (e) => {
    if (e.key === 'Enter') {
      e.preventDefault();
      onSearch(term);
    }
  };

  return (
    <div className="search-bar">
      <label htmlFor="searchExpenses" className="sr-only">
        Search expenses by title
      </label>
      <div className="search-input-wrap">
        <span className="search-icon">
          <FaSearch />
        </span>
        <input
          type="text"
          className="search-input"
          placeholder="Search expenses by title..."
          value={term}
          onChange={handleChange}
          onKeyDown={handleKeyDown}
          id="searchExpenses"
        />
      </div>
    </div>
  );
}

export default SearchBar;
