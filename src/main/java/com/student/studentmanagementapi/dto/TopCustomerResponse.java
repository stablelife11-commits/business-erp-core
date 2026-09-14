package com.student.studentmanagementapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TopCustomerResponse {

    private String customerName;

    private BigDecimal totalAmount;

    public TopCustomerResponse() {
    }
}