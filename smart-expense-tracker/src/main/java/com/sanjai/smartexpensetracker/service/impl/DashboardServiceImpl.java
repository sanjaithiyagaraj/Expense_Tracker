package com.sanjai.smartexpensetracker.service.impl;

import com.sanjai.smartexpensetracker.dto.CategorySpendingDto;
import com.sanjai.smartexpensetracker.dto.DashboardDto;
import com.sanjai.smartexpensetracker.dto.ExpenseDto;
import com.sanjai.smartexpensetracker.entity.Expense;
import com.sanjai.smartexpensetracker.entity.User;
import com.sanjai.smartexpensetracker.exception.ResourceNotFoundException;
import com.sanjai.smartexpensetracker.repository.ExpenseRepository;
import com.sanjai.smartexpensetracker.repository.UserRepository;
import com.sanjai.smartexpensetracker.service.DashboardService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    public DashboardServiceImpl(ExpenseRepository expenseRepository,
                                UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
    }

    @Override
    public DashboardDto getDashboard(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

        Long userId = user.getId();
        LocalDate now = LocalDate.now();

        BigDecimal totalSpending = expenseRepository.getTotalSpendingByUserId(userId);

        BigDecimal monthlySpending = expenseRepository.getMonthlySpendingByUserId(
                userId, now.getMonthValue(), now.getYear());

        Long expenseCount = expenseRepository.countByUserId(userId);

        Expense highestExpense = expenseRepository.findHighestExpenseByUserId(userId);
        ExpenseDto highestExpenseDto = null;
        if (highestExpense != null) {
            highestExpenseDto = ExpenseDto.builder()
                    .id(highestExpense.getId())
                    .title(highestExpense.getTitle())
                    .description(highestExpense.getDescription())
                    .amount(highestExpense.getAmount())
                    .expenseDate(highestExpense.getExpenseDate())
                    .categoryId(highestExpense.getCategory().getId())
                    .categoryName(highestExpense.getCategory().getCategoryName())
                    .userId(highestExpense.getUser().getId())
                    .userFullName(highestExpense.getUser().getFullName())
                    .build();
        }

        List<Object[]> categorySpendingData = expenseRepository.getCategoryWiseSpending(userId);
        List<CategorySpendingDto> categoryWiseSpending = categorySpendingData.stream()
                .map(row -> CategorySpendingDto.builder()
                        .categoryName((String) row[0])
                        .totalAmount((BigDecimal) row[1])
                        .count((Long) row[2])
                        .build())
                .collect(Collectors.toList());

        return DashboardDto.builder()
                .totalSpending(totalSpending)
                .monthlySpending(monthlySpending)
                .expenseCount(expenseCount)
                .highestExpense(highestExpenseDto)
                .categoryWiseSpending(categoryWiseSpending)
                .build();
    }
}
