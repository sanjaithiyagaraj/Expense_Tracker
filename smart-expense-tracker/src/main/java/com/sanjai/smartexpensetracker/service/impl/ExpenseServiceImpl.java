package com.sanjai.smartexpensetracker.service.impl;

import com.sanjai.smartexpensetracker.dto.ExpenseDto;
import com.sanjai.smartexpensetracker.entity.Category;
import com.sanjai.smartexpensetracker.entity.Expense;
import com.sanjai.smartexpensetracker.entity.User;
import com.sanjai.smartexpensetracker.exception.ApiException;
import com.sanjai.smartexpensetracker.exception.ResourceNotFoundException;
import com.sanjai.smartexpensetracker.repository.CategoryRepository;
import com.sanjai.smartexpensetracker.repository.ExpenseRepository;
import com.sanjai.smartexpensetracker.repository.UserRepository;
import com.sanjai.smartexpensetracker.service.ExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository,
                              UserRepository userRepository,
                              CategoryRepository categoryRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ExpenseDto createExpense(ExpenseDto expenseDto, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

        Category category = categoryRepository.findById(expenseDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id",
                        expenseDto.getCategoryId()));

        Expense expense = Expense.builder()
                .title(expenseDto.getTitle())
                .description(expenseDto.getDescription())
                .amount(expenseDto.getAmount())
                .expenseDate(expenseDto.getExpenseDate())
                .user(user)
                .category(category)
                .build();

        Expense savedExpense = expenseRepository.save(expense);
        return mapToDto(savedExpense);
    }

    @Override
    public ExpenseDto updateExpense(Long id, ExpenseDto expenseDto, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense", "id", id));

        if (!expense.getUser().getId().equals(user.getId())) {
            throw new ApiException(HttpStatus.FORBIDDEN, "You are not authorized to update this expense");
        }

        Category category = categoryRepository.findById(expenseDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id",
                        expenseDto.getCategoryId()));

        expense.setTitle(expenseDto.getTitle());
        expense.setDescription(expenseDto.getDescription());
        expense.setAmount(expenseDto.getAmount());
        expense.setExpenseDate(expenseDto.getExpenseDate());
        expense.setCategory(category);

        Expense updatedExpense = expenseRepository.save(expense);
        return mapToDto(updatedExpense);
    }

    @Override
    public void deleteExpense(Long id, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense", "id", id));

        if (!expense.getUser().getId().equals(user.getId())) {
            throw new ApiException(HttpStatus.FORBIDDEN, "You are not authorized to delete this expense");
        }

        expenseRepository.delete(expense);
    }

    @Override
    public ExpenseDto getExpenseById(Long id, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense", "id", id));

        if (!expense.getUser().getId().equals(user.getId())) {
            throw new ApiException(HttpStatus.FORBIDDEN, "You are not authorized to view this expense");
        }

        return mapToDto(expense);
    }

    @Override
    public List<ExpenseDto> getAllExpenses(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

        return expenseRepository.findByUserId(user.getId()).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ExpenseDto> getExpensesByCategory(Long categoryId, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

        categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        return expenseRepository.findByUserIdAndCategoryId(user.getId(), categoryId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ExpenseDto> getExpensesByDateRange(LocalDate startDate, LocalDate endDate, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

        return expenseRepository.findByUserIdAndDateRange(user.getId(), startDate, endDate).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private ExpenseDto mapToDto(Expense expense) {
        return ExpenseDto.builder()
                .id(expense.getId())
                .title(expense.getTitle())
                .description(expense.getDescription())
                .amount(expense.getAmount())
                .expenseDate(expense.getExpenseDate())
                .categoryId(expense.getCategory().getId())
                .categoryName(expense.getCategory().getCategoryName())
                .userId(expense.getUser().getId())
                .userFullName(expense.getUser().getFullName())
                .build();
    }
}
