package com.sanjai.smartexpensetracker.repository;

import com.sanjai.smartexpensetracker.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByUserId(Long userId);

    List<Expense> findByUserIdAndCategoryId(Long userId, Long categoryId);

    @Query("SELECT e FROM Expense e WHERE e.user.id = :userId AND e.expenseDate BETWEEN :startDate AND :endDate ORDER BY e.expenseDate DESC")
    List<Expense> findByUserIdAndDateRange(
            @Param("userId") Long userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.user.id = :userId")
    BigDecimal getTotalSpendingByUserId(@Param("userId") Long userId);

    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.user.id = :userId AND MONTH(e.expenseDate) = :month AND YEAR(e.expenseDate) = :year")
    BigDecimal getMonthlySpendingByUserId(
            @Param("userId") Long userId,
            @Param("month") int month,
            @Param("year") int year);

    @Query("SELECT e FROM Expense e WHERE e.user.id = :userId ORDER BY e.amount DESC LIMIT 1")
    Expense findHighestExpenseByUserId(@Param("userId") Long userId);

    @Query("SELECT COUNT(e) FROM Expense e WHERE e.user.id = :userId")
    Long countByUserId(@Param("userId") Long userId);

    @Query("SELECT e.category.categoryName, COALESCE(SUM(e.amount), 0), COUNT(e) FROM Expense e WHERE e.user.id = :userId GROUP BY e.category.categoryName ORDER BY SUM(e.amount) DESC")
    List<Object[]> getCategoryWiseSpending(@Param("userId") Long userId);
}
