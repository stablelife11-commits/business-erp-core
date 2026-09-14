package com.student.studentmanagementapi.dto;

import lombok.Data;

@Data
public class PurchaseItemResponse {

    private String productName;

    private Integer quantity;

    private Double purchasePrice;

    private Double totalPrice;
}