package com.expense.tracker.service;

import com.expense.tracker.dto.BudgetDTO;
import com.expense.tracker.entity.Budget;
import com.expense.tracker.repository.BudgetRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BudgetService {

    private final BudgetRepository budgetRepository;

    public BudgetService(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    public BudgetDTO getBudget() {
        Budget budget = budgetRepository.findAll()
                .stream()
                .findFirst()
                .orElseGet(() -> {
                    Budget newBudget = Budget.builder()
                            .monthlyBudget(BigDecimal.ZERO)
                            .build();
                    return budgetRepository.save(newBudget);
                });

        BudgetDTO dto = new BudgetDTO();
        dto.setMonthlyBudget(budget.getMonthlyBudget());
        return dto;
    }

    public BudgetDTO updateBudget(BudgetDTO dto) {
        Budget budget = budgetRepository.findAll()
                .stream()
                .findFirst()
                .orElseGet(() -> {
                    Budget newBudget = Budget.builder()
                            .monthlyBudget(BigDecimal.ZERO)
                            .build();
                    return budgetRepository.save(newBudget);
                });

        budget.setMonthlyBudget(dto.getMonthlyBudget());
        Budget updatedBudget = budgetRepository.save(budget);

        BudgetDTO responseDto = new BudgetDTO();
        responseDto.setMonthlyBudget(updatedBudget.getMonthlyBudget());
        return responseDto;
    }
}
