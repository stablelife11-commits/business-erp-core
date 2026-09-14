package com.student.studentmanagementapi.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PurchaseResponse {

    private Long id;

    private String purchaseNumber;

    private LocalDate purchaseDate;

    private String supplierName;

    private String invoiceNumber;

    private Double totalAmount;

    private String status;

    private List<PurchaseItemResponse> items;
}