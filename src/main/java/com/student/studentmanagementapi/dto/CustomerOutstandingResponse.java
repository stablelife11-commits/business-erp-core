package com.student.studentmanagementapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CustomerOutstandingResponse {

    private Long customerId;

    private String customerName;

    private String mobile;

    private BigDecimal totalSales;

    private BigDecimal totalPaid;

    private BigDecimal outstanding;
}