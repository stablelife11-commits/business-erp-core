package com.student.studentmanagementapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class SupplierOutstandingResponse {

    private Long supplierId;

    private BigDecimal totalPurchases;

    private BigDecimal totalPaid;

    private BigDecimal outstanding;


    public SupplierOutstandingResponse() {
    }


    public SupplierOutstandingResponse(
            Long supplierId,
            BigDecimal totalPurchases,
            BigDecimal totalPaid,
            BigDecimal outstanding) {

        this.supplierId = supplierId;
        this.totalPurchases = totalPurchases;
        this.totalPaid = totalPaid;
        this.outstanding = outstanding;
    }
}