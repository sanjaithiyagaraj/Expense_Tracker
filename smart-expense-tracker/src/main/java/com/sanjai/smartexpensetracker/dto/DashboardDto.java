package com.sanjai.smartexpensetracker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardDto {

    private BigDecimal totalSpending;
    private BigDecimal monthlySpending;
    private Long expenseCount;
    private ExpenseDto highestExpense;
    private List<CategorySpendingDto> categoryWiseSpending;
}
