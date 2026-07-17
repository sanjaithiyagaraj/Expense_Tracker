import React from 'react';
import { FaWallet } from 'react-icons/fa';

function Navbar() {
  return (
    <nav className="topbar">
      <div className="topbar-inner">
        <div className="brand-lockup">
          <span className="brand-icon">
            <FaWallet size={20} />
          </span>
          <div>
            <div className="brand-title">Smart Expense Tracker</div>
            <div className="brand-subtitle">Your money, managed cleanly</div>
          </div>
        </div>
      </div>
    </nav>
  );
}

export default Navbar;
