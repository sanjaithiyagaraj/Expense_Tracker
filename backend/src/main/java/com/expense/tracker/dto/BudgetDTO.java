package com.expense.tracker.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BudgetDTO {

    @NotNull(message = "Monthly budget is required")
    @PositiveOrZero(message = "Monthly budget must be zero or positive")
    private BigDecimal monthlyBudget;
}
