package com.student.studentmanagementapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class MonthlySalesResponse {

    private int month;

    private BigDecimal totalSales;

    public MonthlySalesResponse() {
    }

}