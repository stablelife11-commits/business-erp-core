package com.student.studentmanagementapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopSupplierResponse {

    private String supplierName;

    private Double totalPurchase;

    public TopSupplierResponse() {
    }
}