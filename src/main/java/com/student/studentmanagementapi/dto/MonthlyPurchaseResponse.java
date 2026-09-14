package com.student.studentmanagementapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonthlyPurchaseResponse {

    private int month;

    private Double totalPurchase;

    public MonthlyPurchaseResponse() {
    }
}