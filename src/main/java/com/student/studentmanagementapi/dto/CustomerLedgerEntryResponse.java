package com.student.studentmanagementapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class CustomerLedgerEntryResponse {

    private LocalDate date;

    private String type;

    private String referenceNumber;

    private String description;

    private BigDecimal debit;

    private BigDecimal credit;

    private BigDecimal balance;
}