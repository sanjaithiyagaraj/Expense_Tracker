package com.expense.tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardDTO {

    private BigDecimal monthlyBudget;
    private BigDecimal totalExpenses;
    private BigDecimal remainingBudget;
    private long totalTransactions;
    private boolean budgetExceeded;
    private List<ExpenseDTO> recentExpenses;
}
