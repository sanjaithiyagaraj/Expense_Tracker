package com.expense.tracker.util;

import java.time.LocalDate;
import java.time.YearMonth;

public final class DateUtil {

    private DateUtil() {
        // Utility class — prevent instantiation
    }

    public static LocalDate getStartOfMonth(int month, int year) {
        return LocalDate.of(year, month, 1);
    }

    public static LocalDate getEndOfMonth(int month, int year) {
        return YearMonth.of(year, month).atEndOfMonth();
    }
}
