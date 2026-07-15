package com.sanjai.smartexpensetracker.service;

import com.sanjai.smartexpensetracker.dto.ExpenseDto;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseService {

    ExpenseDto createExpense(ExpenseDto expenseDto, String email);

    ExpenseDto updateExpense(Long id, ExpenseDto expenseDto, String email);

    void deleteExpense(Long id, String email);

    ExpenseDto getExpenseById(Long id, String email);

    List<ExpenseDto> getAllExpenses(String email);

    List<ExpenseDto> getExpensesByCategory(Long categoryId, String email);

    List<ExpenseDto> getExpensesByDateRange(LocalDate startDate, LocalDate endDate, String email);
}
