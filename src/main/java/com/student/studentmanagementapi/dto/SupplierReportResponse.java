package com.student.studentmanagementapi.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SupplierReportResponse {

    private String supplierName;
    private long totalBills;
    private int totalQuantity;
    private Double totalPurchase;

    public SupplierReportResponse() {
    }

}