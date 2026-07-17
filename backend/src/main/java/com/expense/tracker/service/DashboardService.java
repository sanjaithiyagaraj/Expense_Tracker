package com.expense.tracker.service;

import com.expense.tracker.dto.DashboardDTO;
import com.expense.tracker.dto.ExpenseDTO;
import com.expense.tracker.entity.Expense;
import com.expense.tracker.repository.ExpenseRepository;
import com.expense.tracker.util.DateUtil;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private final ExpenseRepository expenseRepository;
    private final BudgetService budgetService;

    public DashboardService(ExpenseRepository expenseRepository, BudgetService budgetService) {
        this.expenseRepository = expenseRepository;
        this.budgetService = budgetService;
    }

    public DashboardDTO getDashboard(int month, int year) {
        BigDecimal monthlyBudget = budgetService.getBudget().getMonthlyBudget();
        BigDecimal totalExpenses;
        long totalTransactions;
        List<ExpenseDTO> recentExpenses;

        if (month > 0 && year > 0) {
            // Filter by specific month
            LocalDate start = DateUtil.getStartOfMonth(month, year);
            LocalDate end = DateUtil.getEndOfMonth(month, year);

            totalExpenses = expenseRepository.sumAmountByDateBetween(start, end);
            totalTransactions = expenseRepository.countByDateBetween(start, end);
            recentExpenses = expenseRepository
                    .findTop5ByDateBetweenOrderByDateDescIdDesc(start, end)
                    .stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());
        } else {
            // All-time mode: show totals across ALL expenses
            totalExpenses = expenseRepository.sumAllAmounts();
            totalTransactions = expenseRepository.count();
            recentExpenses = expenseRepository
                    .findAll(Sort.by(Sort.Direction.DESC, "date", "id"))
                    .stream()
                    .limit(5)
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());
        }

        BigDecimal remainingBudget = monthlyBudget.subtract(totalExpenses);
        boolean budgetExceeded = monthlyBudget.compareTo(BigDecimal.ZERO) > 0
                && totalExpenses.compareTo(monthlyBudget) > 0;

        return DashboardDTO.builder()
                .monthlyBudget(monthlyBudget)
                .totalExpenses(totalExpenses)
                .remainingBudget(remainingBudget)
                .totalTransactions(totalTransactions)
                .budgetExceeded(budgetExceeded)
                .recentExpenses(recentExpenses)
                .build();
    }

    private ExpenseDTO mapToDTO(Expense expense) {
        return ExpenseDTO.builder()
                .id(expense.getId())
                .title(expense.getTitle())
                .category(expense.getCategory())
                .amount(expense.getAmount())
                .date(expense.getDate())
                .notes(expense.getNotes())
                .build();
    }
}
