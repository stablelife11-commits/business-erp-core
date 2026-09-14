package com.student.studentmanagementapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Setter
@Getter
public class StockReportResponse {

    private long totalProducts;

    private int totalStockQuantity;

    private BigDecimal totalStockValue;
    public StockReportResponse() {
    }

}