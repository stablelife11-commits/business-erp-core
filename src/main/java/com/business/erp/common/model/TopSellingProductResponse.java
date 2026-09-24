package com.business.erp.common.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopSellingProductResponse {

    private String productName;

    private Long totalQuantity;

    public TopSellingProductResponse() {
    }
}