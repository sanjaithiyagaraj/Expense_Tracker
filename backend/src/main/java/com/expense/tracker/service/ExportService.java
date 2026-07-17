package com.expense.tracker.service;

import com.expense.tracker.entity.Expense;
import com.expense.tracker.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExportService {

    private final ExpenseRepository expenseRepository;

    public ExportService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public String exportToCsv() {
        List<Expense> expenses = expenseRepository.findAll();
        StringBuilder csv = new StringBuilder();

        // CSV Header
        csv.append("ID,Title,Category,Amount,Date,Notes\n");

        // CSV Data Rows
        for (Expense expense : expenses) {
            csv.append(expense.getId()).append(",");
            csv.append(escapeCsvField(expense.getTitle())).append(",");
            csv.append(escapeCsvField(expense.getCategory())).append(",");
            csv.append(expense.getAmount()).append(",");
            csv.append(expense.getDate()).append(",");
            csv.append(escapeCsvField(expense.getNotes()));
            csv.append("\n");
        }

        return csv.toString();
    }

    private String escapeCsvField(String field) {
        if (field == null) {
            return "";
        }
        if (field.contains(",") || field.contains("\"") || field.contains("\n")) {
            return "\"" + field.replace("\"", "\"\"") + "\"";
        }
        return field;
    }
}
