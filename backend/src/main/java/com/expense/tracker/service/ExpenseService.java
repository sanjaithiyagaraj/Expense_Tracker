package com.expense.tracker.service;

import com.expense.tracker.dto.ExpenseDTO;
import com.expense.tracker.entity.Expense;
import com.expense.tracker.exception.ResourceNotFoundException;
import com.expense.tracker.repository.ExpenseRepository;
import com.expense.tracker.util.DateUtil;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public List<ExpenseDTO> getAllExpenses(String sortBy) {
        Sort sort = switch (sortBy != null ? sortBy : "date-desc") {
            case "date-asc" -> Sort.by(Sort.Direction.ASC, "date").and(Sort.by(Sort.Direction.ASC, "id"));
            case "amount-desc" -> Sort.by(Sort.Direction.DESC, "amount").and(Sort.by(Sort.Direction.DESC, "id"));
            case "amount-asc" -> Sort.by(Sort.Direction.ASC, "amount").and(Sort.by(Sort.Direction.ASC, "id"));
            default -> Sort.by(Sort.Direction.DESC, "date").and(Sort.by(Sort.Direction.DESC, "id"));
        };

        return expenseRepository.findAll(sort)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public ExpenseDTO getExpenseById(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + id));
        return mapToDTO(expense);
    }

    public ExpenseDTO createExpense(ExpenseDTO dto) {
        Expense expense = mapToEntity(dto);
        Expense savedExpense = expenseRepository.save(expense);
        return mapToDTO(savedExpense);
    }

    public ExpenseDTO updateExpense(Long id, ExpenseDTO dto) {
        Expense existingExpense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + id));

        existingExpense.setTitle(dto.getTitle());
        existingExpense.setCategory(dto.getCategory());
        existingExpense.setAmount(dto.getAmount());
        existingExpense.setDate(dto.getDate());
        existingExpense.setNotes(dto.getNotes());

        Expense updatedExpense = expenseRepository.save(existingExpense);
        return mapToDTO(updatedExpense);
    }

    public void deleteExpense(Long id) {
        if (!expenseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Expense not found with id: " + id);
        }
        expenseRepository.deleteById(id);
    }

    public List<ExpenseDTO> searchExpenses(String title) {
        return expenseRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<ExpenseDTO> filterExpenses(String category, Integer month, Integer year) {
        List<Expense> expenses;

        if (category != null && !category.isEmpty() && month != null && year != null) {
            LocalDate start = DateUtil.getStartOfMonth(month, year);
            LocalDate end = DateUtil.getEndOfMonth(month, year);
            expenses = expenseRepository.findByCategoryAndDateBetween(category, start, end);
        } else if (category != null && !category.isEmpty()) {
            expenses = expenseRepository.findByCategory(category);
        } else if (month != null && year != null) {
            LocalDate start = DateUtil.getStartOfMonth(month, year);
            LocalDate end = DateUtil.getEndOfMonth(month, year);
            expenses = expenseRepository.findByDateBetween(start, end);
        } else {
            expenses = expenseRepository.findAll();
        }

        return expenses.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
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

    private Expense mapToEntity(ExpenseDTO dto) {
        return Expense.builder()
                .title(dto.getTitle())
                .category(dto.getCategory())
                .amount(dto.getAmount())
                .date(dto.getDate())
                .notes(dto.getNotes())
                .build();
    }
}
