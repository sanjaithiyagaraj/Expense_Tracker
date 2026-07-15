package com.sanjai.smartexpensetracker.controller;

import com.sanjai.smartexpensetracker.dto.ApiResponse;
import com.sanjai.smartexpensetracker.dto.ExpenseDto;
import com.sanjai.smartexpensetracker.service.ExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@Tag(name = "Expenses", description = "Expense Management APIs")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @Operation(summary = "Create a new expense")
    @PostMapping
    public ResponseEntity<ApiResponse<ExpenseDto>> createExpense(
            @Valid @RequestBody ExpenseDto expenseDto,
            Authentication authentication) {
        ExpenseDto created = expenseService.createExpense(expenseDto, authentication.getName());
        return new ResponseEntity<>(
                ApiResponse.success("Expense created successfully", created),
                HttpStatus.CREATED);
    }

    @Operation(summary = "Update an expense")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ExpenseDto>> updateExpense(
            @PathVariable Long id,
            @Valid @RequestBody ExpenseDto expenseDto,
            Authentication authentication) {
        ExpenseDto updated = expenseService.updateExpense(id, expenseDto, authentication.getName());
        return ResponseEntity.ok(
                ApiResponse.success("Expense updated successfully", updated));
    }

    @Operation(summary = "Delete an expense")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteExpense(
            @PathVariable Long id,
            Authentication authentication) {
        expenseService.deleteExpense(id, authentication.getName());
        return ResponseEntity.ok(
                ApiResponse.success("Expense deleted successfully", null));
    }

    @Operation(summary = "Get expense by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ExpenseDto>> getExpenseById(
            @PathVariable Long id,
            Authentication authentication) {
        ExpenseDto expenseDto = expenseService.getExpenseById(id, authentication.getName());
        return ResponseEntity.ok(ApiResponse.success(expenseDto));
    }

    @Operation(summary = "Get all expenses for current user")
    @GetMapping
    public ResponseEntity<ApiResponse<List<ExpenseDto>>> getAllExpenses(
            Authentication authentication) {
        List<ExpenseDto> expenses = expenseService.getAllExpenses(authentication.getName());
        return ResponseEntity.ok(ApiResponse.success(expenses));
    }

    @Operation(summary = "Get expenses by category")
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<List<ExpenseDto>>> getExpensesByCategory(
            @PathVariable Long categoryId,
            Authentication authentication) {
        List<ExpenseDto> expenses = expenseService.getExpensesByCategory(
                categoryId, authentication.getName());
        return ResponseEntity.ok(ApiResponse.success(expenses));
    }

    @Operation(summary = "Get expenses by date range")
    @GetMapping("/date-range")
    public ResponseEntity<ApiResponse<List<ExpenseDto>>> getExpensesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            Authentication authentication) {
        List<ExpenseDto> expenses = expenseService.getExpensesByDateRange(
                startDate, endDate, authentication.getName());
        return ResponseEntity.ok(ApiResponse.success(expenses));
    }
}
