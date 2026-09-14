package com.student.studentmanagementapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class CustomerLedgerResponse {

    private Long customerId;

    private String customerName;

    private String mobile;

    private BigDecimal totalSales;

    private BigDecimal totalPaid;

    private BigDecimal outstanding;

    private List<CustomerLedgerEntryResponse> entries;
}