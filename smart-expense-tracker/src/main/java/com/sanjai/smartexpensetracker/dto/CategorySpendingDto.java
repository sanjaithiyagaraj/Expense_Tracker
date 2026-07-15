package com.sanjai.smartexpensetracker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategorySpendingDto {

    private String categoryName;
    private BigDecimal totalAmount;
    private Long count;
}
